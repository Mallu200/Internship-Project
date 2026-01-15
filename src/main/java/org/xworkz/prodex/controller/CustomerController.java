package org.xworkz.prodex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xworkz.prodex.dto.CustomerDto;
import org.xworkz.prodex.enums.CustomerType;
import org.xworkz.prodex.service.CustomerService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/administrator")
public class CustomerController {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/addCustomerPage")
    public String addCustomerPage(@RequestParam("userEmail") String userEmail, Model model) {
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("customerDto", new CustomerDto());
        return "addCustomer";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@Valid @ModelAttribute("customerDto") CustomerDto dto,
                              BindingResult result,
                              @RequestParam("userEmail") String userEmail,
                              RedirectAttributes ra, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userEmail", userEmail);
            return "addCustomer";
        }

        List<String> serviceErrors = new ArrayList<>();
        if (customerService.registerCustomer(dto, serviceErrors)) {
            ra.addFlashAttribute("successMessage", "Customer " + dto.getCustomerName() + " added successfully!");
            return "redirect:/administrator/viewCustomerPage?userEmail=" + userEmail;
        }

        model.addAttribute("errorMessage", String.join(", ", serviceErrors));
        model.addAttribute("userEmail", userEmail);
        return "addCustomer";
    }

    @GetMapping("/viewCustomerPage")
    public String viewCustomerPage(@RequestParam("userEmail") String userEmail,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   Model model) {

        long total = customerService.getTotalCustomerCount();
        int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

        List<CustomerDto> customers = customerService.getPaginatedCustomers(page, PAGE_SIZE);

        model.addAttribute("allCustomers", customers);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("userEmail", userEmail);
        return "viewCustomers";
    }

    @GetMapping("/searchCustomer")
    public String searchCustomer(@RequestParam(value = "searchCustomerName", required = false) String name,
                                 @RequestParam(value = "searchType", required = false) String type,
                                 @RequestParam(value = "searchEmail", required = false) String email,
                                 @RequestParam(value = "searchContactNumber", required = false) String contact,
                                 @RequestParam("userEmail") String userEmail,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 Model model) {

        CustomerType customerType = (type != null && !type.isEmpty()) ? CustomerType.valueOf(type) : null;

        List<CustomerDto> results = customerService.searchPaginatedCustomers(name, customerType, email, contact, page, PAGE_SIZE);
        long totalResults = customerService.getSearchCustomerCount(name, customerType, email, contact);
        int totalPages = (int) Math.ceil((double) totalResults / PAGE_SIZE);

        model.addAttribute("allCustomers", results);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("searchMode", true);

        return "viewCustomers";
    }

    @GetMapping("/viewCustomerDetails")
    public String viewCustomerDetails(@RequestParam("id") Long id,
                                      @RequestParam("userEmail") String userEmail,
                                      Model model) {
        CustomerDto customer = customerService.getCustomerById(id);
        if (customer == null) {
            model.addAttribute("errorMessage", "Customer record not found.");
            return "forward:/administrator/viewCustomerPage";
        }

        model.addAttribute("customerDetails", customer);
        model.addAttribute("userEmail", userEmail);
        return "viewCustomerDetails";
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(@Valid @ModelAttribute("customerDetails") CustomerDto dto,
                                 BindingResult result,
                                 @RequestParam("userEmail") String userEmail,
                                 RedirectAttributes ra, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userEmail", userEmail);
            return "viewCustomerDetails";
        }

        if (customerService.updateCustomer(dto)) {
            ra.addFlashAttribute("successMessage", "Record updated successfully!");
            return "redirect:/administrator/viewCustomerPage?userEmail=" + userEmail;
        }

        model.addAttribute("errorMessage", "Update failed.");
        model.addAttribute("userEmail", userEmail);
        return "viewCustomerDetails";
    }

    @PostMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam("id") Long id,
                                 @RequestParam("userEmail") String userEmail,
                                 RedirectAttributes ra) {
        int deleted = customerService.deleteCustomer(id);
        if (deleted > 0) {
            ra.addFlashAttribute("successMessage", "Customer deleted successfully.");
        } else {
            ra.addFlashAttribute("errorMessage", "Could not delete customer.");
        }
        return "redirect:/administrator/viewCustomerPage?userEmail=" + userEmail;
    }
}
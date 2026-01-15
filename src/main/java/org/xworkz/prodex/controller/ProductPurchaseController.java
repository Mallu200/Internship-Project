package org.xworkz.prodex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xworkz.prodex.dto.ProductPurchaseDto;
import org.xworkz.prodex.enums.PurchaseStatus;
import org.xworkz.prodex.service.ProductPurchaseService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/purchase")
public class ProductPurchaseController {

    @Autowired
    private ProductPurchaseService productPurchaseService;

    @GetMapping("/showForm")
    public String showPurchaseForm(@RequestParam("userEmail") String email, Model model) {
        model.addAttribute("userEmail", email);

        model.addAttribute("groupNames", productPurchaseService.getAllProductGroupNames());
        model.addAttribute("customerNames", productPurchaseService.getAllDebitCustomerNames());

        model.addAttribute("productPurchaseDto", new ProductPurchaseDto());
        return "purchaseProduct";
    }

    @PostMapping("/submitRequest")
    public String processPurchase(@ModelAttribute("productPurchaseDto") ProductPurchaseDto dto,
                                  @RequestParam("userEmail") String email,
                                  RedirectAttributes ra) {

        dto.setMemberEmail(email); 
        List<String> errors = new ArrayList<>();

        boolean saved = productPurchaseService.submitProductRequest(dto, errors);

        if (saved) {
            ra.addFlashAttribute("successMessage", "Purchase request submitted successfully!");
        } else {
            ra.addFlashAttribute("errorMessage", "Submission failed: " + String.join(", ", errors));
        }

        return "redirect:/member/viewPurchaseHistoryPage?userEmail=" + email;
    }

    @PostMapping("/cancelRequest")
    public String cancelRequest(@RequestParam("purchaseId") Long id,
                                @RequestParam("userEmail") String email,
                                RedirectAttributes ra) {

        boolean updated = productPurchaseService.updateStatus(id, PurchaseStatus.CANCELLED);

        if (updated) {
            ra.addFlashAttribute("successMessage", "Request retracted successfully.");
        } else {
            ra.addFlashAttribute("errorMessage", "Failed to cancel the request.");
        }

        return "redirect:/member/viewPurchaseHistoryPage?userEmail=" + email;
    }
    @PostMapping("/savePurchaseRequest")
    public String savePurchaseRequest(@ModelAttribute ProductPurchaseDto purchaseDto,
                                      @RequestParam("memberEmail") String memberEmail,
                                      RedirectAttributes ra) {

        purchaseDto.setMemberEmail(memberEmail);
        purchaseDto.setStatus(PurchaseStatus.PENDING);

        List<String> errors = new ArrayList<>();

        boolean isSaved = productPurchaseService.submitProductRequest(purchaseDto, errors);

        if (isSaved) {
            ra.addFlashAttribute("successMessage", "Purchase request for " + purchaseDto.getItemName() + " submitted successfully!");
        } else {
            String errorMsg = errors.isEmpty() ? "Database error occurred." : String.join(", ", errors);
            ra.addFlashAttribute("errorMessage", "Failed to submit request: " + errorMsg);
        }

        return "redirect:/administratorDashboardPage?email=" + memberEmail;
    }


}
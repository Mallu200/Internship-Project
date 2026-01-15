package org.xworkz.prodex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xworkz.prodex.dto.EnrollmentDto;
import org.xworkz.prodex.dto.ProductPurchaseDto;
import org.xworkz.prodex.enums.PurchaseStatus;
import org.xworkz.prodex.service.AdministratorService;
import org.xworkz.prodex.service.MemberService;
import org.xworkz.prodex.service.ProductPurchaseService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    @Autowired
    private ProductPurchaseService productPurchaseService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/administratorDashboardPage")
    public String administratorDashboardPage(@RequestParam("email") String email, Model model) {
        EnrollmentDto adminDto = administratorService.findAdminByEmail(email);

        if (adminDto != null) {
            model.addAttribute("adminDto", adminDto);
            model.addAttribute("email", email);
            model.addAttribute("totalMembers", memberService.countAllMembers());

            model.addAttribute("pendingRequestCount", productPurchaseService.getPendingRequestCount());

            return "administratorDashboard";
        }
        return "redirect:/login?error=Unauthorized";
    }

    @GetMapping("/viewMemberPage")
    public String viewMemberPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam("userEmail") String userEmail, Model model) {
        model.addAttribute("allMembers", memberService.findPaginatedMembers(page, 10));
        model.addAttribute("totalMembers", memberService.countAllMembers());
        model.addAttribute("currentPage", page);
        model.addAttribute("userEmail", userEmail);
        return "viewMembers";
    }

    @GetMapping("/searchMember")
    public String searchMember(@RequestParam(value = "searchUserName", required = false) String name,
                               @RequestParam(value = "searchEmail", required = false) String email,
                               @RequestParam(value = "searchContactNumber", required = false) String contact,
                               @RequestParam(value = "searchAccountLocked", required = false) String locked,
                               @RequestParam("userEmail") String userEmail, Model model) {

        List<EnrollmentDto> results = memberService.searchPaginatedMembers(name, email, contact, locked, 1, 10);
        model.addAttribute("allMembers", results);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("searchMode", true);
        return "viewMembers";
    }

    @GetMapping("/editMemberPage")
    public String editMemberPage(@RequestParam("id") Long id,
                                 @RequestParam("userEmail") String adminEmail,
                                 Model model) {
        EnrollmentDto memberDto = memberService.getMemberById(id);
        if (memberDto != null) {
            model.addAttribute("memberDto", memberDto);
            model.addAttribute("userEmail", adminEmail);
            return "editMember";
        }
        return "redirect:/administrator/viewMemberPage?userEmail=" + adminEmail + "&error=MemberNotFound";
    }

    @PostMapping("/updateMember")
    public String updateMember(@ModelAttribute("memberDto") EnrollmentDto memberDto,
                               @RequestParam("userEmail") String adminEmail,
                               RedirectAttributes ra) {
        boolean updated = memberService.updateMember(memberDto);
        if (updated) {
            ra.addFlashAttribute("successMessage", "Record for " + memberDto.getUserName() + " updated successfully!");
        } else {
            ra.addFlashAttribute("errorMessage", "Failed to update record.");
        }
        return "redirect:/administrator/viewMemberPage?userEmail=" + adminEmail;
    }

    @GetMapping("/deleteMember")
    public String deleteMember(@RequestParam("id") Long id,
                               @RequestParam("userEmail") String adminEmail,
                               RedirectAttributes ra) {
        try {
            int deletedRows = memberService.deleteMember(id);
            if (deletedRows > 0) {
                ra.addFlashAttribute("successMessage", "Member record deleted successfully.");
            } else {
                ra.addFlashAttribute("errorMessage", "Member not found.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "An error occurred during deletion.");
        }
        return "redirect:/administrator/viewMemberPage?userEmail=" + adminEmail;
    }


    @GetMapping("/viewPendingRequests")
    public String viewPendingRequests(@RequestParam("userEmail") String email, Model model) {
        List<ProductPurchaseDto> pendingRequests = productPurchaseService.findRequestsByStatus(PurchaseStatus.PENDING);
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("userEmail", email);
        return "viewPendingRequests";
    }

    @PostMapping("/updatePurchaseStatus")
    public String updatePurchaseStatus(@RequestParam("purchaseId") Long purchaseId,
                                       @RequestParam("status") String status,
                                       @RequestParam("adminEmail") String adminEmail,
                                       @RequestParam(value = "comment", required = false) String comment,
                                       RedirectAttributes ra) {
        try {
            PurchaseStatus newStatus = PurchaseStatus.valueOf(status);

            boolean updated = productPurchaseService.updateStatus(purchaseId, newStatus);

            if (updated) {
                ra.addFlashAttribute("successMessage", "Request #" + purchaseId + " has been " + status + " successfully.");
            } else {
                ra.addFlashAttribute("errorMessage", "Failed to update request status.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Invalid status selection.");
        }
        return "redirect:/administrator/viewPendingRequests?userEmail=" + adminEmail;
    }

    @GetMapping("/editAdminProfile")
    public String editAdminProfile(@RequestParam("email") String email, Model model) {
        EnrollmentDto admin = administratorService.findAdminByEmail(email);
        model.addAttribute("adminDto", admin);
        model.addAttribute("userEmail", email);
        return "editAdminProfile";
    }

    @PostMapping("/updateAdminProfile")
    public String updateAdminProfile(@ModelAttribute("adminDto") EnrollmentDto adminDto, RedirectAttributes ra) {
        boolean updated = administratorService.updateAdmin(adminDto);
        if (updated) {
            ra.addFlashAttribute("successMessage", "Profile updated successfully!");
        }
        return "redirect:/administrator/administratorDashboardPage?email=" + adminDto.getEmail();
    }

    @GetMapping("/logout")
    public String adminLogout(HttpSession session, RedirectAttributes ra) {
        if (session != null) {
            session.invalidate();
        }

        ra.addFlashAttribute("successMessage", "Admin session closed successfully.");

        return "redirect:/loginPage";
    }
}
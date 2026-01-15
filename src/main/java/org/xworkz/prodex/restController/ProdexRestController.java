package org.xworkz.prodex.restController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.xworkz.prodex.dto.EnrollmentDto;
import org.xworkz.prodex.dto.ProductGroupNameDto;
import org.xworkz.prodex.service.ProdexService;
import org.xworkz.prodex.service.ProductPurchaseService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api") 
@Api(tags = "Prodex REST API", description = "Endpoints for Enrollment and Product Management")
public class ProdexRestController {

    @Autowired
    private ProdexService prodexService;

    @Autowired
    private ProductPurchaseService productPurchaseService;

    @PostMapping("/register")
    @ApiOperation("Register a new user enrollment")
    public ResponseEntity<String> createEnrollment(@Valid @RequestBody EnrollmentDto enrollmentDto, BindingResult result) {

        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation failed: " + errors);
        }

        List<String> serviceErrors = new ArrayList<>();
        boolean registered = prodexService.registerEnrollment(enrollmentDto, serviceErrors);

        if (registered) {
            return ResponseEntity.ok("Registered Successfully!");
        } else {
            return ResponseEntity.status(400).body("Registration Failed! " + String.join(", ", serviceErrors));
        }
    }

    @GetMapping("/checkEmail")
    @ApiOperation("Check if an email is already registered")
    public ResponseEntity<Map<String, String>> checkEmail(@RequestParam("email") String email) {
        Map<String, String> response = new HashMap<>();

        EnrollmentDto dto = prodexService.findEnrollmentByEmail(email.trim());
        if (dto != null) {
            response.put("status", "EXISTS");
            response.put("message", "Email already registered");
        } else {
            response.put("status", "AVAILABLE");
            response.put("message", "Email is available");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/productGroupNames")
    @ApiOperation("Create a new product group")
    public ResponseEntity<String> saveProductGroupName(@Valid @RequestBody ProductGroupNameDto productGroupNameDto, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed");
        }

        List<String> serviceErrors = new ArrayList<>();
        boolean saved = productPurchaseService.saveProductGroupName(productGroupNameDto, serviceErrors);

        if (saved) {
            return ResponseEntity.ok("Product Group Name Created Successfully!");
        } else {
            return ResponseEntity.status(400).body("Creation Failed! " + String.join(", ", serviceErrors));
        }
    }
}
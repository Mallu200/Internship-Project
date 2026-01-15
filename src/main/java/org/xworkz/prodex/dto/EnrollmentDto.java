package org.xworkz.prodex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.xworkz.prodex.enums.UserRole;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {

    private Long enrollmentId;

    @NotBlank(message = "User name cannot be blank")
    @Size(min = 3, max = 50, message = "User name must be between 3 and 50 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
            message = "Enter a valid 10-digit phone number with optional country code")
    private String contact;

    @NotNull(message = "Role is required")
    private UserRole role;

    private String password;

    private String confirmPassword;

    private String otp;

    private LocalDateTime otpCreatedAt;

    private int failedAttempts;

    private boolean accountLocked;
}

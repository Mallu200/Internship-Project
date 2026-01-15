package org.xworkz.prodex.service;

import org.xworkz.prodex.dto.EnrollmentDto;
import org.xworkz.prodex.dto.LoginDto;

import java.util.List;

public interface ProdexService {

    boolean isPasswordValid(String password);

    boolean registerEnrollment(EnrollmentDto enrollmentDto, List<String> errors);

    EnrollmentDto findEnrollmentByEmail(String email);

    EnrollmentDto findEnrollmentByContact(String contact);

    EnrollmentDto findEnrollmentEntityByEmailOrContact(String emailOrContact);

    boolean checkPassword(LoginDto loginDto, String rawPassword);

    boolean updateFailedAttempts(String emailOrContact, int attemptCount);

    boolean lockAccount(String emailOrContact);

    boolean resetFailedAttempts(String emailOrContact);

    String loginAndStoreByRole(String emailOrContact, String password);

    boolean sendOtpToEmail(String email);

    boolean verifyOtp(String email, String otp);

    boolean updatePassword(String email, String password, List<String> errors);

    boolean clearExpiredOtps();

    boolean updatePasswordAndUnlock(String trim, String password, List<String> errors);
}

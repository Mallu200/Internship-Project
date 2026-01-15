package org.xworkz.prodex.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xworkz.prodex.dto.EnrollmentDto;
import org.xworkz.prodex.dto.LoginDto;
import org.xworkz.prodex.entity.*;
import org.xworkz.prodex.enums.UserRole;
import org.xworkz.prodex.repository.ProdexRepository;
import org.xworkz.prodex.service.ProdexService;
import org.xworkz.prodex.util.SendingMails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

@Service
@Transactional 
public class ProdexServiceImpl implements ProdexService {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Autowired
    private ProdexRepository prodexRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SendingMails mails;

    @Override
    public boolean isPasswordValid(String password) {
        return password != null && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$");
    }

    @Override
    public boolean registerEnrollment(EnrollmentDto enrollmentDto, List<String> errors) {
        if (enrollmentDto == null) {
            errors.add("Enrollment data is null");
            return false;
        }

        try {
            if (!isPasswordValid(enrollmentDto.getPassword())) {
                errors.add("Invalid password format");
                return false;
            }

            if (!enrollmentDto.getPassword().equals(enrollmentDto.getConfirmPassword())) {
                errors.add("Passwords do not match");
                return false;
            }

            if (prodexRepository.findEnrollmentByEmail(enrollmentDto.getEmail()) != null) {
                errors.add("Email already registered: " + enrollmentDto.getEmail());
                return false;
            }

            if (prodexRepository.findEnrollmentByContact(enrollmentDto.getContact()) != null) {
                errors.add("Contact already registered: " + enrollmentDto.getContact());
                return false;
            }

            EnrollmentEntity entity = new EnrollmentEntity();
            BeanUtils.copyProperties(enrollmentDto, entity);
            entity.setPassword(encoder.encode(enrollmentDto.getPassword()));
            entity.setFailedAttempts(0);
            entity.setAccountLocked(false);

            return prodexRepository.saveEnrollment(entity);
        } catch (Exception e) {
            errors.add("System error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public EnrollmentDto findEnrollmentByEmail(String email) {
        EnrollmentEntity entity = prodexRepository.findEnrollmentByEmail(email);
        if (entity == null) return null;
        EnrollmentDto dto = new EnrollmentDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public EnrollmentDto findEnrollmentByContact(String contact) {
        EnrollmentEntity entity = prodexRepository.findEnrollmentByContact(contact);
        if (entity == null) return null;
        EnrollmentDto dto = new EnrollmentDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public EnrollmentDto findEnrollmentEntityByEmailOrContact(String emailOrContact) {
        EnrollmentEntity entity = prodexRepository.findEnrollmentEntityByEmailOrContact(emailOrContact);
        if (entity == null) return null;
        EnrollmentDto dto = new EnrollmentDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public boolean checkPassword(LoginDto loginDto, String rawPassword) {
        return loginDto != null && rawPassword != null && encoder.matches(rawPassword, loginDto.getPassword());
    }

    @Override
    public boolean updateFailedAttempts(String emailOrContact, int attemptCount) {
        EnrollmentEntity enrollment = prodexRepository.findEnrollmentEntityByEmailOrContact(emailOrContact);
        if (enrollment != null) {
            enrollment.setFailedAttempts(attemptCount);
            if (attemptCount >= 3) {
                enrollment.setAccountLocked(true);
            }
            return prodexRepository.updateEnrollment(enrollment);
        }
        return false;
    }

    @Override
    public boolean lockAccount(String emailOrContact) {
        EnrollmentEntity enrollment = prodexRepository.findEnrollmentEntityByEmailOrContact(emailOrContact);
        if (enrollment != null) {
            enrollment.setAccountLocked(true);
            return prodexRepository.updateEnrollment(enrollment);
        }
        return false;
    }

    @Override
    public boolean resetFailedAttempts(String emailOrContact) {
        EnrollmentEntity enrollment = prodexRepository.findEnrollmentEntityByEmailOrContact(emailOrContact);
        if (enrollment != null) {
            enrollment.setFailedAttempts(0);
            enrollment.setAccountLocked(false);
            return prodexRepository.updateEnrollment(enrollment);
        }
        return false;
    }

    @Override
    public String loginAndStoreByRole(String emailOrContact, String rawPassword) {
        EnrollmentDto enrollment = findEnrollmentEntityByEmailOrContact(emailOrContact);
        if (enrollment == null) return "USER_NOT_FOUND";
        if (enrollment.isAccountLocked()) return "ACCOUNT_LOCKED";

        if (!checkPassword(new LoginDto(emailOrContact, enrollment.getPassword()), rawPassword)) {
            return "INVALID_PASSWORD";
        }

        resetFailedAttempts(emailOrContact);
        LocalDateTime now = LocalDateTime.now();

        try {
            if (enrollment.getRole() == UserRole.ADMINISTRATOR) {
                AdministratorEntity admin = new AdministratorEntity();
                BeanUtils.copyProperties(enrollment, admin);
                admin.setLoginDateTime(now);
                prodexRepository.saveAdministrator(admin);
                return "ADMIN_DASHBOARD";
            } else {
                MemberEntity member = new MemberEntity();
                BeanUtils.copyProperties(enrollment, member);
                member.setLoginDateTime(now);
                prodexRepository.saveMember(member);
                return "MEMBER_DASHBOARD";
            }
        } catch (Exception e) {
            return "INTERNAL_ERROR";
        }
    }

    @Override
    public boolean sendOtpToEmail(String email) {
        EnrollmentEntity enrollment = prodexRepository.findEnrollmentByEmail(email);
        if (enrollment != null) {
            String otp = mails.restPasswordMessage(email);
            enrollment.setOtp(encoder.encode(otp));
            enrollment.setOtpCreatedAt(LocalDateTime.now());
            return prodexRepository.updateEnrollment(enrollment);
        }
        return false;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        EnrollmentEntity enrollment = prodexRepository.findEnrollmentByEmail(email);
        if (enrollment == null || enrollment.getOtp() == null || enrollment.getOtpCreatedAt() == null) {
            return false;
        }
        if (enrollment.getOtpCreatedAt().isBefore(LocalDateTime.now().minusMinutes(2))) {
            return false;
        }
        return encoder.matches(otp, enrollment.getOtp());
    }

    @Override
    public boolean updatePassword(String email, String password, List<String> errors) {
        if (!isPasswordValid(password)) {
            errors.add("Password does not meet requirements.");
            return false;
        }

        EnrollmentEntity entity = prodexRepository.findEnrollmentByEmail(email);
        if (entity != null) {
            entity.setPassword(encoder.encode(password));
            entity.setOtp(null); 
            entity.setOtpCreatedAt(null);
            return prodexRepository.updateEnrollment(entity);
        }
        return false;
    }

    @Override
    public boolean updatePasswordAndUnlock(String email, String password, List<String> errors) {
        boolean isUpdated = updatePassword(email, password, errors);
        if (isUpdated) {
            return resetFailedAttempts(email);
        }
        return false;
    }

    @Override
    public boolean clearExpiredOtps() {
        LocalDateTime limit = LocalDateTime.now().minusMinutes(2);
        List<EnrollmentEntity> expiredList = prodexRepository.findByOtpNotNullAndOtpCreatedAtBefore(limit);
        for (EnrollmentEntity e : expiredList) {
            e.setOtp(null);
            e.setOtpCreatedAt(null);
            prodexRepository.updateEnrollment(e);
        }
        return !expiredList.isEmpty();
    }
}
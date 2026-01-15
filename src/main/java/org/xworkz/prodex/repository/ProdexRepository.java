package org.xworkz.prodex.repository;

import org.xworkz.prodex.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ProdexRepository {
    boolean saveEnrollment(EnrollmentEntity enrollmentEntity);

    EnrollmentEntity findEnrollmentByEmail(String email);

    EnrollmentEntity findEnrollmentByContact(String contact);

    EnrollmentEntity findEnrollmentEntityByEmailOrContact(String emailOrContact);

    boolean saveMember(MemberEntity member);

    boolean saveAdministrator(AdministratorEntity admin);

    boolean updateEnrollment(EnrollmentEntity enrollmentEntity);

    List<EnrollmentEntity> findByOtpNotNullAndOtpCreatedAtBefore(LocalDateTime twoMinutesAgo);

}

package org.xworkz.prodex.repository;

import org.xworkz.prodex.entity.EnrollmentEntity;

public interface AdministratorRepository {
    EnrollmentEntity findAdminByEmail(String email);
    boolean updateAdmin(EnrollmentEntity entity);
}
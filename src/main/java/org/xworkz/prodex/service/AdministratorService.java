package org.xworkz.prodex.service;

import org.xworkz.prodex.dto.EnrollmentDto;

public interface AdministratorService {

    EnrollmentDto findAdminByEmail(String trim);

    boolean updateAdmin(EnrollmentDto adminDto);
}

package org.xworkz.prodex.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xworkz.prodex.dto.EnrollmentDto;
import org.xworkz.prodex.entity.EnrollmentEntity;
import org.xworkz.prodex.enums.UserRole;
import org.xworkz.prodex.repository.AdministratorRepository;
import org.xworkz.prodex.service.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorRepository adminRepository;

    @Override
    public EnrollmentDto findAdminByEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;

        EnrollmentEntity entity = adminRepository.findAdminByEmail(email.trim());

        if (entity != null && entity.getRole() == UserRole.ADMINISTRATOR) {
            EnrollmentDto dto = new EnrollmentDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }
        return null;
    }

    @Override
    public boolean updateAdmin(EnrollmentDto adminDto) {
        if (adminDto == null || adminDto.getEmail() == null) return false;

        try {
            EnrollmentEntity existingEntity = adminRepository.findAdminByEmail(adminDto.getEmail().trim());

            if (existingEntity != null && existingEntity.getRole() == UserRole.ADMINISTRATOR) {
                existingEntity.setUserName(adminDto.getUserName());
                existingEntity.setContact(adminDto.getContact());

                if (adminDto.getPassword() != null && !adminDto.getPassword().trim().isEmpty()) {
                    existingEntity.setPassword(adminDto.getPassword());
                }

                return adminRepository.updateAdmin(existingEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
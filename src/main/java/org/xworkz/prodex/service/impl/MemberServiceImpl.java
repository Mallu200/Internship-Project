package org.xworkz.prodex.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xworkz.prodex.dto.EnrollmentDto;
import org.xworkz.prodex.entity.EnrollmentEntity;
import org.xworkz.prodex.enums.UserRole;
import org.xworkz.prodex.repository.MemberRepository;
import org.xworkz.prodex.service.MemberService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private EnrollmentDto convertAndValidateRole(EnrollmentEntity entity) {
        if (entity == null) {
            return null;
        }

        if (entity.getRole() != UserRole.MEMBER) {
            return null;
        }

        EnrollmentDto dto = new EnrollmentDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private List<EnrollmentDto> convertEntityListToDtoList(List<EnrollmentEntity> entities) {
        return entities.stream()
                .map(this::convertAndValidateRole)
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentDto findMemberByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }
        try {
            EnrollmentEntity entity = memberRepository.findMemberByEmail(email);
            return convertAndValidateRole(entity);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EnrollmentDto findMemberByContact(String contact) {
        if (contact == null || contact.isEmpty()) {
            return null;
        }
        try {
            EnrollmentEntity entity = memberRepository.findMemberByContact(contact);
            return convertAndValidateRole(entity);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EnrollmentDto getMemberById(Long enrollmentId) {
        if (enrollmentId == null) {
            return null;
        }
        try {
            EnrollmentEntity entity = memberRepository.getMemberById(enrollmentId);
            return convertAndValidateRole(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<EnrollmentDto> findAllMembers() {
        return findPaginatedMembers(1, 500);
    }

    @Override
    public List<EnrollmentDto> searchMembers(String userName, String email, String contact, boolean accountLocked) {
        String lockedStatus = accountLocked ? "true" : "false";
        return searchPaginatedMembers(userName, email, contact, lockedStatus, 1, 500);
    }

    @Override
    public long countAllMembers() {
        try {
            return memberRepository.countAllMembers();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<EnrollmentDto> findPaginatedMembers(int currentPage, int pageSize) {
        try {
            int startIndex = (currentPage - 1) * pageSize;
            List<EnrollmentEntity> entities = memberRepository.findPaginatedMembers(startIndex, pageSize);
            return convertEntityListToDtoList(entities);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public long countMembersByCriteria(String userName, String email, String contactNumber, String accountLocked) {
        try {
            // Handle "All" status: if the string is empty or null, pass null to the repository
            String statusParam = (accountLocked == null || accountLocked.isEmpty()) ? null : accountLocked;

            return memberRepository.countMembersByCriteria(userName, email, contactNumber, statusParam);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<EnrollmentDto> searchPaginatedMembers(String userName, String email, String contactNumber, String accountLocked, int currentPage, int pageSize) {
        try {
            int startIndex = (currentPage - 1) * pageSize;

            // FIX: Ensure "All Statuses" passes null, otherwise pass the string "true" or "false"
            String statusParam = (accountLocked == null || accountLocked.isEmpty()) ? null : accountLocked;

            List<EnrollmentEntity> entities = memberRepository.searchPaginatedMembers(
                    userName, email, contactNumber, statusParam, startIndex, pageSize
            );
            return convertEntityListToDtoList(entities);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public boolean updateUserDetails(EnrollmentDto enrollmentDto) {
        if (enrollmentDto == null || enrollmentDto.getEmail() == null) {
            return false;
        }

        try {
            EnrollmentEntity existingEntity = memberRepository.findMemberByEmail(enrollmentDto.getEmail());

            if (existingEntity == null || existingEntity.getRole() != UserRole.MEMBER) {
                return false;
            }

            existingEntity.setUserName(enrollmentDto.getUserName());
            existingEntity.setContact(enrollmentDto.getContact());


            if (enrollmentDto.getPassword() != null && !enrollmentDto.getPassword().trim().isEmpty()) {
                existingEntity.setPassword(enrollmentDto.getPassword());
            }

            return memberRepository.updateMember(existingEntity);

        } catch (Exception e) {
            System.err.println("Error updating user details for: " + enrollmentDto.getEmail());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateMember(EnrollmentDto enrollmentDto) {
        if (enrollmentDto == null || enrollmentDto.getEnrollmentId() == null) {
            return false;
        }

        try {
            EnrollmentEntity existingEntity = memberRepository.getMemberById(enrollmentDto.getEnrollmentId());
            if (existingEntity == null) return false;

            existingEntity.setUserName(enrollmentDto.getUserName());
            existingEntity.setEmail(enrollmentDto.getEmail());
            existingEntity.setContact(enrollmentDto.getContact());
            existingEntity.setAccountLocked(enrollmentDto.isAccountLocked());
            existingEntity.setRole(enrollmentDto.getRole());

            if (enrollmentDto.getPassword() != null && !enrollmentDto.getPassword().trim().isEmpty()) {
                existingEntity.setPassword(enrollmentDto.getPassword());
            }

            return memberRepository.updateMember(existingEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int deleteMember(Long enrollmentId) {
        if (enrollmentId == null) return 0;

        try {
            EnrollmentEntity existingEntity = memberRepository.getMemberById(enrollmentId);
            if (existingEntity == null) return 0;

            return memberRepository.deleteMember(enrollmentId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
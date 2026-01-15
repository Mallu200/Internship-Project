package org.xworkz.prodex.repository;

import org.xworkz.prodex.entity.EnrollmentEntity;

import java.util.List;

public interface MemberRepository {
    List<EnrollmentEntity> findAllMembers();

    EnrollmentEntity findMemberByEmail(String email);

    EnrollmentEntity findMemberByContact(String contact);

    List<EnrollmentEntity> searchMembers(String userName, String email, String contact, boolean accountLocked);

    EnrollmentEntity getMemberById(Long enrollmentId);

    boolean updateMember(EnrollmentEntity existingEntity);

    int deleteMember(Long enrollmentId);

    long countAllMembers();

    List<EnrollmentEntity> findPaginatedMembers(int startIndex, int pageSize);

    long countMembersByCriteria(String userName, String email, String contactNumber, String accountLocked);
    List<EnrollmentEntity> searchPaginatedMembers(String userName, String email, String contactNumber, String accountLocked, int startIndex, int pageSize);
}

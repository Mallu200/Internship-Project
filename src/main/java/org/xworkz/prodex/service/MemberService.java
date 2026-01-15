package org.xworkz.prodex.service;

import org.xworkz.prodex.dto.EnrollmentDto;
import java.util.List;

public interface MemberService {

    EnrollmentDto findMemberByEmail(String email);

    boolean updateUserDetails(EnrollmentDto enrollmentDto);

    EnrollmentDto getMemberById(Long enrollmentId);

    EnrollmentDto findMemberByContact(String contact);

    boolean updateMember(EnrollmentDto enrollmentDto);

    int deleteMember(Long enrollmentId);

    List<EnrollmentDto> findAllMembers();

    long countAllMembers();

    List<EnrollmentDto> findPaginatedMembers(int currentPage, int pageSize);

    List<EnrollmentDto> searchMembers(String userName, String email, String contact, boolean accountLocked);

    long countMembersByCriteria(String userName, String email, String contactNumber, String accountLocked);

    List<EnrollmentDto> searchPaginatedMembers(String userName, String email, String contactNumber, String accountLocked, int currentPage, int pageSize);
}
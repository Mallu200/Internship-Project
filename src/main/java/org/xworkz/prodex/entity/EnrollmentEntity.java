package org.xworkz.prodex.entity;

import lombok.Data;
import org.xworkz.prodex.enums.UserRole;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "enrollment_info")
@NamedQueries({
        @NamedQuery(
                name = "EnrollmentEntity.findByEmail",
                query = "SELECT e FROM EnrollmentEntity e WHERE e.email = :email"
        ),
        @NamedQuery(
                name = "EnrollmentEntity.findByContact",
                query = "SELECT e FROM EnrollmentEntity e WHERE e.contact = :contact"
        ),
        @NamedQuery(
                name = "EnrollmentEntity.findByEmailOrContact",
                query = "SELECT e FROM EnrollmentEntity e WHERE e.email = :identifier OR e.contact = :identifier"
        ),
        @NamedQuery(
                name = "EnrollmentEntity.findExpiredOtps",
                query = "SELECT e FROM EnrollmentEntity e WHERE e.otp IS NOT NULL AND e.otpCreatedAt < :expiryLimit"
        ),
        @NamedQuery(
                name = "EnrollmentEntity.findMemberByEmail",
                query = "SELECT e FROM EnrollmentEntity e WHERE e.email = :email"
        ),
        @NamedQuery(
                name = "EnrollmentEntity.findAdminByEmail",
                query = "SELECT e FROM EnrollmentEntity e WHERE e.email = :email"
        ),
        @NamedQuery(
                name = "EnrollmentEntity.countAllMembers",
                query = "SELECT COUNT(e) FROM EnrollmentEntity e WHERE e.role = org.xworkz.prodex.enums.UserRole.MEMBER"
        ),

        @NamedQuery(
                name = "EnrollmentEntity.findPaginatedMembers",
                query = "SELECT e FROM EnrollmentEntity e WHERE e.role = org.xworkz.prodex.enums.UserRole.MEMBER ORDER BY e.enrollmentId DESC"
        ),

        @NamedQuery(
                name = "EnrollmentEntity.countMembersByCriteria",
                query = "SELECT COUNT(e) FROM EnrollmentEntity e WHERE e.role = org.xworkz.prodex.enums.UserRole.MEMBER " +
                        "AND (:name IS NULL OR e.userName LIKE :name) " +
                        "AND (:email IS NULL OR e.email LIKE :email) " +
                        "AND (:contact IS NULL OR e.contact LIKE :contact) " +
                        "AND (:lockedStatus IS NULL OR e.accountLocked = :lockedStatus)" 
        ),

// Query for "All Statuses" (Ignores accountLocked field)
        @NamedQuery(
                name = "EnrollmentEntity.searchMembersWithoutStatus",
                query = "SELECT e FROM EnrollmentEntity e WHERE e.role = org.xworkz.prodex.enums.UserRole.MEMBER " +
                        "AND (:name IS NULL OR e.userName LIKE :name) " +
                        "AND (:email IS NULL OR e.email LIKE :email) " +
                        "AND (:contact IS NULL OR e.contact LIKE :contact) " +
                        "ORDER BY e.enrollmentId DESC"
        ),

        @NamedQuery(
                name = "EnrollmentEntity.searchPaginatedMembers",
                query = "SELECT e FROM EnrollmentEntity e WHERE e.role = org.xworkz.prodex.enums.UserRole.MEMBER " +
                        "AND (:name IS NULL OR e.userName LIKE :name) " +
                        "AND (:email IS NULL OR e.email LIKE :email) " +
                        "AND (:contact IS NULL OR e.contact LIKE :contact) " +
                        "AND (e.accountLocked = :lockedStatus) " + 
                        "ORDER BY e.enrollmentId DESC"
        ),
        @NamedQuery(
                name = "EnrollmentEntity.deleteMember",
                query = "DELETE FROM EnrollmentEntity e WHERE e.enrollmentId = :enrollmentId"
        )
})
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String contact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String password;

    private String otp;

    @Column(name = "otp_created_at")
    private LocalDateTime otpCreatedAt;

    @Column(name = "failed_attempts")
    private int failedAttempts = 0;

    @Column(name = "account_locked")
    private boolean accountLocked = false;
}
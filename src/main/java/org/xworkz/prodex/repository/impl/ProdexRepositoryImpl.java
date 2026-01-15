package org.xworkz.prodex.repository.impl;

import org.springframework.stereotype.Repository;
import org.xworkz.prodex.entity.AdministratorEntity;
import org.xworkz.prodex.entity.EnrollmentEntity;
import org.xworkz.prodex.entity.MemberEntity;
import org.xworkz.prodex.repository.ProdexRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
public class ProdexRepositoryImpl implements ProdexRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean saveEnrollment(EnrollmentEntity enrollmentEntity) {
        try {
            entityManager.persist(enrollmentEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public EnrollmentEntity findEnrollmentByEmail(String email) {
        try {
            return entityManager.createNamedQuery("EnrollmentEntity.findByEmail", EnrollmentEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EnrollmentEntity findEnrollmentByContact(String contact) {
        try {
            return entityManager.createNamedQuery("EnrollmentEntity.findByContact", EnrollmentEntity.class)
                    .setParameter("contact", contact)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EnrollmentEntity findEnrollmentEntityByEmailOrContact(String emailOrContact) {
        try {
            return entityManager.createNamedQuery("EnrollmentEntity.findByEmailOrContact", EnrollmentEntity.class)
                    .setParameter("identifier", emailOrContact)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean saveMember(MemberEntity member) {
        try {
            entityManager.persist(member);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveAdministrator(AdministratorEntity admin) {
        try {
            entityManager.persist(admin);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateEnrollment(EnrollmentEntity enrollmentEntity) {
        try {
            entityManager.merge(enrollmentEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<EnrollmentEntity> findByOtpNotNullAndOtpCreatedAtBefore(LocalDateTime expiryLimit) {
        try {
            return entityManager.createNamedQuery("EnrollmentEntity.findExpiredOtps", EnrollmentEntity.class)
                    .setParameter("expiryLimit", expiryLimit)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
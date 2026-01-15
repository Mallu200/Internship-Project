package org.xworkz.prodex.repository.impl;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xworkz.prodex.entity.EnrollmentEntity;
import org.xworkz.prodex.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private EntityManagerFactory factory;

    private String processSearchParam(String param) {
        return (param == null || param.trim().isEmpty()) ? null : "%" + param.trim() + "%";
    }

    private Boolean processLockedStatusParam(String param) {
        if (param == null || param.trim().isEmpty()) {
            return null; // This allows "All Statuses" to work
        }
        return Boolean.valueOf(param.trim()); // Converts "true" to true, "false" to false
    }

    @Override
    public List<EnrollmentEntity> findAllMembers() {
        EntityManager manager = factory.createEntityManager();

        try {
            List<EnrollmentEntity> result = manager.createNamedQuery("EnrollmentEntity.findAllMembers", EnrollmentEntity.class)
                    .getResultList();
            return result;
        } catch (Exception e) {
            return Collections.emptyList();
        } finally {
            manager.close();
        }
    }

    @Override
    public List<EnrollmentEntity> searchMembers(String userName, String email, String contact, boolean lockedStatus) {
        EntityManager manager = factory.createEntityManager();

        try {
            String nameParam = processSearchParam(userName);
            String emailParam = processSearchParam(email);
            String contactParam = processSearchParam(contact);

            List<EnrollmentEntity> result = manager.createNamedQuery("EnrollmentEntity.searchMembers", EnrollmentEntity.class)
                    .setParameter("name", nameParam)
                    .setParameter("email", emailParam)
                    .setParameter("contact", contactParam)
                    .setParameter("lockedStatus", lockedStatus)
                    .getResultList();
            return result;
        } catch (Exception e) {
            return Collections.emptyList();
        } finally {
            manager.close();
        }
    }

    @Override
    public EnrollmentEntity findMemberByEmail(String email) {
        EntityManager manager = factory.createEntityManager();

        try {
            EnrollmentEntity result = (EnrollmentEntity) manager.createNamedQuery("EnrollmentEntity.findMemberByEmail")
                    .setParameter("email", email)
                    .getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public EnrollmentEntity findMemberByContact(String contact) {
        EntityManager manager = factory.createEntityManager();

        try {
            EnrollmentEntity result = (EnrollmentEntity) manager.createNamedQuery("EnrollmentEntity.findMemberByContact")
                    .setParameter("contact", contact)
                    .getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public EnrollmentEntity getMemberById(Long enrollmentId) {
        EntityManager manager = factory.createEntityManager();

        try {
            EnrollmentEntity entity = manager.find(EnrollmentEntity.class, enrollmentId);
            return entity;
        } catch (Exception e) {
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public boolean updateMember(EnrollmentEntity existingEntity) {
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();
            manager.merge(existingEntity);
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            manager.close();
        }
    }

    @Override
    public int deleteMember(Long enrollmentId) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            int deletedCount = manager.createNamedQuery("EnrollmentEntity.deleteMember")
                    .setParameter("enrollmentId", enrollmentId)
                    .executeUpdate();
            manager.getTransaction().commit();
            return deletedCount;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback(); 
            }
            e.printStackTrace();
            return 0;
        } finally {
            manager.close();
        }
    }
    @Override
    public long countAllMembers() {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("EnrollmentEntity.countAllMembers", Long.class)
                    .setHint("javax.persistence.cache.retrieveMode", "BYPASS")
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        } finally {
            manager.close();
        }
    }

    @Override
    public List<EnrollmentEntity> findPaginatedMembers(int startIndex, int pageSize) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("EnrollmentEntity.findPaginatedMembers", EnrollmentEntity.class)
                    .setFirstResult(startIndex)
                    .setMaxResults(pageSize)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        } finally {
            manager.close();
        }
    }

    @Override
    public long countMembersByCriteria(String userName, String email, String contactNumber, String accountLocked) {
        EntityManager manager = factory.createEntityManager();
        try {
            String nameParam = processSearchParam(userName);
            String emailParam = processSearchParam(email);
            String contactParam = processSearchParam(contactNumber);
            Boolean lockedParam = processLockedStatusParam(accountLocked);

            return manager.createNamedQuery("EnrollmentEntity.countMembersByCriteria", Long.class)
                    .setParameter("name", nameParam)
                    .setParameter("email", emailParam)
                    .setParameter("contact", contactParam)
                    .setParameter("lockedStatus", lockedParam)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        } finally {
            manager.close();
        }
    }

    @Override
    public List<EnrollmentEntity> searchPaginatedMembers(String userName, String email, String contactNumber, String accountLocked, int startIndex, int pageSize) {
        EntityManager manager = factory.createEntityManager();
        try {
            String name = (userName != null && !userName.isEmpty()) ? "%" + userName + "%" : null;
            String mail = (email != null && !email.isEmpty()) ? "%" + email + "%" : null;
            String cont = (contactNumber != null && !contactNumber.isEmpty()) ? "%" + contactNumber + "%" : null;

            String queryName;

            if (accountLocked == null || accountLocked.isEmpty()) {
                queryName = "EnrollmentEntity.searchMembersWithoutStatus";
            } else {
                queryName = "EnrollmentEntity.searchPaginatedMembers";
            }

            var query = manager.createNamedQuery(queryName, EnrollmentEntity.class)
                    .setParameter("name", name)
                    .setParameter("email", mail)
                    .setParameter("contact", cont);

            if (accountLocked != null && !accountLocked.isEmpty()) {
                query.setParameter("lockedStatus", Boolean.parseBoolean(accountLocked));
            }

            return query.setFirstResult(startIndex)
                    .setMaxResults(pageSize)
                    .getResultList();
        } finally {
            manager.close();
        }
    }
}
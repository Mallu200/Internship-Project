package org.xworkz.prodex.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xworkz.prodex.entity.CustomerEntity;
import org.xworkz.prodex.enums.CustomerType;
import org.xworkz.prodex.repository.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private EntityManagerFactory factory;

    @Override
    public boolean saveCustomer(CustomerEntity entity) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) manager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            manager.close();
        }
    }

    @Override
    public CustomerEntity findCustomerById(Long customerId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.find(CustomerEntity.class, customerId);
        } finally {
            manager.close();
        }
    }

    @Override
    public CustomerEntity findCustomerByEmail(String email) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("CustomerEntity.findCustomerByEmail", CustomerEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public CustomerEntity findCustomerByContact(String contact) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("CustomerEntity.findCustomerByContact", CustomerEntity.class)
                    .setParameter("contact", contact)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public CustomerEntity findCustomerByTaxId(String taxId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("CustomerEntity.findCustomerByTaxId", CustomerEntity.class)
                    .setParameter("taxId", taxId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public List<CustomerEntity> findAllCustomers() {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("CustomerEntity.findPaginated", CustomerEntity.class)
                    .getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public List<CustomerEntity> findPaginatedCustomers(int start, int pageSize) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("CustomerEntity.findPaginated", CustomerEntity.class)
                    .setFirstResult(start)
                    .setMaxResults(pageSize)
                    .getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public long countAllCustomers() {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("CustomerEntity.countAll", Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            return 0L;
        } finally {
            manager.close();
        }
    }

    @Override
    public List<CustomerEntity> searchCustomers(String name, CustomerType type, String email, String contact) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("CustomerEntity.searchPaginatedByAllFields", CustomerEntity.class)
                    .setParameter("customerName", name)
                    .setParameter("customerType", type)
                    .setParameter("email", email)
                    .setParameter("contact", contact)
                    .getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public List<CustomerEntity> searchPaginatedCustomers(String name, CustomerType type, String email, String contact, int start, int pageSize) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("CustomerEntity.searchPaginatedByAllFields", CustomerEntity.class)
                    .setParameter("customerName", name)
                    .setParameter("customerType", type)
                    .setParameter("email", email)
                    .setParameter("contact", contact)
                    .setFirstResult(start)
                    .setMaxResults(pageSize)
                    .getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public long countSearchCustomers(String name, CustomerType type, String email, String contact) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createNamedQuery("CustomerEntity.countSearchByAllFields", Long.class)
                    .setParameter("customerName", name)
                    .setParameter("customerType", type)
                    .setParameter("email", email)
                    .setParameter("contact", contact)
                    .getSingleResult();
        } catch (Exception e) {
            return 0L;
        } finally {
            manager.close();
        }
    }

    @Override
    public boolean updateCustomer(CustomerEntity existingEntity) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(existingEntity);
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) manager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            manager.close();
        }
    }

    @Override
    public int deleteCustomerById(Long customerId) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            int deletedCount = manager.createNamedQuery("CustomerEntity.deleteCustomerById")
                    .setParameter("customerId", customerId)
                    .executeUpdate();
            manager.getTransaction().commit();
            return deletedCount;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) manager.getTransaction().rollback();
            e.printStackTrace();
            return 0;
        } finally {
            manager.close();
        }
    }
}
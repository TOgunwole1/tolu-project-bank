package com.mudson.repository;

import com.mudson.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional
public class UserRepoImpl implements UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findByFirstName(String firstName) {
        return entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.firstName = :firstName", User.class)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    @Override
    public Boolean existsByEmail(String email) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Boolean existsByPhone(String phone) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.phone = :phone", Long.class)
                .setParameter("phone", phone)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Boolean existsByAddress(String address) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.address = :address", Long.class)
                .setParameter("address", address)
                .getSingleResult();
        return count > 0;
    }
    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public <S extends User> S save(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        for (S entity : entities) {
            entityManager.persist(entity);
        }
        entityManager.flush();
        // This is not an ideal implementation, but it satisfies the interface.
        return (List<S>) entities;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {
        for (User entity : entities) {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        }
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        for (Long id : longs) {
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);
            }
        }
    }

    @Override
    public void deleteAllInBatch() {
        entityManager.createQuery("DELETE FROM User").executeUpdate();
    }

    @Override
    @Deprecated
    public User getOne(Long aLong) {
        return entityManager.find(User.class, aLong);
    }

    @Override
    public User getById(Long aLong) {
        return entityManager.find(User.class, aLong);
    }

    @Override
    public User getReferenceById(Long aLong) {
        return entityManager.getReference(User.class, aLong);
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
         for (S entity : entities) {
            entityManager.persist(entity);
        }
        return (List<S>) entities;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.ofNullable(entityManager.find(User.class, aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return findById(aLong).isPresent();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.id IN :ids", User.class)
                .setParameter("ids", longs)
                .getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
    }

    @Override
    public void deleteById(Long aLong) {
        User user = entityManager.find(User.class, aLong);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
         for (Long id : longs) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        for (User entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM User").executeUpdate();
    }

    @Override
    public List<User> findAll(Sort sort) {
        // Basic implementation, doesn't handle sort.
        return findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        // Not implemented
        return null;
    }
}

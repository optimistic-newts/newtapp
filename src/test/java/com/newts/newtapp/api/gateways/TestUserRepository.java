package com.newts.newtapp.api.gateways;

import com.newts.newtapp.entities.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

/**
 * A mock UserRepository implemented for testing purposes.
 */
@SuppressWarnings({"NullableProblems", "SpringDataMethodInconsistencyInspection", "SpringDataRepositoryMethodParametersInspection"})
@Configuration
@ConditionalOnMissingBean
public class TestUserRepository implements UserRepository {
    /**
     * A map from user id to user entity to be used in this repository.
     */
    private final HashMap<Integer, User> users;

    /**
     * Create a new TestUserRepository.
     */
    public TestUserRepository(){
        this.users = new HashMap<>();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        users.remove(integer);
    }

    @Override
    public void delete(User entity) {
        users.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends User> S save(S entity) {
        if (entity.getId() == 0) {
            int newId = 1;
            while (users.containsKey(newId)) {
                newId++;
            }
            entity.setId(newId);
        }
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        if (users.containsKey(integer)) {
            return Optional.of(users.get(integer));
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Integer integer) {
        return null;
    }

    @Override
    public User getById(Integer integer) {
       return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
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
    public Optional<User> findByUsername(String username) {
        for(User user:users.values()){
            if(Objects.equals(user.getUsername(), username)){
                return Optional.of(users.get(user.getId()));
            }
        }
        return Optional.empty();
    }
}

package com.newts.newtapp.api.gateways;

import com.newts.newtapp.entities.Message;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * A mock MessageRepository implemented for testing purposes.
 */
@SuppressWarnings({"NullableProblems", "SpringDataMethodInconsistencyInspection", "SpringDataRepositoryMethodParametersInspection"})
// We suppress warnings for all of the test repositories. We are not worried about
// this particular warning.
@Configuration
@ConditionalOnMissingBean
public class TestMessageRepository implements MessageRepository{
    /**
     * A map message id to message entity acting as the persistence layer in our application.
     */
    private final HashMap<Integer, Message> messages;

    /**
     * Create a new TestMessageRepository
     */
    public TestMessageRepository() { this.messages = new HashMap<>(); }

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public List<Message> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Message> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Message entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Message> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Message> S save(S entity) {
        if (entity.getId() == 0) {
            int newId = 1;
            while (messages.containsKey(newId)) {
                newId++;
            }
            entity.setId(newId);
        }
        messages.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Message> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Message> findById(Integer integer) {
        if (messages.containsKey(integer)) {
            return Optional.of(messages.get(integer));
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
    public <S extends Message> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Message> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Message> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Message getOne(Integer integer) {
        return null;
    }

    @Override
    public Message getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Message> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Message> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Message> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Message> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Message> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Message> boolean exists(Example<S> example) {
        return false;
    }
}

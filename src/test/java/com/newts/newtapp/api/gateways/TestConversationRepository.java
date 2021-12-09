package com.newts.newtapp.api.gateways;

import com.newts.newtapp.entities.Conversation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * A mock ConversationRepository implemented for testing purposes.
 */
@SuppressWarnings({"NullableProblems", "SpringDataMethodInconsistencyInspection", "SpringDataRepositoryMethodParametersInspection"})
@Configuration
@ConditionalOnMissingBean
public class TestConversationRepository implements ConversationRepository{
    /**
     * A map from conversation id to conversation entity acting as the persistence layer in our application.
     */
    private final HashMap<Integer, Conversation> conversations;

    /**
     * Create a new TestConversationRepository.
     */
    public TestConversationRepository(){
        this.conversations = new HashMap<>();
    }

    @Override
    public ArrayList<Conversation> findAll() {
        return new ArrayList<>(conversations.values());
    }

    @Override
    public List<Conversation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Conversation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Conversation> findAllById(Iterable<Integer> integers) {
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
    public void delete(Conversation entity) {

        conversations.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Conversation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Conversation> S save(S entity) {
        if (entity.getId() == 0) {
            int newId = 1;
            while (conversations.containsKey(newId)) {
                newId++;
            }
            entity.setId(newId);
        }
        conversations.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Conversation> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Conversation> findById(Integer integer) {
        if (conversations.containsKey(integer)) {
            return Optional.of(conversations.get(integer));
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
    public <S extends Conversation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Conversation> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Conversation> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Conversation getOne(Integer integer) {
        return null;
    }

    @Override
    public Conversation getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Conversation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Conversation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Conversation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Conversation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Conversation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Conversation> boolean exists(Example<S> example) {
        return false;
    }
}

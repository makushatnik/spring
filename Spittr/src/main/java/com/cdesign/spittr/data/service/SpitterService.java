package com.cdesign.spittr.data.service;

import com.cdesign.spittr.data.entity.Spitter;
import com.cdesign.spittr.data.repo.SpitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Ageev Evgeny on 29.08.2016.
 */
@Service
public class SpitterService {

    @Autowired
    private SpitterRepository spitterRepository;

    private TransactionTemplate txTemplate;

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Page<Spitter> findUsers(int page, int size) {
        return spitterRepository.findAll(new PageRequest(page, size, sortByUserName()));
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Page<Spitter> findActiveUsers(int page, int size) {
        return spitterRepository.findAllByActiveIsTrue(new PageRequest(page, size, sortByUserName()));
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Page<Spitter> findBlockedUsers(int page, int size) {
        return spitterRepository.findAllByBlockedIsTrue(new PageRequest(page, size, sortByUserName()));
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Spitter findOne(Long spitterId) {
        return spitterRepository.findOne(spitterId);
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Spitter findByEmail(String email) {
        return spitterRepository.findByEmail(email);
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Spitter findByUserName(String username) {
        return spitterRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean userExists(Long id) {
        return spitterRepository.spitterExists(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public int countActiveSpitters() {
        return spitterRepository.countByActiveIsTrue();
    }

    @Transactional(readOnly = true)
    public int countAll() {
        return txTemplate.execute((s) -> (int)em.createNativeQuery("Spitter.CountAll", Long.class).getSingleResult());
    }

    @Transactional
    public void save(Spitter spitter) {
        spitterRepository.save(spitter);
    }

    @Transactional
    public void delete(Long id) { spitterRepository.delete(id); }

    private Sort sortByUserName() {
        return new Sort(Sort.Direction.ASC, "username");
    }
}

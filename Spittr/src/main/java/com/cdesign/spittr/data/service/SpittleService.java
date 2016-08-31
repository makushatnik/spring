package com.cdesign.spittr.data.service;

import com.cdesign.spittr.data.entity.Spittle;
import com.cdesign.spittr.data.repo.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ageev Evgeny on 30.08.2016.
 */
@Service
public class SpittleService {

    @Autowired
    private SpittleRepository spittleRepository;

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Page<Spittle> findAll(int page, int size) {
        return spittleRepository.findAll(new PageRequest(page, size, sortByTime()));
    }

    @Transactional(readOnly = true)
    public Page<Spittle> findBySpitterId(Long spitterId, int page, int size) {
        return spittleRepository.findAllById(spitterId, new PageRequest(page, size, sortByTime()));
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @PostAuthorize("(returnObject.spitter.username == principal.username)" +
            "or hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    public Spittle findOne(Long spittleId) {
        return spittleRepository.findOne(spittleId);
    }

    @Transactional
    @PreAuthorize("(hasRole('ROLE_SPITTER') and #spittle.message.length <= 140)" +
            "or hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    public Spittle addSpittle(Spittle spittle) {
        return spittleRepository.save(spittle);
    }

    @Transactional
    @PreAuthorize("(returnObject.spitter.username == principal.username)" +
            "or hasRole('ROLE_ADMIN')")
    public Spittle updateSpittle(Spittle spittle) {
        return spittleRepository.save(spittle);
    }

    @Transactional
    @PreAuthorize("(returnObject.spitter.username == principal.username)" +
            "or hasRole('ROLE_ADMIN')")
    public void delete(Long id) { spittleRepository.delete(id); }

    private Sort sortByTime() {
        return new Sort(Sort.Direction.DESC, "time");
    }
}

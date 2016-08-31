package com.cdesign.spittr.data.repo;

import com.cdesign.spittr.data.entity.Spitter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Ageev Evgeny on 28.08.2016.
 */
@Repository
public interface SpitterRepository extends PagingAndSortingRepository<Spitter, Long> {

    Spitter findByEmail(String email);

    Spitter findByUsername(String username);

    @Cacheable("spitterCache")
    Page<Spitter> findAllByActiveIsTrue(PageRequest req);

    Page<Spitter> findAllByBlockedIsTrue(PageRequest req);

    Page<Spitter> findByTimeBetween(Date start, Date end);

    @Query("select s from Spitter s WHERE s.email LIKE '%gmail.com'")
    Page<Spitter> findAllGmailSpitters();

    int countByActiveIsTrue();

    @Query("SELECT COUNT(u.id) > 0 as val FROM Spitter s WHERE s.id = ?1")
    boolean spitterExists(Long spitterId);

    @Override
    @Cacheable(value = "spitterCache", condition = "#result.active == true")
    Spitter findOne(Long id);

    @Override
    @CachePut(value = "spitterCache", key = "#result.id")
    Spitter save(Spitter spitter);

    @Override
    @CacheEvict("spitterCache")
    void delete(Long spittleId);
}

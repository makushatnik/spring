package com.cdesign.spittr.data.repo;

import com.cdesign.spittr.data.entity.Spittle;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Ageev Evgeny on 28.08.2016.
 */
public interface SpittleRepository extends PagingAndSortingRepository<Spittle, Long> {

    Page<Spittle> findAllById(Long id, PageRequest pr);

    @Override
    @Cacheable("spittleCache")
    Spittle findOne(Long id);

    @Override
    @CachePut("spittleCache")
    Spittle save(Spittle spittle);

    @Override
    @CacheEvict("spittleCache")
    void delete(Long spittleId);
}

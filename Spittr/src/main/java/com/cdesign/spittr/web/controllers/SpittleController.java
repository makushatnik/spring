package com.cdesign.spittr.web.controllers;

import com.cdesign.spittr.config.ConstantManager;
import com.cdesign.spittr.data.entity.Spittle;
import com.cdesign.spittr.data.service.SpittleService;
import com.cdesign.spittr.web.response.MessageResponse;
import com.cdesign.spittr.web.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Ageev Evgeny on 28.08.2016.
 */
@RestController
@RequestMapping("/spittles")
public class SpittleController {

    @Autowired
    private SpittleService spittleService;

    @RequestMapping(method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<Spittle> spittles (
            @RequestParam(value="spit_id",required = false) Long spitterId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = ConstantManager.DEFAULT_PAGE_SIZE) int size) throws Exception {
        final Page<Spittle> p;
        if (spitterId == null) {
            p = spittleService.findAll(page, size);
        } else {
            p = spittleService.findBySpitterId(spitterId, page, size);
        }

        return new PageResponse<>(
                p.getContent(),
                p.getTotalPages(),
                p.getTotalElements(),
                p.isFirst(),
                p.isLast(),
                p.hasNext(),
                p.hasPrevious());
    }

    //@Scheduled()
    @RequestMapping(value = "/{spittleId}", method = GET)
    public ResponseEntity<?> showSpittle(@PathVariable long spittleId) throws Exception {
        Spittle spittle = spittleService.findOne(spittleId);
        if (spittle == null) {
            throw new EntityNotFoundException("Spittle with id - " + spittleId + " not found");
//            ErrorResponse error = new ErrorResponse(404, "", "Spittle " + spittleId + " not found");
//            return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Spittle>(spittle, HttpStatus.OK);
    }

    @RequestMapping(method = POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Spittle> addSpittle(@RequestBody Spittle spittle,
                                               UriComponentsBuilder ucb) throws Exception {
        spittle = spittleService.addSpittle(spittle);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/spittle/")
                .path(String.valueOf(spittle.getId()))
                .build()
                .toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<Spittle>(spittle, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{spittleId}", method = PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Spittle updateSpittle(@PathVariable long spittleId, Spittle newSpittle) {
        Spittle spittle = spittleService.findOne(spittleId);
        if (spittle == null) {
            throw new EntityNotFoundException("Spittle with id - " + spittleId + " not found");
        }
        spittle = spittleService.updateSpittle(newSpittle);
        return spittle;
    }

    @RequestMapping(value = "/{spittleId}", method = DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse delete(@PathVariable long spittleId) {
        spittleService.delete(spittleId);
        return new MessageResponse("Deleted");
    }
}

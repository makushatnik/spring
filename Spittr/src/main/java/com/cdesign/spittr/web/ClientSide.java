package com.cdesign.spittr.web;

import com.cdesign.spittr.config.ConstantManager;
import com.cdesign.spittr.data.entity.Spitter;
import com.cdesign.spittr.data.entity.Spittle;
import com.cdesign.spittr.exceptions.NotModifiedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Ageev Evgeny on 31.08.2016.
 * Client Side Stub
 */
public class ClientSide {

    public Spittle fetchSpittle(long id) {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Spittle> response = rest.getForEntity(ConstantManager.BASE_URL +
                "/spittles/{id}", Spittle.class, id);
        if (response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
            throw new NotModifiedException("Spittle witH id - " + id + " is not modified.");
        }
        return response.getBody();
    }

    public void updateSpittle(Spittle spittle) throws Exception{
        RestTemplate rest = new RestTemplate();
        rest.put(ConstantManager.BASE_URL + "/spittles/{id}", spittle, spittle.getId());
    }

    public void deleteSpittle(Long id) {
        RestTemplate rest = new RestTemplate();
        rest.delete(ConstantManager.BASE_URL + "/spittles/{id}", id);
    }

    public Spitter postSpitterForObject(Spitter spitter) {
        RestTemplate rest = new RestTemplate();
        return rest.postForObject(ConstantManager.BASE_URL + "/spitters", spitter, Spitter.class);
    }

    public String postSpitter(Spitter spitter) {
        RestTemplate rest = new RestTemplate();
        return rest.postForLocation(ConstantManager.BASE_URL + "/spitters", spitter).toString();
    }
}

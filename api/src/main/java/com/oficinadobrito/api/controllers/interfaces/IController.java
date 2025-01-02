package com.oficinadobrito.api.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.List;

public interface IController <T,C,U> {
    ResponseEntity<?> postResource(@RequestBody C resource);
    ResponseEntity<?> getResourceById(@PathVariable("id") BigInteger id);
    ResponseEntity<List<T>> getAllResource();
    ResponseEntity<?> updateResource(@PathVariable("id") BigInteger id , @RequestBody U resource);
    ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id);
}

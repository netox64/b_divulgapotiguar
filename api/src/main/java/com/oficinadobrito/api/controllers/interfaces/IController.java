package com.oficinadobrito.api.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.List;

public interface IController <T,C,U> {
    ResponseEntity<T> postResource(@RequestBody C resource);
    ResponseEntity<T> getResourceById(@PathVariable("id") BigInteger id);
    ResponseEntity<List<T>> getAllResource();
    ResponseEntity<T> updateResource(@PathVariable("id") BigInteger id , @RequestBody U resource);
    ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id);
}

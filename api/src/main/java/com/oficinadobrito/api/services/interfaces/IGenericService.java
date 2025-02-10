package com.oficinadobrito.api.services.interfaces;

import java.math.BigInteger;

public interface IGenericService<T> {
    T save(T resource);
    T findById(BigInteger id);
    Iterable<T> findAll();
    void delete(BigInteger id);
}

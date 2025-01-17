package com.oficinadobrito.api.services.interfaces;

import java.math.BigInteger;

public interface IGenericService<T> {
    T save(T resource);
    T findById(BigInteger id);
    Iterable<T> findAll();
    T updateById(BigInteger id,T resource);
    void delete(BigInteger id);
}

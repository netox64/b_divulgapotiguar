package com.oficinadobrito.api.services;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import com.oficinadobrito.api.services.interfaces.IGenericService;

import java.math.BigInteger;

public class GenericService <T> implements IGenericService<T> {

    protected final IGenericRepository<T> repository;
    private static final String MESSAGE = "The searched resource containing this id does not exist.";

    public GenericService(IGenericRepository<T> repository){
        this.repository = repository;
    }

    public T save(T resource){
        return this.repository.save(resource);
    }

    public T findById(BigInteger id) {
        return this.repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(MESSAGE));
    }

    public Iterable<T> findAll() {
        return this.repository.findAll();
    }

    public void delete(BigInteger id) {
        this.repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(MESSAGE));
        this.repository.deleteById(id);
    }
}

package com.oficinadobrito.api.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.math.BigInteger;

@NoRepositoryBean
public interface IGenericRepository<T> extends JpaRepository<T, BigInteger> {
}
package com.example.slmiddleware.service;

public interface EntityMapper <D,E>{

    E toEntity(final D dto);
    D toDto(final E entity);
}

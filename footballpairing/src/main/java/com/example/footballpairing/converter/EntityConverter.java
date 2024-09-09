package com.example.footballpairing.converter;

public interface EntityConverter<T, R, E> {
    T create(R request);
    E toResponse(T entity);
}

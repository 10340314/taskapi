package com.study.taskAPI.dto.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class Mapper<E, D> {
    protected abstract D mapToDTO(E e);
    protected abstract E mapToEntity(D d);

    public D toDTO(E entity) {
        if (entity == null)
            return null;
        return mapToDTO(entity);
    }

    public E toEntity(D dto) {
        if (dto == null)
            return null;
        return mapToEntity(dto);
    }

    public List<D> toDTOList(List<E> entities) {
        if (entities == null)
            return null;
        return entities.stream().map(this::toDTO).toList();
    }

    public List<E> toEntityList(List<D> dtos) {
        if (dtos == null)
            return null;
        return dtos.stream().map(this::toEntity).toList();
    }
}

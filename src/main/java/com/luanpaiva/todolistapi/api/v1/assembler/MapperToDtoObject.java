package com.luanpaiva.todolistapi.api.v1.assembler;

import java.util.List;

public interface MapperToDtoObject<I, O> {
    
    O toDtoObject(I i);
    List<O> toListDtoObject(List<I> i);

}

package com.luanpaiva.todolistapi.api.v1.assembler;

import java.util.List;

public interface MapperToDomainObject<I, O> {
    
    O toDomainObject(I i);
    List<O> toListDomainObject(List<I> i);
    
}

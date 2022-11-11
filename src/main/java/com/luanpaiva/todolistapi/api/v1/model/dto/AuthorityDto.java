package com.luanpaiva.todolistapi.api.v1.model.dto;

import com.luanpaiva.todolistapi.domain.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDto {
    
    private Long id;
    private Role roleName;
}

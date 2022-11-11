package com.luanpaiva.todolistapi.api.v1.model.input;

import com.luanpaiva.todolistapi.domain.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityInput {
    
    private Role roleName;
}

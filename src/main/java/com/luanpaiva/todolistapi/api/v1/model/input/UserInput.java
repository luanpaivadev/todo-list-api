package com.luanpaiva.todolistapi.api.v1.model.input;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {
    
    private String username;
    private String password;
    private Boolean enabled;
    private Collection<AuthorityInput> authorities;
}

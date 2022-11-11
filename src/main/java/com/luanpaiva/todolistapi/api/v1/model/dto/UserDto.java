package com.luanpaiva.todolistapi.api.v1.model.dto;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    
    private Long id;
    private String username;
    private Boolean enabled;
    private Collection<AuthorityDto> authorities;
}

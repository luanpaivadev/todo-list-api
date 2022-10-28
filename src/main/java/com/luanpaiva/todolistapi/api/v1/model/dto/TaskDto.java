package com.luanpaiva.todolistapi.api.v1.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    
    private Long id;
    private String description;
    private Boolean completed;
    private String alarm;

}

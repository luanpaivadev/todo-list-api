package com.luanpaiva.todolistapi.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskInput {
    
    private String description;
    private Boolean completed;
    private String alarm;

}

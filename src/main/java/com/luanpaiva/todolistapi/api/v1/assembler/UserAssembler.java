package com.luanpaiva.todolistapi.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.luanpaiva.todolistapi.api.v1.model.input.UserInput;
import com.luanpaiva.todolistapi.domain.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAssembler implements MapperToDomainObject<UserInput, User> {

    private final ModelMapper modelMapper;

    @Override
    public User toDomainObject(UserInput userInput) {
        return modelMapper.map(userInput, User.class);
    }

    @Override
    public List<User> toListDomainObject(List<UserInput> userInputs) {
        return userInputs.stream().map(user -> modelMapper.map(user, User.class)).collect(Collectors.toList());
    }

}

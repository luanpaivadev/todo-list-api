package com.luanpaiva.todolistapi.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.luanpaiva.todolistapi.api.v1.model.dto.UserDto;
import com.luanpaiva.todolistapi.domain.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDisassembler implements MapperToDtoObject<User, UserDto> {

    private final ModelMapper modelMapper;

    @Override
    public UserDto toDtoObject(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> toListDtoObject(List<User> users) {
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

}

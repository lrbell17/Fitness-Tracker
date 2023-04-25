package com.lrbell.fitness.api.controller;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import com.lrbell.fitness.api.responses.AbstractResponseBody;
import com.lrbell.fitness.api.responses.OkResponseBody;
import com.lrbell.fitness.api.responses.exceptions.UserNameNotFoundException;
import com.lrbell.fitness.api.responses.exceptions.UserNotFoundException;
import com.lrbell.fitness.api.helpers.mappers.UserMapper;
import com.lrbell.fitness.api.helpers.dto.UserDto;
import com.lrbell.fitness.api.responses.ResponseMessage;
import com.lrbell.fitness.model.User;
import com.lrbell.fitness.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController implements UpdatableEntityController<User, UserDto> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/name/{userName}")
    public ResponseEntity<GenericDto> getByUserName(@PathVariable(value = "userName") final String userName) {
        final User user = userRepository.findDistinctByUserName(userName);

        if (Objects.isNull(user)) {
            throw new UserNameNotFoundException(userName);
        } else {
            return ResponseEntity.ok().body(userMapper.entityToDto(user));
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GenericDto> getById(@PathVariable(value = "id") final String id) {
        final Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok().body(userMapper.entityToDto(user.get()));
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<AbstractResponseBody> create(@RequestBody final User user) {
        user.setCreatedAt(System.currentTimeMillis());
        userRepository.save(user);
        return ResponseEntity.ok().body(new OkResponseBody(ResponseMessage.USER_CREATED, user.getUserId()));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<AbstractResponseBody> update(@PathVariable(value = "id") final String id,
                                                   @RequestBody final UserDto userDto) {
        final Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            final User userToUpdate = existingUser.get();
            userToUpdate.setUpdatedAt(System.currentTimeMillis());
            userMapper.updateFromDto(userDto, userToUpdate);
            userRepository.save(userToUpdate);
            return ResponseEntity.ok().body(new OkResponseBody(ResponseMessage.USER_UPDATED, id));
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<AbstractResponseBody> delete(@PathVariable(value = "id") final String id) {

        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().body(new OkResponseBody(ResponseMessage.USER_DELETED, id));
        } else {
            throw new UserNotFoundException(id);
        }
    }
}

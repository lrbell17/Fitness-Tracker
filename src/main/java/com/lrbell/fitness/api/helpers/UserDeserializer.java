package com.lrbell.fitness.api.helpers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lrbell.fitness.api.responses.exceptions.UserNotFoundException;
import com.lrbell.fitness.model.User;
import com.lrbell.fitness.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

public class UserDeserializer extends JsonDeserializer<User> {

    public static final String USER_NOT_FOUND_DUMMY_NAME = "NOT_FOUND";

    @Autowired
    private UserRepository userRepository;

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException, UserNotFoundException{
        final String userId = jp.getText();
        final Optional<User> user =  userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get();
        } else {
            final User notFoundUser = new User();
            notFoundUser.setUserId(userId);
            notFoundUser.setUserName(USER_NOT_FOUND_DUMMY_NAME);
            return notFoundUser;
        }
    }
}

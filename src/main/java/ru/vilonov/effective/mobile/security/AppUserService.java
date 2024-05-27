package ru.vilonov.effective.mobile.service;

import org.springframework.stereotype.Service;
import ru.vilonov.effective.mobile.controller.dto.embedded.UserDto;
import ru.vilonov.effective.mobile.model.AppUser;

@Service
public interface AppUserService {
    AppUser createUser(UserDto user);

    AppUser findByUsername(String username);
}

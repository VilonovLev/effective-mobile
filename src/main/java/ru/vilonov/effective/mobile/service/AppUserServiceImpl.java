package ru.vilonov.effective.mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vilonov.effective.mobile.controller.dto.embedded.UserDto;
import ru.vilonov.effective.mobile.model.AppUser;
import ru.vilonov.effective.mobile.repository.AppUserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    public final AppUserRepository appUserRepository;

    @Override
    public AppUser createUser(UserDto user) {
        return this.appUserRepository.save(AppUser.builder()
                .username(user.username())
                .password("{noop}" + user.password())
                .build());
    }

    @Override
    @Transactional
    public AppUser findByUsername(String username) {
        return this.appUserRepository.findByUsername(username).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("load user by username");
        UserDetails userDetails = this.appUserRepository
                .findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles("user")
                        .build())
                .orElseThrow(() -> {
                    log.warn("{} user not found", username);
                    throw  new UsernameNotFoundException("${error.user.not_found}".formatted(username));
                });
        return userDetails;
    }
}

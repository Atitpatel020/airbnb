package com.schedular.service;

import com.schedular.entity.CurrentUserSession;
import com.schedular.entity.UserRegistration;
import com.schedular.payload.LoginDto;
import com.schedular.payload.UserDto;
import com.schedular.repository.CurrentUserSessionRepository;
import com.schedular.repository.UserRegistrationRepository;
import com.schedular.utill.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserRegistrationService {
    private ModelMapper modelMapper;
    private UserRegistrationRepository userRegistrationRepository;
    private JwtService jwtService;
    private CurrentUserSessionRepository sessionRepository;
    public UserRegistrationService(ModelMapper modelMapper, UserRegistrationRepository userRegistrationRepository, JwtService jwtService, CurrentUserSessionRepository sessionRepository) {
        this.modelMapper = modelMapper;
        this.userRegistrationRepository = userRegistrationRepository;
        this.jwtService = jwtService;
        this.sessionRepository = sessionRepository;
    }

    public UserRegistration saveRegistration(UserDto userDto) {
        Optional<UserRegistration> opUser = userRegistrationRepository.findByUserName(userDto.getUserName());

        if (!opUser.isPresent()) { // Correcting the null check
            UserRegistration user = mapToEntity(userDto);
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
            UserRegistration saveUser = userRegistrationRepository.save(user);
            return saveUser;
        }

        return null;
    }

    // String token = jwtService.createToken(user);
    UserRegistration mapToEntity(UserDto userDto) {
        UserRegistration registration = modelMapper.map(userDto, UserRegistration.class);
        return registration;
    }

    UserDto mapToDto(UserRegistration userRegistration) {
        UserDto dto = modelMapper.map(userRegistration, UserDto.class);
        return dto;
    }

    public String verifyLogin(LoginDto loginDto) throws Exception {
        Optional<UserRegistration> opUser = userRegistrationRepository.findByUserName(loginDto.getUserName());
        if (opUser.isPresent()) {
            UserRegistration user = opUser.get();
            if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {

                Optional<CurrentUserSession> byUserId = sessionRepository.findById(user.getId());
                if(byUserId.isPresent()){
                    throw new Exception("User Already logged in");
                }
                String jwtToken = jwtService.createToken(user);
                CurrentUserSession currentUserSession = new CurrentUserSession(
                        user.getId(),
                        jwtToken,
                        LocalDateTime.now()
                );
                currentUserSession.setId(user.getId());
                sessionRepository.save(currentUserSession);
            }
        }
        return null;
    }
}

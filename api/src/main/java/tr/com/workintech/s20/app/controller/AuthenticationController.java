package tr.com.workintech.s20.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tr.com.workintech.s20.app.dto.*;
import tr.com.workintech.s20.app.exception.AuthenticationException;
import tr.com.workintech.s20.app.exception.UserAlreadyExistsException;
import tr.com.workintech.s20.app.service.UserService;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) throws UserAlreadyExistsException {

        log.debug("Received register request: {}", request);

        userService.register(request.email(), request.password());

        log.debug("User registered.");

        return new RegisterResponse();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws AuthenticationException {

        log.debug("Received login request: {}", request);

        String token = userService.login(request.email(), request.password());

        log.debug("User logged in.");

        return new LoginResponse(token);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse handleUserAlreadyExistsException() {

        return new ErrorResponse("Bu email adresi ile kayıtlı bir kullanıcı var");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(AuthenticationException ex) {

        return new ErrorResponse("Giriş başarısız");
    }
}

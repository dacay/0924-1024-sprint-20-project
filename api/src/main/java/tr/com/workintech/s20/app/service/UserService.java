package tr.com.workintech.s20.app.service;

import tr.com.workintech.s20.app.entity.User;
import tr.com.workintech.s20.app.exception.AuthenticationException;
import tr.com.workintech.s20.app.exception.UserAlreadyExistsException;

public interface UserService {

    User register(String email, String password) throws UserAlreadyExistsException;

    String login(String email, String password) throws AuthenticationException;

    void deactive(Long id);
}

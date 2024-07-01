package by.tms.bookstore.services;

import by.tms.bookstore.dto.UserDto;
import by.tms.bookstore.models.User;

public interface UserService {

    User save(UserDto userDto);
}

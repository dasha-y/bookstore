package by.tms.bookstore.services;

import by.tms.bookstore.dto.UserDto;
import by.tms.bookstore.models.User;
import by.tms.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;
    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getFullname(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getRole());

        return userRepository.save(user);
    }



    public User getUserById(Long userId){
        return userRepository.findById(userId).get();
    }



}
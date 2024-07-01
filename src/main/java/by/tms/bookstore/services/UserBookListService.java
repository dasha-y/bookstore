package by.tms.bookstore.services;

import by.tms.bookstore.models.UserBookList;
import by.tms.bookstore.repository.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookListService {


    @Autowired
    private UserBookRepository userbook;


    public void saveUserBooks(UserBookList userBookList){


        userbook.save(userBookList);

    }

    public List<UserBookList> getAllUserBooks(){
        return userbook.findAll();
    }


    public List<UserBookList> getUserBooksByUserId(Long userId) {
        return userbook.findByUserId(userId);
    }


    public void deleteById(Long id){
        userbook.deleteById(id);
    }
}
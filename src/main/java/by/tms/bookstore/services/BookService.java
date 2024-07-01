package by.tms.bookstore.services;

import by.tms.bookstore.models.Book;
import by.tms.bookstore.repository.BooksRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class BookService {

    private static final Logger log = LogManager.getLogger(BookService.class);

    @Autowired
    private BooksRepository booksRepository;
    public Book getBookById(Long id){
        log.info( "Find book by id" + id);
        return  booksRepository.findById(id).get();
    }
}

package by.tms.bookstore.repository;

import by.tms.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {
}

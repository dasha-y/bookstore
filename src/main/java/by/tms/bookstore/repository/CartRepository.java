package by.tms.bookstore.repository;

import by.tms.bookstore.models.CartBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartBooks, Long> {
    List<CartBooks> findByUserId(Long userId);
}

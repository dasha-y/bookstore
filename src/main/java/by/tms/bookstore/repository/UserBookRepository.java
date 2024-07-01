package by.tms.bookstore.repository;

import by.tms.bookstore.models.UserBookList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookRepository extends JpaRepository<UserBookList, Long> {
    List<UserBookList> findByUserId(Long userId);
}

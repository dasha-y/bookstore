package by.tms.bookstore.repository;

import by.tms.bookstore.models.UserBookList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookListRepository extends JpaRepository<UserBookList, Long> {
}

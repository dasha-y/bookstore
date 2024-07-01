package by.tms.bookstore.services;

import by.tms.bookstore.models.CartBooks;
import by.tms.bookstore.repository.BooksRepository;
import by.tms.bookstore.repository.CartRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CartService {
    private static final Logger log = LogManager.getLogger(CartService.class);
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BooksRepository booksRepository;
    public void saveCartBooks(CartBooks cartBooks){


        cartRepository.save(cartBooks);

    }

    public List<CartBooks> getAllCartBooks(){
        return cartRepository.findAll();
    }

    public List<CartBooks> getUserCartByUserId(Long userId){
        return cartRepository.findByUserId(userId);
    }

    public void deleteById(Long id){
        cartRepository.deleteById(id);
    }
    public double calculateTotalPrice(Long userId) {
        double totalPrice = 0.0;

        for (CartBooks cartBook : cartRepository.findByUserId(userId)) {
            totalPrice += cartBook.getBook().getPrice();
        }
        log.info( "count total price = " + totalPrice);
        return totalPrice;
    }



}

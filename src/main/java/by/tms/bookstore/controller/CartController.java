package by.tms.bookstore.controller;

import by.tms.bookstore.models.Book;
import by.tms.bookstore.models.CartBooks;
import by.tms.bookstore.models.User;
import by.tms.bookstore.repository.BooksRepository;
import by.tms.bookstore.repository.CartRepository;
import by.tms.bookstore.repository.UserRepository;
import by.tms.bookstore.services.BookService;
import by.tms.bookstore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/books")
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private BookService service;

    @Autowired
    private BooksRepository repo;


    @GetMapping("/cart")
    public String cart(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

        User user = userRepository.findByEmail(userDetails.getUsername());
        List<CartBooks> cart = cartService.getUserCartByUserId(user.getId());
        model.addAttribute("cartBooks",cart);

        double totalPrice = cartService.calculateTotalPrice(user.getId());
        model.addAttribute("totalPrice", totalPrice);

        return "books/cart";
    }

    @RequestMapping("/cart/{id}")
    public String cart(@PathVariable("id") Long id, Principal principal){
        Book b = service.getBookById(id);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userRepository.findByEmail(userDetails.getUsername());

        CartBooks cart = new CartBooks();
        cart.setBook(b);
        cart.setUser(user);
        cartService.saveCartBooks(cart);

        return "redirect:/books/cart";
    }
    @GetMapping("/deleteCartBook/{id}")
    public String deleteBook(@PathVariable("id") Long id){


        cartRepository.deleteById(id);

        return "redirect:/books/cart";
    }
}

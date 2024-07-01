package by.tms.bookstore.controller;

import by.tms.bookstore.dto.BookDto;
import by.tms.bookstore.models.Book;
import by.tms.bookstore.models.User;
import by.tms.bookstore.models.UserBookList;
import by.tms.bookstore.repository.BooksRepository;
import by.tms.bookstore.repository.UserRepository;
import by.tms.bookstore.services.BookService;
import by.tms.bookstore.services.CustomUserDetails;
import by.tms.bookstore.services.UserBookListService;
import by.tms.bookstore.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/books")
//@Log4j2
public class BooksController {
    private static final Logger log = LogManager.getLogger(BookService.class);
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private BooksRepository repo;
    @Autowired
    private UserRepository userRepository;



    @Autowired
    private BookService service;

    @Autowired
    private UserBookListService userBookListService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    private CustomUserDetails customUserDetails;

    @GetMapping("/home")
    public String home(){
        return "books/home";
    }

    @GetMapping({"", "/available"})
    public String showBooksList(Model model){
        List<Book> books = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("books", books);
        return "books/available";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model, Principal principal){
        BookDto bookDto = new BookDto();
        model.addAttribute("bookDto", bookDto);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "books/createBook";
    }

    @PostMapping("/create")
    public String showCreatePage(@Valid @ModelAttribute BookDto bookDto, BindingResult result){
        if(bookDto.getImageFile().isEmpty()){
            result.addError(new FieldError("bookDto", "imageFile", "The image file is required"));
        }
        if (result.hasErrors()){
            return "books/createBook";
        }
        //save image file

        MultipartFile image = bookDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try{
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if(!Files.exists(uploadPath)){
                Files.createDirectory(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setPages(bookDto.getPages());
        book.setPrice(bookDto.getPrice());
        book.setAnnotation(bookDto.getAnnotation());
        book.setImageBook(storageFileName);

        repo.save(book);

        return "redirect:/books";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam Long id){

        try{
            Book book = repo.findById(id).get();
            model.addAttribute("book", book);

            BookDto bookDto = new BookDto();
            bookDto.setName(book.getName());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setGenre(book.getGenre());
            bookDto.setPages(book.getPages());
            bookDto.setPrice(book.getPrice());
            bookDto.setAnnotation(book.getAnnotation());

            model.addAttribute("bookDto", bookDto);

        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/books";
        }
        return "books/editBook";
    }

    @PostMapping("/edit")
    public String updateBook(Model model, @RequestParam Long id,
                             @Valid @ModelAttribute BookDto bookDto, BindingResult result ){

        try{
            Book book = repo.findById(id).get();
            model.addAttribute("book", book);

            if(result.hasErrors()){
                return "books/editBook";
            }
            if(!bookDto.getImageFile().isEmpty()){
                //delete old image
                String uploadFile = "public/images/";
                Path oldImagePath = Paths.get(uploadFile + book.getImageBook());

                try{
                    Files.delete(oldImagePath);
                } catch (Exception ex){
                    System.out.println("Exception: " + ex.getMessage());
                }

                //save new image file
                MultipartFile image = bookDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try(InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(uploadFile + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                book.setImageBook(storageFileName);
            }
            book.setName(bookDto.getName());
            book.setAuthor(bookDto.getAuthor());
            book.setGenre(bookDto.getGenre());
            book.setPages(bookDto.getPages());
            book.setPrice(bookDto.getPrice());
            book.setAnnotation(bookDto.getAnnotation());

            repo.save(book);

        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/books";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam Long id){

        try{
            Book book = repo.findById(id).get();

            //delete book image
            Path imagePath = Paths.get("public/images/" + book.getImageBook());
            try{
                Files.delete(imagePath);
            }catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
            //delete the book
            repo.delete(book);
        }catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        log.info("delete book");
        return "redirect:/books";
    }

    @GetMapping("/user_books")
    public String getMyBooks( Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

        User user = userRepository.findByEmail(userDetails.getUsername());
        // Получаем список книг текущего пользователя
        List<UserBookList> userBooks = userBookListService.getUserBooksByUserId(user.getId());

        model.addAttribute("book", userBooks);
        model.addAttribute("user", userDetails);

        return "books/userBooks";
    }

    @RequestMapping(   "/user_books/{id}")
    public String getUserList(@PathVariable("id") Long id, Principal principal){

        Book b = service.getBookById(id);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userRepository.findByEmail(userDetails.getUsername());


        UserBookList ub = new UserBookList();

        ub.setBook(b);
        ub.setUser(user);


        userBookListService.saveUserBooks(ub);

        return "redirect:/books/user_books";
    }


}

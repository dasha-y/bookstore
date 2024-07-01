package by.tms.bookstore.controller;

import by.tms.bookstore.repository.UserBookRepository;
import by.tms.bookstore.services.UserBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class UserBookController {
    @Autowired
    private UserBookListService userBookListService;

    @Autowired
    private UserBookRepository userBookRepository;

    @GetMapping("/deleteUserBook/{id}")
    public String deleteBook(@PathVariable("id") Long id){


        userBookListService.deleteById(id);

        return "redirect:/books/user_books";
    }
//    @GetMapping("/editUserBook")
//    public String showEditPage(Model model, @RequestParam Long id){
//
//        try{
//            UserBookList book = userBookRepository.findById(id).get();
//            model.addAttribute("book", book);
//
//            BookDto bookDto = new BookDto();
//            bookDto.setName(book.getName());
//            bookDto.setAuthor(book.getAuthor());
//            bookDto.setGenre(book.getGenre());
//            bookDto.setPages(book.getPages());
//            bookDto.setRating(book.getRating());
//            bookDto.setAnnotation(book.getAnnotation());
//
//            bookDto
//
//            model.addAttribute("bookDto", bookDto);
//
//        } catch (Exception ex){
//            System.out.println("Exception: " + ex.getMessage());
//            return "redirect:/books";
//        }
//        return "books/EditBook";
//    }
//    @PostMapping("/editUserBook")
//    public String updateBook(Model model, @RequestParam Long id,
//                             @Valid @ModelAttribute BookDto bookDto, BindingResult result ){
//
//        try{
//            UserBookList book = userBookRepository.findById(id).get();
//            model.addAttribute("book", book);
//
//            if(result.hasErrors()){
//                return "books/editBook";
//            }
//            if(!bookDto.getImageFile().isEmpty()){
//                //delete old image
//                String uploadFile = "public/images/";
//                Path oldImagePath = Paths.get(uploadFile + book.getImageBook());
//
//                try{
//                    Files.delete(oldImagePath);
//                } catch (Exception ex){
//                    System.out.println("Exception: " + ex.getMessage());
//                }
//
//                //save new image file
//                MultipartFile image = bookDto.getImageFile();
//                Date createdAt = new Date();
//                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
//
//                try(InputStream inputStream = image.getInputStream()){
//                    Files.copy(inputStream, Paths.get(uploadFile + storageFileName),
//                            StandardCopyOption.REPLACE_EXISTING);
//                }
//                book.setImageBook(storageFileName);
//            }
//            book.setName(bookDto.getName());
//            book.setAuthor(bookDto.getAuthor());
//            book.setGenre(bookDto.getGenre());
//            book.setPages(bookDto.getPages());
//            book.setRating(bookDto.getRating());
//            book.setAnnotation(bookDto.getAnnotation());
//
//            userBookRepository.save(book);
//
//        } catch (Exception ex){
//            System.out.println("Exception: " + ex.getMessage());
//        }
//
//        return "redirect:/books/user_books";
//    }
}

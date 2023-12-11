package library.controllers.major;

import library.enums.BookCategory;
import library.exception.ResourceNotFoundException;
import library.model.Book;
import library.model.Rental;
import library.repository.BookRepository;
import library.repository.RentalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;
    
    @GetMapping("/books/new")
    public String showAddBookForm(Model model) {
        var book = new Book();
        model.addAttribute("book", book);
        return "book/addBook";
    }
    
    @PostMapping("/books")
    public String addBook(@Valid Book book, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error",
                    "Not valid data passed when trying to add new book! Try again.");
            return "book/addBook";
        }

        bookRepository.save(book);
        redirectAttributes.addFlashAttribute("success", "Successfully added new book with id " + book.getBookId());

        return "redirect:/books/" + book.getBookId();
    }

    @RequestMapping(path = { "/books" })
    public String searchBook(Model model, String title, String categories, String author) {
        Set<Book> selectedBooks = new HashSet<>();
        selectedBooks.addAll((Collection<? extends Book>) bookRepository.findAll());
        
        retrieveDataMatchingFilters(selectedBooks, title, categories, author);
        
        var rentedBookIds = RentalController.retrieveIdsOfRentedBooks(rentalRepository);        
        model.addAttribute("bookIdToCanBookBeRentedMap", 
                makeMappingBookIdToCanBeRented(selectedBooks, rentedBookIds));
        model.addAttribute("allBooks", selectedBooks);

        return "book/listBooks";
    }
    
    @RequestMapping(path = "/books/{id}")
    public String showBookById(@PathVariable int id, Model model) {
        var book = bookRepository.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            model.addAttribute("bookId", book.get().getBookId());
            
            var rentedBookIds = RentalController.retrieveIdsOfRentedBooks(rentalRepository);
            var canBeRented = !rentedBookIds.contains(id);
            
            if(!canBeRented) {
            	Rental rental = rentalRepository.findRentalsByBook(book.get()).get(0);
            	model.addAttribute("cardIdOfUserThatRentedBook", rental.getLibraryUser().getLibraryCardId());
            }
            model.addAttribute("canBeRented", canBeRented);
        } else {
            throw new ResourceNotFoundException("book", "bookId", id);
        }
        return "book/showBook";
    }

    @GetMapping("/books/{id}/edit")
    public String showUpdateBookForm(@PathVariable("id") int id, Model model) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book", "bookId", id));
        model.addAttribute("book", book);
        
        var bookCategoryToIsAlreadySelectedMap = new HashMap<>();
        for (BookCategory bk : BookCategory.values()) {
            bookCategoryToIsAlreadySelectedMap.put(bk, book.getCategories().contains(bk.getDisplayValue()));
        }
        model.addAttribute("bookCategoryToIsAlreadySelectedMap", bookCategoryToIsAlreadySelectedMap);
        return "book/editBook";
    }

    @PutMapping("/books/{id}")
    public String updateBook(@PathVariable("id") int id, @Valid Book book, BindingResult result,
            RedirectAttributes redirectAttrs) {
        var bookById = bookRepository.findById(id);

        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "Invalid data passed for updating book object.");
        } else if (bookById.isPresent()) {
        	var bookToUpdate = bookById.get();
            copyBookData(book, bookToUpdate);
            bookRepository.save(bookToUpdate);
            
            redirectAttrs.addFlashAttribute("success",
                    String.format("Successfully updated data for book with ID %d!", id));
        } else {
            redirectAttrs.addFlashAttribute("error",
                    String.format("Couldn't find book with ID %s - so it was not possible to update it!", id));
        }
        return "redirect:/books/" + id;
    }

    @GetMapping("/delete/books/{id}")
    public String deleteBook(@PathVariable("id") int id, RedirectAttributes redirectAttrs) {
        var book = bookRepository.findById(id);
        if (book.isPresent()) {
        	var rentalsForBook = rentalRepository.findRentalsByBook(book.get());
            rentalRepository.deleteAll(rentalsForBook);
            bookRepository.delete(book.get());
            
            reportDeleteBookSuccess(redirectAttrs, book.get());
        } else {
            reportDeleteBookFailure(redirectAttrs, book.get());
        }
        return "redirect:/books";
    }
    
    /*
     * SUPPORTING METHODS:
     */
    
    private void copyBookData(Book bookFrom, Book bookTo) {
        bookTo.setTitle(bookFrom.getTitle());
        bookTo.setAuthor(bookFrom.getAuthor());
        bookTo.setIsbn(bookFrom.getIsbn());
        bookTo.setCategories(bookFrom.getCategories());
        bookTo.setDescription(bookFrom.getDescription());
        bookTo.setYearOfPublication(bookFrom.getYearOfPublication());
        bookTo.setShelf(bookFrom.getShelf());
    }
    
    private void reportDeleteBookSuccess(RedirectAttributes redirectAttrs, Book book) {
        var bookDeletedMessage = String.format(
                "Book with ID %d (title: %s, author: %s) has been successfully deleted.", 
                book.getBookId(), book.getTitle(), book.getAuthor());
        redirectAttrs.addFlashAttribute("success", bookDeletedMessage);
    }
    
    private void reportDeleteBookFailure(RedirectAttributes redirectAttrs, Book book) {
        var bookNotDeletedMessage = String.format("Couldn't delete book with ID %d as such book does not exists!",
                book.getBookId());
        redirectAttrs.addFlashAttribute("error", bookNotDeletedMessage);
    }

    private void retrieveDataMatchingFilters(Set<Book> allBooks, String title, 
            String categories, String author) {
    	
        if (atLeastOneInputNotEmpty(title, categories, author)) {
            if (title != null && !title.isEmpty()) {
                intersectionIfSecondArgNotEmpty(allBooks, 
                        bookRepository.findBooksByTitle(title.trim()));
            }

            if (categories != null && !categories.isEmpty()) {
                intersectionIfSecondArgNotEmpty(allBooks,
                        bookRepository.findBooksByCategory(categories.trim()));
            }

            if (author != null && !author.isEmpty()) {
                intersectionIfSecondArgNotEmpty(allBooks, 
                        bookRepository.findBooksByAuthor(author.trim()));
            }
        }
    }
    
    private Boolean atLeastOneInputNotEmpty(String title, String categories, String author) {
    	return title != null || categories != null || author != null;
    }
    
    private  void intersectionIfSecondArgNotEmpty(Set<Book> allBooksMatchingFilter, List<Book> newItems) {
        if (newItems != null) {
            allBooksMatchingFilter.retainAll(newItems);
        }
    }
    
    private Map<Integer, Boolean> makeMappingBookIdToCanBeRented(Set<Book> allBooksThatMatchFilters, 
            List<Integer> rentedBookIds) {
        Map<Integer, Boolean> bookIdToCanBookBeRentedMap = new HashMap<>();

        if (allBooksThatMatchFilters != null && !allBooksThatMatchFilters.isEmpty()) {
            allBooksThatMatchFilters.stream().forEach(
                    book -> bookIdToCanBookBeRentedMap.put(book.getBookId(), RentalController.canBookBeRented(book, rentedBookIds)));
        }
        return bookIdToCanBookBeRentedMap;
    }
}

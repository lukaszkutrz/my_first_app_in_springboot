package library.controllers.major;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library.helpers.RentalData;
import library.model.Book;
import library.model.LibraryUser;
import library.model.Rental;
import library.repository.BookRepository;
import library.repository.LibraryUserRepository;
import library.repository.RentalRepository;

@Controller
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryUserRepository libraryUserRepository;

    @PostMapping("/rent-books/{id}")
    public String rentBook(@PathVariable("id") int id, long userLibraryCardId, RedirectAttributes redirectAttrs) {
        var bookById = bookRepository.findById(id);
        var libraryUser = libraryUserRepository.findById(userLibraryCardId);
        var isBookPresent = bookById.isPresent();
        var isUserPresent = libraryUser.isPresent();

        if (isBookPresent && isUserPresent) {
            var bookSuccesfullyRented = false;

            synchronized (RentalController.class) {
            	var rentedBookIds = retrieveIdsOfRentedBooks(rentalRepository);

                if (canBookBeRented(bookById.get(), rentedBookIds)) {
                    var bookRental = new Rental();
                    bookRental.setBook(bookById.get());
                    bookRental.setLibraryUserId(libraryUser.get());
                    rentalRepository.save(bookRental);
                    bookSuccesfullyRented = true;
                }
            }

            if (bookSuccesfullyRented) {
                reportBookSuccesfullyRented(redirectAttrs, libraryUser.get(), bookById.get());
                return "redirect:/books-rented-by-user/" + userLibraryCardId;
            } else {
                redirectAttrs.addFlashAttribute("error",
                        String.format("Book with ID %d cannot be rented, as it is already rented!", id));
                return "redirect:/books/" + id;
            }

        } else {
            var rentalData = new RentalData(bookById.get(), libraryUser.get(), isBookPresent, isUserPresent);
            reportProblemWithRentingBook(redirectAttrs, rentalData);
            return "redirect:/books";
        }
    }

    @RequestMapping(path = "/books-rented-by-user/{libraryUserId}")
    public String showRentedBooksByLibraryUserId(@PathVariable long libraryUserId, Model model) {
        var libraryUser = libraryUserRepository.findById(libraryUserId);
        var rentals = rentalRepository.findRentalsByUser(libraryUser.get());

        var rentedBooks = new ArrayList<>();

        if (anyRentalsExistsAndUserIsPresent(rentals, libraryUser)) {
            rentedBooks.addAll(getRentedBooksFromRentals(rentals));
        }

        model.addAttribute("libraryUser", libraryUser.isPresent() ? libraryUser.get() : null);
        model.addAttribute("rentedBooks", rentedBooks);

        return "rental/listRentedBooks";
    }
    
    private boolean anyRentalsExistsAndUserIsPresent(List<Rental> rentals, Optional<LibraryUser> user) {
    	return rentals != null && !rentals.isEmpty() && user.isPresent();
    }

    @RequestMapping("/return-books/{id}")
    public String handleBookReturned(@PathVariable("id") long libraryUserId, @RequestParam("bookId") int bookId,
            RedirectAttributes redirectAttrs) {
        var libraryUser = libraryUserRepository.findById(libraryUserId);
        var book = bookRepository.findById(bookId);

        if (libraryUser.isPresent() && book.isPresent()) {
            var rental = rentalRepository.findRentalsByUserAndBookId(libraryUser.get(), book.get());
            rentalRepository.delete(rental);

            reportRentalSuccesfullyDeleted(redirectAttrs, libraryUserId, bookId);
        } else {
            reportDeleteRentalFailure(redirectAttrs, libraryUserId, bookId);
        }

        if (libraryUser.isPresent()) {
            return "redirect:/books-rented-by-user/" + libraryUserId;
        }
        return "redirect:/books";
    }

    @RequestMapping(path = { "/rented-books" })
    public String getAllLibraryUsers(Model model, Integer bookId) {

        var rentals = new ArrayList<Rental>();
        rentals.addAll((Collection<? extends Rental>) rentalRepository.findAll());

        var rentedBooksIds = rentals.stream().map(rental -> rental.getBook().getBookId())
                .collect(Collectors.toList());

        var rentedBooks = selectBooksWhichAreRented(bookRepository.findAll(), rentedBooksIds);
        var rentedAndSearched = selectBooksByBookIdIfSpecified(rentedBooks, bookId);

        model.addAttribute("rentedBooks", rentedAndSearched);
        model.addAttribute("bookIdsToOwnerIds", retrieveMappingBookToUser(rentals));

        return "rental/searchRentedBooks";
    }

    /*
     * REPORTING SUCCESS OR FAILURE OF ACTIONS
     */

    private void reportBookSuccesfullyRented(RedirectAttributes redirectAttrs, LibraryUser user, Book book) {
        var bookRentedMessage = String.format(
                "Successfully rented book with ID %d (title - %s, author - %s) for library user with card ID %d (username - %s)",
                book.getBookId(), book.getTitle(), book.getAuthor(), user.getLibraryCardId(), user.getUserName());
        redirectAttrs.addFlashAttribute("success", bookRentedMessage);
    }

    private void reportProblemWithRentingBook(RedirectAttributes redirectAttrs, RentalData rentalData) {
        var book = rentalData.getBook();
        var user = rentalData.getUser();
        var errorMessage = String.format("Problem during renting book with ID %d by user with library card ID %d!%n"
                + "Book with id=%d present in database: %b%nLibrary User with card ID %d present in database:%b",
                book.getBookId(), user.getLibraryCardId(), book.getBookId(), rentalData.isBookPresent(),
                user.getLibraryCardId(), rentalData.isUserPresent());
        redirectAttrs.addFlashAttribute("error", errorMessage);
    }

    private void reportRentalSuccesfullyDeleted(RedirectAttributes redirectAttrs, Long libraryUserId, Integer bookId) {
        var rentalDeletedMsg = String.format("Successfully deleted rental of user with ID %d for book with ID %d.",
                libraryUserId, bookId);
        redirectAttrs.addFlashAttribute("success", rentalDeletedMsg);
    }

    private void reportDeleteRentalFailure(RedirectAttributes redirectAttrs, Long libraryUserId, Integer bookId) {
        var bookNotDeletedMessage = String.format(
                "Couldn't remove rental of user with library card ID %d for book with ID %d as there's no such user or book!",
                libraryUserId, bookId);
        redirectAttrs.addFlashAttribute("error", bookNotDeletedMessage);
    }

    /*
     * SUPPORTING METHODS:
     */

    private Map<Integer, Long> retrieveMappingBookToUser(List<Rental> rentals) {
        Map<Integer, Long> bookToUser = new HashMap<>();

        rentals.stream().forEach(rental -> {
            bookToUser.put(rental.getBook().getBookId(), rental.getLibraryUser().getLibraryCardId());
        });

        return bookToUser;
    }
    
    protected static List<Book> selectBooksWhichAreRented(Iterable<Book> books, List<Integer> rentedBookIds) {
        List<Book> selectedBooks = new ArrayList<>();
        for (Book book : books) {
            if (rentedBookIds.contains(book.getBookId())) {
                selectedBooks.add(book);
            }
        }
        return selectedBooks;
    }

    public static List<Integer> retrieveIdsOfRentedBooks(RentalRepository rentalRepo) {
        List<Integer> results = new ArrayList<>();
        var rentals = rentalRepo.findAll();

        for (Rental rental : rentals) {
            results.add(rental.getBook().getBookId());
        }
        return results;
    }

    protected static boolean canBookBeRented(Book book, List<Integer> idsOfRentedBooks) {
        return !idsOfRentedBooks.contains(book.getBookId());
    }

    protected static List<Book> selectBooksByBookIdIfSpecified(List<Book> books, Integer desiredBookId) {
        List<Book> selectedBooks = new ArrayList<>();
        
        if (desiredBookId == null) {
            return books;
        }

        for (Book book : books) {
            if (desiredBookId.equals(book.getBookId())) {
                selectedBooks.add(book);
                return selectedBooks;
            }
        }
        return selectedBooks;
    }

    private List<Book> getRentedBooksFromRentals(List<Rental> rentals) {
        List<Book> rentedBooks = new ArrayList<>();
        for (var rental : rentals) {
            var bookId = rental.getBook().getBookId();
            var book = bookRepository.findById(bookId);
            if (book.isPresent()) {
                rentedBooks.add(book.get());
            }
        }
        return rentedBooks;
    }
}

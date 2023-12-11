package library.helpers;

import library.model.Book;
import library.model.LibraryUser;

public class RentalData {

    Book book;
    LibraryUser user;
    Boolean isBookPresent;
    Boolean isUserPresent;
    
    public RentalData(Book book, LibraryUser user, Boolean isBookPresent, Boolean isUserPresent) {
        super();
        this.book = book;
        this.user = user;
        this.isBookPresent = isBookPresent;
        this.isUserPresent = isUserPresent;
    }

    public Book getBook() {
        return book;
    }

    public LibraryUser getUser() {
        return user;
    }

    public Boolean isBookPresent() {
        return isBookPresent;
    }

    public Boolean isUserPresent() {
        return isUserPresent;
    }
    
}

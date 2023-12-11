package library.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rentalId")
    private int rentalId;

    @OneToOne
    @JoinColumn(name = "bookId")
    @NotNull(message = "Reference to bookId cannot be empty or null.")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "libraryUserId")
    @NotNull(message = "Reference to libraryUserId cannot be empty or null.")
    private LibraryUser libraryUser;

    public Rental() {
    }

    public Rental(Book book, LibraryUser libraryUser) {
        this.book = book;
        this.libraryUser = libraryUser;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }

    public void setLibraryUserId(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }

    @Override
    public String toString(){
        return "Rental:\n rentalId id=" + rentalId + ",\nbook id=" + book + ",\nlibrary user id=" + libraryUser;
    }
}

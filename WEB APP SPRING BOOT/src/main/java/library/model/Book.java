package library.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Table(name = "book")
public class Book implements java.io.Serializable{

    @Id
    @GeneratedValue(generator = "book-id-generator")
    @GenericGenerator(
            name = "book-id-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "book_id_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "300000"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "bookId")
    private int bookId;

    @Column(name = "title")
    @NotEmpty(message = "Title cannot be empty or null.")
    @Size(max = 255, message = "Title must be max 255 characters long.")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Author cannot be empty or null.")
    @Size(max = 255, message = "Author must be max 255 characters long.")
    private String author;

    @Column(name = "isbn")
    @Size(min = 17, max = 17, message = "ISBN must be 17 characters long.")
    @NotEmpty(message = "ISBN cannot be empty or null.")
    @Pattern(regexp = "((\\d+-)(\\d+-)(\\d+-)(\\d+-)\\d+)", message = "ISBN must contain 5 groups of digits separated by dash")
    private String isbn;

    @Column(name = "categories")
    @NotNull(message = "Categories can be empty, but cannot be null.")
    @Size(max = 255, message = "Categories must be max 255 characters long.")
    private String categories;

    @Column(name = "description")
    @NotNull(message = "Description can be empty, but cannot be null.")
    private String description;

    @Column(name = "yearOfPublication")
    @NotNull(message = "Year of publication cannot be empty or null.")
    @Min(value = 1, message = "Year of publication must be a digit and greater than 0.")
    private int yearOfPublication;
    
    @Column(name = "shelf")
    @NotNull(message = "Description can be empty, but cannot be null.")
    private String shelf;


    public int getBookId() {
        return bookId;
    }

    public Book() {
    }

    public Book(String title, String author, String isbn,  String categories, String description, int yearOfPublication, String shelf) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.categories = categories;
        this.description = description;
        this.yearOfPublication = yearOfPublication;
        this.shelf = shelf;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public  String getCategories() {
        return categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    @Override
    public String toString() {
        return "Book:\nbookId= " + bookId + ",\ntitle= " + title + ",\nauthor= " + author + ",\nisbn= " + isbn +
                ",\ncategories= " + categories + ",\ndescription=" + description + ",\nyear of publication=" + yearOfPublication 
                + "\nshelf="+shelf;
    }

    @Override
    public int hashCode(){
        return this.bookId;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }

        if (getClass() != obj.getClass())
            return false;

        Book other = (Book) obj;
        return Objects.equals(bookId, other.bookId);
    }
}

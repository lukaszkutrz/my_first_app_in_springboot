package library.repository;

import library.model.Book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    List<Book> findBooksByTitle(@Param("title") String title);
    
    @Query(value = "SELECT b FROM Book b WHERE b.author LIKE %:author%")
    List<Book> findBooksByAuthor(@Param("author") String author);

    @Query(value = "SELECT b FROM Book b WHERE b.categories LIKE %:categories%")
    List<Book> findBooksByCategory(@Param("categories") String categories);
    
    @Query(value= "SELECT b FROM Book b WHERE b.bookId =:bookId")
    List<Book> findBooksById(@Param("bookId") Integer bookId);
}

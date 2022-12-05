package library.repository;

import library.model.Book;
import library.model.Rental;
import library.model.LibraryUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalRepository extends CrudRepository<Rental, Integer> {

    @Query(value = "SELECT r FROM Rental r WHERE r.libraryUser =:libraryUser")
    List<Rental> findRentalsByUser(@Param("libraryUser") LibraryUser libraryUser);

    @Query(value = "SELECT r FROM Rental r WHERE r.book =:book")
    List<Rental> findRentalsByBook(@Param("book") Book book);

    @Query(value = "SELECT r FROM Rental r WHERE r.libraryUser =:libraryUser AND r.book =:book")
    Rental findRentalsByUserAndBookId(@Param("libraryUser") LibraryUser libraryUser, @Param("book") Book book);
    

}

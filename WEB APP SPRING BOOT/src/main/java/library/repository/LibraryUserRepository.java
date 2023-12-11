package library.repository;

import library.model.LibraryUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface LibraryUserRepository  extends CrudRepository<LibraryUser, Long> {
    @Query("SELECT u FROM LibraryUser u WHERE u.userName LIKE %:userName%")
    List<LibraryUser> getAllUsersByName(@Param("userName") String userName);

    @Query("SELECT u FROM LibraryUser u WHERE u.login =:login")
    List<LibraryUser> getAllUsersWithGivenLogin(@Param("login") String login);
}


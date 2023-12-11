package library.model;

import library.enums.LibraryUserRoleType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "libraryUser", uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
public class LibraryUser {

    @Id
    @GeneratedValue(generator = "library-card-id-generator")
    @GenericGenerator(
            name = "library-card-id-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "library_card_id_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "100000"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "libraryCardId")
    private long libraryCardId;

    @Column(name = "libraryUserRole")
    @NotNull(message = "Library user's role type cannot be empty or null.")
    @Enumerated(EnumType.STRING)
    private LibraryUserRoleType libraryUserRole;

    @Column(name = "userName")
    @NotEmpty(message = "Username cannot be empty or null.")
    @Size(max = 255, message = "Username must be max 255 characters long.")
    private String userName;

    @Column(name = "login", unique = true)
    @NotEmpty(message = "Login cannot be empty or null.")
    @Size(min=8, max = 255, message = "Login must be at least 8 and max 255 characters long.")
    private String login;

    public LibraryUser() {
    }

    public LibraryUser(LibraryUserRoleType libraryUserRole, String userName, String login) {
        this.libraryUserRole = libraryUserRole;
        this.userName = userName;
        this.login = login;
    }

    public long getLibraryCardId() {
        return libraryCardId;
    }

    public void setLibraryCardId(long libraryCardId) {
        this.libraryCardId = libraryCardId;
    }

    public LibraryUserRoleType getLibraryUserRole() {
        return libraryUserRole;
    }

    public void setLibraryUserRole(LibraryUserRoleType libraryUserRole) {
        this.libraryUserRole = libraryUserRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString(){
        return "LibraryUser:\nlibrary card id=" + this.libraryCardId + ",\nlibrary user role=" + this.libraryUserRole
                + ",\nuser name=" + this.userName+ ",\nlogin=" + this.login;
    }

}

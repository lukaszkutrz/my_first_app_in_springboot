package library.controllers.major;

import library.exception.ResourceNotFoundException;
import library.model.LibraryUser;
import library.model.Rental;
import library.repository.LibraryUserRepository;
import library.repository.RentalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class LibraryUserController {

    @Autowired
    private LibraryUserRepository libraryUserRepository;
    
    @Autowired
    private RentalRepository rentalRepository;

    @GetMapping("/users/new")
    public String showAddUserForm(Model model) {
        var user = new LibraryUser();
        model.addAttribute("user", user);
        return "libraryUser/addUser";
    }

    @PostMapping("/users")
    public String addUser(@Valid LibraryUser user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Not valid data passed when trying to add new library user! Try again.");
            return "libraryUser/addUser";
        }

        if (isLoginAlreadyReserved(user.getLogin())) {
            redirectAttributes.addFlashAttribute("error", String.format("Cannot add user with login %s as this login is already reserved!", user.getUserName()));
            return "libraryUser/addUser";
        }

        libraryUserRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Successfully added new library user with library card id " + user.getLibraryCardId());

        return "redirect:/users/" + user.getLibraryCardId();
    }

    @RequestMapping(path = {"/users"})
    public String searchUser(Model model, String userName) {
        var list = new ArrayList<>();
        
        if (userName != null && !userName.isBlank()) {
            userName = userName.trim();
            list.addAll(libraryUserRepository.getAllUsersByName(userName));
        } else {
            list.addAll((Collection<? extends LibraryUser>) libraryUserRepository.findAll());
        }

        model.addAttribute("allUsers", list);
        return "libraryUser/listUsers";
    }

    @RequestMapping(path = "/users/{id}")
    public String showUserById(@PathVariable long id, Model model) {
        var libraryUser = libraryUserRepository.findById(id);
        
        if (libraryUser.isPresent()) {
            model.addAttribute("user", libraryUser.get());
            model.addAttribute("userId", libraryUser.get().getLibraryCardId());
        } else {
            throw new ResourceNotFoundException("libraryUser", "libraryCardId", id);
        }
        return "libraryUser/showUser";
    }

    @GetMapping("/users/{id}/edit")
    public String showUpdateUserForm(@PathVariable("id") long id, Model model) {
        var user = libraryUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + id));

        model.addAttribute("user", user);
        return "libraryUser/editUser";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid LibraryUser user, RedirectAttributes redirectAttrs) {
        var libraryUser = libraryUserRepository.findById(id);

        if (libraryUser.isPresent()) {
            libraryUser.get().setUserName(user.getUserName());
            libraryUser.get().setLogin(user.getLogin());
            libraryUser.get().setLibraryUserRole(libraryUser.get().getLibraryUserRole());

            libraryUserRepository.save(libraryUser.get());
            redirectAttrs.addFlashAttribute("success", String.format("Successfully updated data for user with ID %d!", id));
        } else {
            redirectAttrs.addFlashAttribute("error", String.format("Couldn't find user with ID %s - so it was not possible to update it!", id));
        }
        return "redirect:/users/" + id;
    }
    
    @GetMapping("/delete/users/{id}")
    public String deleteUser(@PathVariable("id") long id, RedirectAttributes redirectAttrs) {
        var user = libraryUserRepository.findById(id);
        
        if (user.isPresent()) {
            List<Rental> rentalsForUser = rentalRepository.findRentalsByUser(user.get());
            rentalRepository.deleteAll(rentalsForUser);
            libraryUserRepository.delete(user.get());
            
            redirectAttrs.addFlashAttribute("success", String.format("Successfully deleted user with ID %d!", id));
        } else {
            redirectAttrs.addFlashAttribute("error", String.format("Couldn't find library user with ID %d, so it cannot be deleted!", id));
        }
        return "redirect:/users";
    }

    private boolean isLoginAlreadyReserved(String login) {
        return !libraryUserRepository.getAllUsersWithGivenLogin(login).isEmpty();
    }
}

package library.controllers.supporting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import library.repository.LibraryUserRepository;

@Controller
public class IndexPageController {
    @Autowired
    private LibraryUserRepository libraryUserRepository;

    @RequestMapping("/")
    public String homePage(Model model) {
        retrieveLibraryCardIdForUserAndSetItInModel(model);
        return "index";
    }

    @RequestMapping("/my-books")
    public String myBooks(Model model) {
        Long userId = retrieveLibraryCardIdForUserAndSetItInModel(model);
        return "redirect:/books-rented-by-user/" + userId;
    }

    /*
     * SUPPORTING METHODS
     */

    private Long retrieveLibraryCardIdForUserAndSetItInModel(Model model) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var currentUserLogin = auth.getName();

        var currentUserQueryResult = libraryUserRepository.getAllUsersWithGivenLogin(currentUserLogin);

        if (currentUserQueryResult != null && !currentUserQueryResult.isEmpty()) {
            model.addAttribute("currentLibraryCardId", currentUserQueryResult.get(0).getLibraryCardId());
            return currentUserQueryResult.get(0).getLibraryCardId();
        }
        return null;
    }

}

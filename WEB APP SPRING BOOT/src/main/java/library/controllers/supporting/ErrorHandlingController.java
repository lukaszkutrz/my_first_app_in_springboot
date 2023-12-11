package library.controllers.supporting;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandlingController implements ErrorController {

    @RequestMapping(path = {"/error"})
    public String displayError(Model model) {
        return "common/displayMessage";
    }
}
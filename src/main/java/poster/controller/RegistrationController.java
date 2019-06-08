package poster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import poster.entity.UserEntity;
import poster.service.UserService;

import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(UserEntity userEntity, Map<String, Object> model) {
        if (userService.addUser(userEntity)) {
            return "redirect:/login";
        }

        model.put("message", "Такой пользователь уже найден");

        return "registration";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activeUser(code);

        if (isActivated) {
            model.addAttribute("message", "Активация прошла успешно!");
        } else {
            model.addAttribute("message", "Код активации не найден.");
        }

        return "login";
    }

}

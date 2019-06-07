package poster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import poster.entity.RoleEntity;
import poster.entity.UserEntity;
import poster.repository.UserRepository;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(UserEntity userEntity, Map<String, Object> model) {
        UserEntity userFromDB = userRepository.findByUsername(userEntity.getUsername());

        if (userFromDB != null) {
            model.put("message", "User exists");

            return "registration";
        }

        userEntity.setActive(true);
        userEntity.setRoles(Collections.singleton(RoleEntity.USER));

        userRepository.save(userEntity);

        return "redirect:/login";
    }

}

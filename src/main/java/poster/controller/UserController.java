package poster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poster.entity.RoleEntity;
import poster.entity.UserEntity;
import poster.repository.UserRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "userList";
    }

    @GetMapping("{userEntity}")
    public String userEditForm(@PathVariable UserEntity userEntity, Model model) {
        model.addAttribute("user", userEntity);
        model.addAttribute("roles", RoleEntity.values());

        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") UserEntity userEntity
    ) {
        userEntity.setUsername(username);

        Set<String> roles = Arrays.stream(RoleEntity.values())
                .map(RoleEntity::name)
                .collect(Collectors.toSet());

        userEntity.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                userEntity.getRoles().add(RoleEntity.valueOf(key));
            }
        }

        userRepository.save(userEntity);

        return "redirect:/user";
    }

}

package poster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poster.entity.RoleEntity;
import poster.entity.UserEntity;
import poster.service.UserService;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{userEntity}")
    public String userEditForm(@PathVariable UserEntity userEntity, Model model) {
        model.addAttribute("user", userEntity);
        model.addAttribute("roles", RoleEntity.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") UserEntity userEntity
    ) {
        userService.saveUser(userEntity, username, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(@AuthenticationPrincipal UserEntity userEntity, Model model) {
        model.addAttribute("username", userEntity.getUsername());
        model.addAttribute("email", userEntity.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal UserEntity userEntity,
            @RequestParam String email,
            @RequestParam String password
    ) {
        userService.updateProfile(userEntity, email, password);

        return "redirect:/user/profile";
    }

}

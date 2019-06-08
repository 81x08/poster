package poster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import poster.entity.UserEntity;
import poster.entity.dto.CaptchaResponseDTO;
import poster.service.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String reCaptchaSecret;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String reCaptchaResponse,
            @Valid UserEntity userEntity,
            BindingResult bindingResult,
            Model model
    ) {
        String url = String.format(CAPTCHA_URL, reCaptchaSecret, reCaptchaResponse);

        CaptchaResponseDTO captchaResponseDTO = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);

        if (captchaResponseDTO != null && !captchaResponseDTO.isSuccess()) {
            model.addAttribute("captchaError", "Неверная каптча");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);

        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Пароль подтверждения не должен быть пустым");
        }

        if (userEntity.getPassword() != null && !userEntity.getPassword().equals(passwordConfirm)) {
            model.addAttribute("password2Error", "Пароли не совпадают");
        }

        if (isConfirmEmpty || bindingResult.hasErrors() || (captchaResponseDTO != null && !captchaResponseDTO.isSuccess())) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userService.addUser(userEntity)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже есть");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activeUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Активация прошла успешно!");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Код активации не найден.");
        }

        return "login";
    }

}

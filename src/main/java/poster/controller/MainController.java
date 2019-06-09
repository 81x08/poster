package poster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import poster.entity.MessageEntity;
import poster.entity.UserEntity;
import poster.repository.MessageRepository;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String tag, Model model) {
        Iterable<MessageEntity> messages;

        if (tag == null || tag.isEmpty()) {
            messages = messageRepository.findAll();
        } else {
            messages = messageRepository.findByTag(tag);
        }

        model.addAttribute("messages", messages);
        model.addAttribute("tag", tag);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal UserEntity userEntity,
            @Valid MessageEntity messageEntity,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        messageEntity.setAuthor(userEntity);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", messageEntity);
        } else {
            saveFile(messageEntity, file);
        }

        Iterable<MessageEntity> messages = messageRepository.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

    @GetMapping("/user-messages/{currentUser}")
    public String userMessages(
            @AuthenticationPrincipal UserEntity sessionUser,
            @PathVariable UserEntity currentUser,
            Model model,
            @RequestParam(name = "message", required = false) MessageEntity messageEntity
    ) {
        Set<MessageEntity> messages = currentUser.getMessages();

        model.addAttribute("userChannel", currentUser);
        model.addAttribute("subscriptionsCount", currentUser.getSubscriptions().size());
        model.addAttribute("subscribersCount", currentUser.getSubscribers().size());
        model.addAttribute("isSubscriber", currentUser.getSubscribers().contains(sessionUser));
        model.addAttribute("messages", messages);
        model.addAttribute("message", messageEntity);
        model.addAttribute("isCurrentUser", sessionUser.equals(currentUser));

        return "userMessages";
    }

    @PostMapping("/user-messages/{currentUserId}")
    public String updateMessage(
            @AuthenticationPrincipal UserEntity sessionUser,
            @PathVariable Long currentUserId,
            @RequestParam(name = "id") MessageEntity messageEntity,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (messageEntity.getAuthor().equals(sessionUser)) {
            if (!StringUtils.isEmpty(text)) {
                messageEntity.setText(text);
            }

            if (!StringUtils.isEmpty(tag)) {
                messageEntity.setTag(tag);
            }

            saveFile(messageEntity, file);

            messageRepository.save(messageEntity);
        }

        return "redirect:/user-messages/" + currentUserId;
    }

    private void saveFile(MessageEntity messageEntity, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            messageEntity.setFileName(resultFileName);
        }
    }

}
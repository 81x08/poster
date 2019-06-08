package poster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import poster.entity.RoleEntity;
import poster.entity.UserEntity;
import poster.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(UserEntity userEntity) {
        UserEntity userFromDB = userRepository.findByUsername(userEntity.getUsername());

        if (userFromDB != null) {
            return false;
        }

        userEntity.setActive(true);
        userEntity.setRoles(Collections.singleton(RoleEntity.USER));
        userEntity.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(userEntity);

        sendMessage(userEntity);

        return true;
    }

    public boolean activeUser(String code) {
        UserEntity userEntity = userRepository.findByActivationCode(code);

        if (userEntity == null) {
            return false;
        }

        userEntity.setActivationCode(null);

        userRepository.save(userEntity);

        return true;
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(UserEntity userEntity, String username, Map<String, String> form) {
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
    }

    public void updateProfile(UserEntity userEntity, String email, String password) {
        String userEmail = userEntity.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) || (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            userEntity.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                userEntity.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            userEntity.setPassword(password);
        }

        userRepository.save(userEntity);

        if (isEmailChanged) {
            sendMessage(userEntity);
        }
    }

    private void sendMessage(UserEntity userEntity) {
        if(!StringUtils.isEmpty(userEntity.getEmail())) {
            String message = String.format(
                    "Привет, %s\n" +
                            "Добро пожаловать на Poster\n" +
                            "Чтобы подтвердить почту, перейдите по ссылке: http://localhost:8080/activate/%s",
                    userEntity.getUsername(), userEntity.getActivationCode()
            );

            mailSenderService.send(userEntity.getEmail(), "Код активации", message);
        }
    }

}

package poster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poster.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByActivationCode(String code);
}

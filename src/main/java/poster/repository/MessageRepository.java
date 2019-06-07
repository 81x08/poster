package poster.repository;

import org.springframework.data.repository.CrudRepository;
import poster.entity.MessageEntity;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {

    List<MessageEntity> findByTag(String tag);

}

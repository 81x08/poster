package poster.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Текст поста не должен быть пустым")
    @Length(max = 2048, message = "Сообщение слишком длинное (2 КБ)")
    @Column(name = "text")
    private String text;

    @NotBlank(message = "Для поста требуется как минимум 1 тэг")
    @Length(max = 255, message = "Слишком много тэгов (255 символов)")
    @Column(name = "tag")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity author;

    private String fileName;

    public MessageEntity() {
    }

    public MessageEntity(String text, String tag, UserEntity userEntity) {
        this.text = text;
        this.tag = tag;
        this.author = userEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}

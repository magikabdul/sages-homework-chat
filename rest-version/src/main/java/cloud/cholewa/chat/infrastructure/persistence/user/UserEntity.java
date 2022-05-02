package cloud.cholewa.chat.infrastructure.persistence.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQuery(name = UserEntity.FIND_BY_NICK, query = "SELECT u FROM UserEntity u WHERE u.nick LIKE :nick")
@NamedQuery(name = UserEntity.FIND_BY_TOKEN, query = "SELECT u FROM UserEntity u WHERE u.token LIKE :token")
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserEntity {

    public static final String FIND_BY_NICK = "userFindByNick";
    public static final String FIND_BY_TOKEN = "userFindByToken";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nick;

    @Column(nullable = false)
    private String password;

    @Setter
    private String token;

    public UserEntity(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }
}

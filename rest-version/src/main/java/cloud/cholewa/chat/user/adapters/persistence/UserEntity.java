package cloud.cholewa.chat.user.adapters.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQuery(name = UserEntity.FIND_BY_NICK, query = "SELECT u FROM UserEntity u WHERE u.nick = :nick")
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {

    public static final String FIND_BY_NICK = "userFindByNick";

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String nick;
    private String password;

    public UserEntity(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }
}

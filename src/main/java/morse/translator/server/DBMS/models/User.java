package morse.translator.server.DBMS.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40, nullable = false)
    private String first_name;

    @Column(length = 40, nullable = false)
    private String last_name;

    @Column(length = 60, unique = true, nullable = false)
    private String login;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 20, unique = true)
    private String phone_number;

    private Date birthday;

    public Long getId() { return id; }

    public String getLast_name() { return last_name; }

    public String getFirst_name() { return first_name; }

    public Date getBirthday() { return birthday; }

    public String getEmail() { return email; }

    public String getLogin() { return login; }

    public String getPhone_number() { return phone_number; }

    public void setLast_name(String last_name) { this.last_name = last_name; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public void setEmail(String email) { this.email = email; }

    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }

    public void setLogin(String login) { this.login = login; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}

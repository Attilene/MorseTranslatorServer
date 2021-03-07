package morse.translator.server.dbms.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Password password;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<History> histories;

    public User() {}

    public User(String first_name,
                String last_name,
                String login,
                String email,
                String phone_number,
                Date birthday) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.login = login;
        this.email = email;
        this.phone_number = phone_number;
        this.birthday = birthday;
        this.password = null;
        this.histories = new ArrayList<>();
    }

    public void addHistory(History history){
        this.histories.add(history);
        history.setUser(this);
    }

    public void removeHistory(History history){
        this.histories.remove(history);
        history.setUser(null);
    }

    public Long getId() { return id; }

    public String getLast_name() { return last_name; }

    public String getFirst_name() { return first_name; }

    public Date getBirthday() { return birthday; }

    public String getEmail() { return email; }

    public String getLogin() { return login; }

    public String getPhone_number() { return phone_number; }

    public Password getPassword() { return password; }

    public List<History> getHistories() { return histories; }

    public void setLast_name(String last_name) { this.last_name = last_name; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public void setEmail(String email) { this.email = email; }

    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }

    public void setLogin(String login) { this.login = login; }

    public void setHistories(List<History> histories) { this.histories = histories; }

    public void setPassword(Password password) {
        if (password == null) {
            if (this.password != null) this.password.setUser(null);
        }
        else { password.setUser(this); }
        this.password = password;
    }

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
                ", password=" + password +
                ", histories=" + histories +
                '}';
    }
}

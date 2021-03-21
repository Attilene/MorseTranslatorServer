package morse.translator.server.dbms.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class for the users table
 *
 * @author  Artem Bakanov aka Attilene
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    /**
     *  Primary key of the users table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * First name column of the users table
     */
    @Column(length = 40, nullable = false)
    private String first_name;

    /**
     * Last name column of the users table
     */
    @Column(length = 40, nullable = false)
    private String last_name;

    /**
     * Login column of the users table
     */
    @Column(length = 60, unique = true, nullable = false)
    private String login;

    /**
     * Email column of the users table
     */
    @Column(length = 50, unique = true, nullable = false)
    private String email;

    /**
     * Phone number column of the users table
     */
    @Column(length = 20, unique = true)
    private String phone_number;

    /**
     * Birthday column of the users table
     */
    private Date birthday;

    /**
     * Bonding the Password model to the users table
     */
    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Password password;

    /**
     * Bonding the History model to the users table
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<History> histories;

    /**
     * Constructor for the model
     */
    public User() {}

    /**
     * Constructor for the model
     *
     * @param  first_name    user first name
     * @param  last_name     user last name
     * @param  login         user login
     * @param  email         user email
     * @param  phone_number  user phone number
     * @param  birthday      user birthday
     */
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

    /**
     * Adding instance of the History class to array of histories
     *
     * @param  history  instance of the History class
     */
    public void addHistory(History history){
        this.histories.add(history);
        history.setUser(this);
    }

    /**
     * Removing instance of the History class from array of histories
     *
     * @param  history  instance of the History class
     */
    public void removeHistory(History history){
        this.histories.remove(history);
        history.setUser(null);
    }

    /**
     * Getter for user id
     *
     * @return  id
     */
    public Long getId() { return id; }

    /**
     * Getter for user last name
     *
     * @return  last_name
     */
    public String getLast_name() { return last_name; }

    /**
     * Getter for user first name
     *
     * @return  first_name
     */
    public String getFirst_name() { return first_name; }

    /**
     * Getter for user birthday
     *
     * @return  birthday
     */
    public Date getBirthday() { return birthday; }

    /**
     * Getter for user email
     *
     * @return  email
     */
    public String getEmail() { return email; }

    /**
     * Getter for user login
     *
     * @return  login
     */
    public String getLogin() { return login; }

    /**
     * Getter for user phone number
     *
     * @return  phone_number
     */
    public String getPhone_number() { return phone_number; }

    /**
     * Getter for user password
     *
     * @return  password
     */
    public Password getPassword() { return password; }

    /**
     * Getter for user histories
     *
     * @return  histories
     */
    public List<History> getHistories() { return histories; }

    /**
     * Setter for user last name
     *
     * @param  last_name  user last name
     */
    public void setLast_name(String last_name) { this.last_name = last_name; }

    /**
     * Setter for user first name
     *
     * @param  first_name  user first name
     */
    public void setFirst_name(String first_name) { this.first_name = first_name; }

    /**
     * Setter for user birthday
     *
     * @param  birthday  user birthday
     */
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    /**
     * Setter for user email
     *
     * @param  email  user email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Setter for user phone number
     *
     * @param  phone_number  user phone number
     */
    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }

    /**
     * Setter for user login
     *
     * @param  login  user login
     */
    public void setLogin(String login) { this.login = login; }

    /**
     * Setter for user histories
     *
     * @param  histories  user histories
     */
    public void setHistories(List<History> histories) { this.histories = histories; }

    /**
     * Setter for user password
     *
     * @param  password  user password
     */
    public void setPassword(Password password) {
        if (password == null) {
            if (this.password != null) this.password.setUser(null);
        }
        else { password.setUser(this); }
        this.password = password;
    }

    /**
     * Method for displaying or returning User class instance field values
     *
     * @return  User class instance field values
     */
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

package morse.translator.server.dbms.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Model class for the passwords table
 *
 * @author  Artem Bakanov aka Attilene
 */
@Entity
@Table(name = "passwords")
public class Password implements Serializable {
    /**
     *  Primary key of the passwords table
     */
    @Id
    private Long id;

    /**
     * Hash column of the passwords table
     */
    @Column(nullable = false, length = 1000)
    private String hash;

    /**
     * Salt column of the passwords table
     */
    @Column(nullable = false, length = 500)
    private String salt;

    /**
     * Foreign key of the passwords table to the users table
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    /**
     * Constructor for the model
     */
    public Password() {}

    /**
     * Constructor for the model
     *
     * @param  hash  password hash
     * @param  salt  password salt
     */
    public Password(String hash,
                    String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    /**
     * Getter for password id
     *
     * @return  id
     */
    public Long getId() { return id; }

    /**
     * Getter for password hash
     *
     * @return  hash
     */
    public String getHash() { return hash; }

    /**
     * Getter for password salt
     *
     * @return  salt
     */
    public String getSalt() { return salt; }

    /**
     * Getter for password owner
     *
     * @return  user
     */
    public User getUser() { return user; }

    /**
     * Setter for password hash
     *
     * @param  hash  password hash
     */
    public void setHash(String hash) { this.hash = hash; }

    /**
     * Setter for password salt
     *
     * @param  salt  password salt
     */
    public void setSalt(String salt) { this.salt = salt; }

    /**
     * Setter for password owner
     *
     * @param  user password owner
     */
    public void setUser(User user) { this.user = user; }

    /**
     * Method for displaying or returning Password class instance field values
     *
     * @return  Password class instance field values
     */
    @Override
    public String toString() {
        return "Password{" +
                "hash='" + hash + '\'' +
                ", salt='" + salt + '\'' +
                ", user=" + user +
                '}';
    }
}

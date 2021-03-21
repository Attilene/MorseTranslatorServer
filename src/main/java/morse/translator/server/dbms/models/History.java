package morse.translator.server.dbms.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Model class for the histories table
 *
 * @author  Artem Bakanov aka Attilene
 */
@Entity
@Table(name = "histories")
public class History implements Serializable {
    /**
     *  Primary key of the histories table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Start string column of the histories table
     */
    @Column(nullable = false, length = 5000)
    private String start_string;

    /**
     * End string column of the histories table
     */
    @Column(nullable = false, length = 5000)
    private String end_string;

    /**
     * Operation time column of the histories table
     */
    @Column(nullable = false)
    private Date operation_time;

    /**
     * Foreign key of the histories table to the users table
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    /**
     * Constructor for the model
     */
    public History() {}

    /**
     * Constructor for the model
     *
     * @param  start_string    history start string
     * @param  end_string      history end string
     * @param  operation_time  history operation time
     */
    public History(String start_string,
                   String end_string,
                   Date operation_time) {
        this.start_string = start_string;
        this.end_string = end_string;
        this.operation_time = operation_time;
    }

    /**
     * Getter for history operation time
     *
     * @return  operation_time
     */
    public Date getOperation_time() { return operation_time; }

    /**
     * Getter for history id
     *
     * @return  id
     */
    public Long getId() { return id; }

    /**
     * Getter for history end string
     *
     * @return  end_string
     */
    public String getEnd_string() { return end_string; }

    /**
     * Getter for history start string
     *
     * @return  start_string
     */
    public String getStart_string() { return start_string; }

    /**
     * Getter for history owner
     *
     * @return  user
     */
    public User getUser() { return user; }

    /**
     * Setter for history end string
     *
     * @param  end_string  history end_string
     */
    public void setEnd_string(String end_string) { this.end_string = end_string; }

    /**
     * Setter for history start string
     *
     * @param  start_string  history start_string
     */
    public void setStart_string(String start_string) { this.start_string = start_string; }

    /**
     * Setter for history operation time
     *
     * @param  operation_time  history operation_time
     */
    public void setOperation_time(Date operation_time) { this.operation_time = operation_time; }

    /**
     * Setter for history owner
     *
     * @param  user history owner
     */
    public void setUser(User user) { this.user = user; }

    /**
     * Method for displaying or returning History class instance field values
     *
     * @return  History class instance field values
     */
    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", start_string='" + start_string + '\'' +
                ", end_string='" + end_string + '\'' +
                ", operation_time=" + operation_time +
                ", user=" + user +
                '}';
    }
}

package morse.translator.server.DBMS.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "histories")
public class History implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 2000)
    private String start_string;

    @NotNull
    @Column(nullable = false, length = 4000)
    private String end_string;

    @NotNull
    private Date operation_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public Date getOperation_time() { return operation_time; }

    public Long getId() { return id; }

    public String getEnd_string() { return end_string; }

    public String getStart_string() { return start_string; }

    public User getUser() { return user; }

    public void setEnd_string(String end_string) { this.end_string = end_string; }

    public void setStart_string(String start_string) { this.start_string = start_string; }

    public void setOperation_time(Date operation_time) { this.operation_time = operation_time; }

    public void setUser(User user) { this.user = user; }

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

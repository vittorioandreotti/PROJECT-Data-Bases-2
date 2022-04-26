package entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table (name = "alert", schema = "telcoservice")
@NamedQueries({
        @NamedQuery(name = "Alert.findByUsername", query = "SELECT a FROM Alert a where a.user = :user"),
        @NamedQuery(name = "Alert.findAll", query = "SELECT a FROM Alert a" )
})
public class Alert {
    //the PK is the same of User,
    @Id
    //uni-directional One-To-One association to User
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "USER")
    private User user;
    private float total_price;
    private LocalDateTime time_last_rejection;

    public Alert() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public LocalDateTime getTime_last_rejection() {
        return time_last_rejection;
    }

    public void setTime_last_rejection(LocalDateTime time_last_rejection) {
        this.time_last_rejection = time_last_rejection;
    }
}

package entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table (name = "alert", schema = "telcoservice")
public class Alert {
    //the PK is the same of User,
    @Id
    //uni-directional One-To-One association to User
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "USER")
    private User user;
    private Date time_last_rejection;

    public Alert() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTime_last_rejection() {
        return time_last_rejection;
    }

    public void setTime_last_rejection(Date time_last_rejection) {
        this.time_last_rejection = time_last_rejection;
    }
}

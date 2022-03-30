package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table (name = "user", schema = "telcoservice")
@NamedQueries(
    @NamedQuery(name = "User.checkCredentials", query = "SELECT u FROM User u where u.username = :username and u.password = :password")
)
public class User {
    @Id
    private String username;
    private String password;
    private String email;
    private boolean user_type;
    private boolean insolvent;

//    @ManyToOne
//    @JoinTable (name = "PROVA",
//            schema = "telcoservice",
//            joinColumns = @JoinColumn (name = "USER"),
//            inverseJoinColumns = @JoinColumn (name = "ORDEER"))
//    private Order order;

//    @OneToMany (mappedBy = "user")
//        private List<ServActiv> servActivs;

//    //uni-directional Many-To-One association to Order
//    @ElementCollection
//    @CollectionTable (
//            name = "SERVICEACTIV",
//            schema = "telcoservice",
//            joinColumns = @JoinColumn (name = "USER", referencedColumnName = "USERNAME")
//    )
//    @MapKeyJoinColumn(name="ORDEER", referencedColumnName = "ID")
//    Map<Order, ServActiv> orders;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isUser_type() {
        return user_type;
    }

    public void setUser_type(boolean user_type) {
        this.user_type = user_type;
    }

    public boolean isInsolvent() {
        return insolvent;
    }

    public void setInsolvent(boolean insolvent) {
        this.insolvent = insolvent;
    }

//    public Map<Order, ServActiv> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(Map<Order, ServActiv> orders) {
//        this.orders = orders;
//    }
}

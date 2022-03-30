package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity (name = "Ordeer")
@Table (name = "ordeer", schema = "telcoservice")
public class Order {

    @Id
    @GeneratedValue
    private int id;
    private Date date_sub;
    private boolean is_valid;
    private float total_price;
    private Date date_start_activation;
    private Date date_end_activation;
    private int rej_numb;
    private int date_last_rej;

    //bi-directional Many-To-One association to ValidityPeriod
    @ManyToOne
    @JoinColumn (name = "VALIDITY_PERIOD")
    private ValidityPeriod validity_period;

    //bi-directional Many-To-One association to Package
    @ManyToOne
    @JoinColumn (name = "SERV_PACKAGE")
    private Package serv_package;

    //uni-directional Many-To-One association to User
    @ManyToOne
    @JoinColumn (name = "USER")
    private User user;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_sub() {
        return date_sub;
    }

    public void setDate_sub(Date date_sub) {
        this.date_sub = date_sub;
    }

    public boolean isIs_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public Date getDate_start_activation() {
        return date_start_activation;
    }

    public void setDate_start_activation(Date date_start_activation) {
        this.date_start_activation = date_start_activation;
    }

    public Date getDate_end_activation() {
        return date_end_activation;
    }

    public void setDate_end_activation(Date date_end_activation) {
        this.date_end_activation = date_end_activation;
    }

    public int getRej_numb() {
        return rej_numb;
    }

    public void setRej_numb(int rej_numb) {
        this.rej_numb = rej_numb;
    }

    public int getDate_last_rej() {
        return date_last_rej;
    }

    public void setDate_last_rej(int date_last_rej) {
        this.date_last_rej = date_last_rej;
    }

    public ValidityPeriod getValidity_period() {
        return validity_period;
    }

    public void setValidity_period(ValidityPeriod validity_period) {
        this.validity_period = validity_period;
    }

    public Package getServ_package() {
        return serv_package;
    }

    public void setServ_package(Package serv_package) {
        this.serv_package = serv_package;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

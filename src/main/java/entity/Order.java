package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity (name = "Ordeer")
@Table (name = "ordeer", schema = "telcoservice")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Date date_order;
    private Date date_sub;
    private boolean is_valid;
    private float total_price;

    //bi-directional Many-To-One association to ValidityPeriod
    @ManyToOne
    @JoinColumn (name = "VALIDITY_PERIOD")
    private ValidityPeriod validity_period;

    //bi-directional Many-To-One association to Package
    @ManyToOne
    @JoinColumn (name = "SERV_PACKAGE")
    private Package serv_package;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate_order() {
        return date_order;
    }

    public void setDate_order(Date date_order) {
        this.date_order = date_order;
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
}

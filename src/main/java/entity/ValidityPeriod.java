package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "validityperiod", schema = "telcoservice")
@NamedQueries({
        @NamedQuery(name = "ValidityPeriod.findAll", query = "SELECT v FROM ValidityPeriod v")
})
public class ValidityPeriod {
    @Id
    private int num_month;
    private float monthly_fee;

    //bi-directional One-To-Many association to Order
    @OneToMany(mappedBy = "validity_period", fetch = FetchType.LAZY)
    private List<Order> orders;

    public ValidityPeriod() {
    }

    public int getNum_month() {
        return num_month;
    }

    public void setNum_month(int num_month) {
        this.num_month = num_month;
    }

    public float getMonthly_fee() {
        return monthly_fee;
    }

    public void setMonthly_fee(float monthly_fee) {
        this.monthly_fee = monthly_fee;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

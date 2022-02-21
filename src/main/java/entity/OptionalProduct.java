package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "optionalproduct", schema = "telcoservice")
public class OptionalProduct {
    @Id
    private String name;
    private float monthly_fee;

    public OptionalProduct() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMonthly_fee() {
        return monthly_fee;
    }

    public void setMonthly_fee(float monthly_fee) {
        this.monthly_fee = monthly_fee;
    }
}

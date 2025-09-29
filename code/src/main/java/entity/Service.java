package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service", schema = "telcoservice")
@NamedQueries({
        @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s"),
})
public class Service {
    @Id
    @GeneratedValue
    private int id;
    private String type;
    private int n_min;
    private int n_sms;
    private int n_gbs;
    private float fee_min;
    private float fee_sms;
    private float fee_gbs;

    public Service() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getN_min() {
        return n_min;
    }

    public void setN_min(int n_min) {
        this.n_min = n_min;
    }

    public int getN_sms() {
        return n_sms;
    }

    public void setN_sms(int n_sms) {
        this.n_sms = n_sms;
    }

    public int getN_gbs() {
        return n_gbs;
    }

    public void setN_gbs(int n_gbs) {
        this.n_gbs = n_gbs;
    }

    public float getFee_min() {
        return fee_min;
    }

    public void setFee_min(float fee_min) {
        this.fee_min = fee_min;
    }

    public float getFee_sms() {
        return fee_sms;
    }

    public void setFee_sms(float fee_sms) {
        this.fee_sms = fee_sms;
    }

    public float getFee_gbs() {
        return fee_gbs;
    }

    public void setFee_gbs(float fee_gbs) {
        this.fee_gbs = fee_gbs;
    }
}

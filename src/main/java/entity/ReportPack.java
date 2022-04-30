package entity;

import javax.persistence.*;

@Entity(name = "ReportPack")
@Table(name = "report_package", schema = "telcoservice")
@NamedQuery(name = "ReportPack.findAll", query = "SELECT rp FROM ReportPack rp")
public class ReportPack {

    @Id
    @OneToOne
    @JoinColumn(
            name = "PACK_ID",
            referencedColumnName = "ID"
    )
    private Package id;
    private int numb_purchase;
    private float total_value_sales_with_op;
    private float total_value_sales_without_op;
    private float avg_optionalproduct;

    public ReportPack() {
    }

    public Package getId() {
        return id;
    }

    public void setId(Package id) {
        this.id = id;
    }

    public int getNumb_purchase() {
        return numb_purchase;
    }

    public void setNumb_purchase(int numb_purchase) {
        this.numb_purchase = numb_purchase;
    }

    public float getTotal_value_sales_with_op() {
        return total_value_sales_with_op;
    }

    public void setTotal_value_sales_with_op(float total_value_sales_with_op) {
        this.total_value_sales_with_op = total_value_sales_with_op;
    }

    public float getTotal_value_sales_without_op() {
        return total_value_sales_without_op;
    }

    public void setTotal_value_sales_without_op(float total_value_sales_without_op) {
        this.total_value_sales_without_op = total_value_sales_without_op;
    }

    public float getAvg_optionalproduct() {
        return avg_optionalproduct;
    }

    public void setAvg_optionalproduct(float avg_optionalproduct) {
        this.avg_optionalproduct = avg_optionalproduct;
    }
}

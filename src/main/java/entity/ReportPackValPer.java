package entity;

import javax.persistence.*;

@Entity(name = "ReportPackValPer")
@Table(name = "report_package_validityperiod", schema = "telcoservice")
@NamedQuery(name = "ReportPackValPer.findAll", query = "SELECT rpvp FROM ReportPackValPer rpvp")
@IdClass(ReportPackValPerID.class)
public class ReportPackValPer {

    @Id
    @ManyToOne
    @JoinColumn (
            name = "PACK_ID",
            referencedColumnName = "ID"
    )
    private Package id;

    @Id
    @ManyToOne
    @JoinColumn (
            name = "NUM_MONTH_VP",
            referencedColumnName = "NUM_MONTH"
    )
    private ValidityPeriod num_month;

    private float total_value_sales;

    public ReportPackValPer() {
    }

    public Package getId() {
        return id;
    }

    public void setId(Package id) {
        this.id = id;
    }

    public ValidityPeriod getNum_month() {
        return num_month;
    }

    public void setNum_month(ValidityPeriod num_month) {
        this.num_month = num_month;
    }

    public float getTotal_value_sales() {
        return total_value_sales;
    }

    public void setTotal_value_sales(float total_value_sales) {
        this.total_value_sales = total_value_sales;
    }
}

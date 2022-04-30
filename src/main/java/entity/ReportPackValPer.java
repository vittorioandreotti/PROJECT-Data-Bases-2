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

    private int numb_purchase;

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

    public int getNumb_purchase() {
        return numb_purchase;
    }

    public void setNumb_purchase(int numb_purchase) {
        this.numb_purchase = numb_purchase;
    }
}

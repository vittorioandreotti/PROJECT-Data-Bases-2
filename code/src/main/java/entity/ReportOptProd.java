package entity;

import javax.persistence.*;

@Entity (name = "ReportOptionalProduct")
@Table (name = "report_optional_product", schema = "telcoservice")
@NamedQuery(name = "ReportOptProd.bestSeller", query = "SELECT rop FROM ReportOptionalProduct rop ORDER BY rop.total_values_sales DESC")
public class ReportOptProd {

    @Id
    @OneToOne
    @JoinColumn (
            name = "NAME",
            referencedColumnName = "NAME"
    )
    private OptionalProduct name;
    private float total_values_sales;

    public ReportOptProd() {
    }

    public OptionalProduct getName() {
        return name;
    }

    public void setName(OptionalProduct name) {
        this.name = name;
    }

    public float getTotal_values_sales() {
        return total_values_sales;
    }

    public void setTotal_values_sales(float total_values_sales) {
        this.total_values_sales = total_values_sales;
    }
}

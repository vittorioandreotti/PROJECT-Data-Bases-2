package entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "service_schedule", schema = "telcoservice")
public class ServiceSchedule {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private int id;
    private LocalDate date_activation;
    private LocalDate date_deactivation;

    //uni-directional ManyToOne association to User
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "USER")
    private User user;

    //uni-directional ManyToOne association to Package
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "PACKAGE")
    private Package aPackage;

    //uni-directional ManyToOne association to OptionalProduct
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (
            name = "opt_sched",
            schema = "telcoservice",
            joinColumns = @JoinColumn (name = "SERVICE_SCHEDULE"),
            inverseJoinColumns = @JoinColumn (name = "OPTIONAL_PRODUCT"))
    private List<OptionalProduct> optionalProduct;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate_activation() {
        return date_activation;
    }

    public void setDate_activation(LocalDate date_activation) {
        this.date_activation = date_activation;
    }

    public LocalDate getDate_deactivation() {
        return date_deactivation;
    }

    public void setDate_deactivation(LocalDate date_deactivation) {
        this.date_deactivation = date_deactivation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public List<OptionalProduct> getOptionalProduct() {
        return optionalProduct;
    }

    public void setOptionalProduct(List<OptionalProduct> optionalProduct) {
        this.optionalProduct = optionalProduct;
    }
}
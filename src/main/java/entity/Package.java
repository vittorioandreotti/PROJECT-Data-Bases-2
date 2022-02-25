package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "package", schema = "telcoservice")
@NamedQueries({
        @NamedQuery(name = "Package.findAll", query = "SELECT p FROM Package p"),
})
public class Package {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    //bi-directional One-To-Many association to Order
    @OneToMany (mappedBy = "serv_package", fetch = FetchType.LAZY)
    private List<Order> orders;

    //bi-directional Many-To-Many association to Service
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (
            name = "comprises",
            schema = "telcoservice",
            joinColumns = { @JoinColumn (name = "PACKAGE") },
            inverseJoinColumns = { @JoinColumn (name = "SERVICE") }
    )
    private List<Service> services;

    //uni-directional Many-To-Many association to OptionalProduct
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (
            name = "contain",
            schema = "telcoservice",
            joinColumns = @JoinColumn (name = "PACKAGE"),
            inverseJoinColumns = @JoinColumn (name = "OPTIONAL_PRODUCT")
    )
    private List<OptionalProduct> optionalProducts;

    public Package() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }
}

package services;

import entity.*;
import entity.Package;
import org.eclipse.persistence.config.QueryHints;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class OrderService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;
    @EJB(name = "services/OptionalProductService")
    private OptionalProductService optionalProductService;
    @EJB(name = "services/UserService")
    private UserService userService;

    public OrderService() {
    }

    public void createOrder(LocalDate date_start_activation,
                            LocalDate date_end_activation,
                            float total_price,
                            ValidityPeriod validityPeriod,
                            Package serv_package,
                            List<OptionalProduct> optionalProducts,
                            User user,
                            boolean isValid) {
        Order newOrder = new Order();
        newOrder.setDate_start_activation(date_start_activation);
        newOrder.setTotal_price(total_price);
        newOrder.setValidity_period(validityPeriod);
        newOrder.setServ_package(serv_package);
        if (optionalProducts.isEmpty())
            newOrder.setOptionalProducts(optionalProducts);
        newOrder.setUser(user);
        newOrder.setIs_valid(isValid);
        newOrder.setDate_sub(LocalDateTime.now());
        if (!isValid) {
            newOrder.setDate_last_rej(LocalDateTime.now());
            newOrder.setRej_numb(1);
            user.setInsolvent(true);
        } else {
            ServiceSchedule serviceSchedule = new ServiceSchedule();
            serviceSchedule.setUser(user);
            serviceSchedule.setaPackage(serv_package);
            if (optionalProducts.isEmpty())
                serviceSchedule.setOptionalProduct(optionalProducts);
            serviceSchedule.setDate_activation(date_start_activation);
            serviceSchedule.setDate_deactivation(date_end_activation);
            this.entityManager.persist(serviceSchedule);
        }
        this.entityManager.persist(newOrder);
        this.entityManager.merge(user);
    }

    public void changeValidity(String orderId, boolean isValid) {
        Order order = this.findById(Integer.parseInt(orderId));
        order.setIs_valid(isValid);
        if (!isValid) {
            order.setDate_last_rej(LocalDateTime.now());
            order.addRej_numb();
        }
    }

    public float totalPrice(ValidityPeriod valPer, List<OptionalProduct> optProducts) {
        float priceOfPackage = valPer.getNum_month() * valPer.getMonthly_fee();
        float priceOfOptProds = 0;
        float totalPrice;

        if (optProducts != null) {
            for (OptionalProduct optProduct : optProducts) {
                priceOfOptProds += optProduct.getMonthly_fee() * valPer.getNum_month();
            }
        }

        //2 decimal digits
        totalPrice = Float.parseFloat(String.format("%.2f", priceOfOptProds + priceOfPackage).replace(",", "."));

        return totalPrice;
    }

    public List<Order> findByInsolventUser(User user) {
        List<Order> orders;
        orders = entityManager.createNamedQuery("Order.findByInsolventUser", Order.class).setParameter("user", user).getResultList();
        return orders;
    }

    public Order findById(int id) {
        return (entityManager.find(Order.class, id));
    }

    public List<Order> getSuspendedOrders () {
        List<Order> suspendedOrders;
        try {
            suspendedOrders = entityManager.createNamedQuery("Order.getSuspendedOrders", Order.class).setHint(QueryHints.REFRESH, true).getResultList();
        } catch (PersistenceException e){
            e.printStackTrace();
            suspendedOrders = null;
        }
        return suspendedOrders;
    }
}

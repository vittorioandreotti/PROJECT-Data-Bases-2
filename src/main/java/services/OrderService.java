package services;

import entity.Package;
import entity.Service;
import entity.ValidityPeriod;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;
    @EJB (name = "services/OptionalProductService")
    private OptionalProductService optionalProductService;

    public OrderService(){}

    public float totalPrice (ValidityPeriod valPer, String[] optProducts) {
        float priceOfPackage = valPer.getNum_month() * valPer.getMonthly_fee();
        float priceOfOptProd = 0;

        if (optProducts != null) {
            for (String optProduct : optProducts) {
                priceOfOptProd += optionalProductService.findByName(optProduct).getMonthly_fee() * valPer.getNum_month();
            }
        }
        return priceOfOptProd + priceOfPackage;
    }
}

package services;

import entity.ValidityPeriod;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class ValidityPeriodService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public ValidityPeriodService(){}

    public List<ValidityPeriod> findAll() {
        List<ValidityPeriod> validityPeriods= null;
        try {
           validityPeriods = entityManager.createNamedQuery("ValidityPeriod.findAll", ValidityPeriod.class).getResultList();
        }catch (PersistenceException exception){
            exception.printStackTrace();
        }
        return validityPeriods;
    }

    public ValidityPeriod findByNum_Month (int num_month) {
        return (entityManager.find(ValidityPeriod.class, num_month));
    }
}
package services;

import entity.ValidityPeriod;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Stateless
public class ValidityPeriodService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public ValidityPeriodService(){}

    public List<ValidityPeriod> findAllValidityPeriods () {
        List<ValidityPeriod> validityPeriods= null;
        try {
           validityPeriods = entityManager.createNamedQuery("ValidityPeriod.findAll", ValidityPeriod.class).getResultList();
        }catch (PersistenceException exception){
            exception.printStackTrace();
        }
        return validityPeriods;
    }
}

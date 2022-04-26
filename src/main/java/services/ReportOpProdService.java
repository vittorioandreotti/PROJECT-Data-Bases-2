package services;

import entity.ReportOptProd;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class ReportOpProdService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public ReportOpProdService () {}

    public List<ReportOptProd> findAll() {
        List<ReportOptProd> reportOptProds;

        try {
            reportOptProds = entityManager.createNamedQuery("ReportOptProd.findAll", ReportOptProd.class).getResultList();
        } catch (PersistenceException e) {
            reportOptProds = null;
            e.printStackTrace();
        }
        return reportOptProds;
    }
}

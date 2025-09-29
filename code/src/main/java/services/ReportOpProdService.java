package services;

import entity.ReportOptProd;
import org.eclipse.persistence.config.QueryHints;

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

    public ReportOptProd bestSeller() {
        ReportOptProd reportOptProds;

        try {
            reportOptProds = entityManager.createNamedQuery("ReportOptProd.bestSeller", ReportOptProd.class).setHint(QueryHints.REFRESH, true).setMaxResults(1).getSingleResult();
        } catch (PersistenceException e) {
            reportOptProds = null;
            e.printStackTrace();
        }
        return reportOptProds;
    }
}

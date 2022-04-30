package services;

import entity.ReportPack;
import org.eclipse.persistence.config.QueryHints;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class ReportPackService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public ReportPackService () {}

    public List<ReportPack> findAll() {
        List<ReportPack> reportPackServices;

        try {
            reportPackServices = entityManager.createNamedQuery("ReportPack.findAll", ReportPack.class).setHint(QueryHints.REFRESH, true).getResultList();
        } catch (PersistenceException e) {
            reportPackServices = null;
            e.printStackTrace();
        }
        return reportPackServices;
    }
}

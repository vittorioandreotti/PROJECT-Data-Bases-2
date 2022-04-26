package services;

import entity.ReportPackValPer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class ReportPackValPerService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public ReportPackValPerService () {}

    public List<ReportPackValPer> findAll() {
        List<ReportPackValPer> reportPackValPers;

        try {
            reportPackValPers = entityManager.createNamedQuery("ReportPackValPer.findAll", ReportPackValPer.class).getResultList();
        } catch (PersistenceException e) {
            reportPackValPers = null;
            e.printStackTrace();
        }
        return reportPackValPers;
    }
}

package services;

import entity.Alert;
import entity.OptionalProduct;
import entity.User;
import org.eclipse.persistence.config.QueryHints;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class AlertService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public AlertService () {}

    public Alert findByUser (User user) {
        Alert alert;
        try {
            alert = entityManager.createNamedQuery("Alert.findByUsername", Alert.class).setParameter("user", user).setHint(QueryHints.REFRESH, true).getSingleResult();
        } catch (NoResultException e) {
            alert = null;
        }
        return alert;
    }

    public List<Alert> findAll () {
        List<Alert> alerts;

        try {
            alerts = entityManager.createNamedQuery("Alert.findAll", Alert.class).setHint(QueryHints.REFRESH, true).getResultList();
        } catch (PersistenceException e) {
            alerts = null;
            e.printStackTrace();
        }
        return alerts;
    }
}

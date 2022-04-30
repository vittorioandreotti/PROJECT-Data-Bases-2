package services;

import entity.Package;
import entity.Service;
import entity.User;
import org.eclipse.persistence.config.QueryHints;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ServiceService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public ServiceService () {}

    public List<Service> findAll () {
        List <Service> services= null;

        try {
            services = entityManager.createNamedQuery("Service.findAll", Service.class).setHint(QueryHints.REFRESH, true).getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return services;
    }

    public Service findById (Integer id) {
        return (entityManager.find(Service.class, id));
    }

    public List<Service> findSet (String[] servIds) {
        List<Service> services = new ArrayList<Service>();
        Service service;
        for (String id : servIds) {
            service = this.findById(Integer.parseInt(id));
            services.add(service);
        }
        return services;
    }
}

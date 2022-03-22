package services;

import entity.Package;
import entity.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class PackageService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public PackageService () {}

    public List<Package> findAllPackages() {
        List packages = null;

        try {
            packages = entityManager.createNamedQuery("Package.findAll", Package.class).getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return packages;
    }

    public Package findById (Integer id) {
        return (entityManager.find(Package.class, id));
    }
}

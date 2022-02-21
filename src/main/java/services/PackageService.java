package services;

import entity.Package;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class PackageService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public PackageService () {}

    public List<Package> getPackInfo() {
        List packages = null;

        try {
            packages = entityManager.createNamedQuery("Package.findAll", Package.class).getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return packages;
    }
}

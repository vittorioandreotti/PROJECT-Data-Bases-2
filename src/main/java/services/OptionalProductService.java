package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OptionalProductService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public OptionalProductService (){}

}

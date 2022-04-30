package services;

import entity.OptionalProduct;
import exception.MoreThanOneElement;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OptionalProductService {

    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public OptionalProductService () {}

    public List<OptionalProduct> findAll () {
        List <OptionalProduct> optionalProducts = null;

        try {
            optionalProducts = entityManager.createNamedQuery("OptionalProduct.findAll", OptionalProduct.class).getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return optionalProducts;
    }

    public OptionalProduct findByName (String optProdName) {
        return entityManager.find(OptionalProduct.class, optProdName);
    }

    public List<OptionalProduct> findSet (String[] optProdNames) {
        List<OptionalProduct> optionalProducts = new ArrayList<OptionalProduct>();
        OptionalProduct optionalProduct;
        if(optProdNames == null) {
            optionalProducts = null;
            return optionalProducts;
        }
        else {
            for (String name : optProdNames) {
                optionalProduct = this.findByName(name);
                optionalProducts.add(optionalProduct);
            }
            return optionalProducts;
        }
    }

    public ArrayList<String> getOptProdsNames (List<OptionalProduct> optionalProductList) {
        ArrayList<String> optProdsNames = new ArrayList<>();
        String optProdName;
        for (OptionalProduct opProd : optionalProductList) {
            optProdName = opProd.getName();
            optProdsNames.add(optProdName);
        }
        return optProdsNames;
    }

    public void createOptProd (String name, Float monthlyFee) throws MoreThanOneElement {
        OptionalProduct optionalProduct = this.findByName(name);
        if (optionalProduct != null){
            throw new MoreThanOneElement();
        }
        OptionalProduct newOptionalProduct = new OptionalProduct();
        newOptionalProduct.setName(name);
        newOptionalProduct.setMonthly_fee(monthlyFee);
        this.entityManager.persist(newOptionalProduct);
    }
}

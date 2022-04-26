package services;

import entity.User;
import org.eclipse.persistence.config.QueryHints;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.security.auth.login.CredentialException;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public UserService(){
    }

    public User checkCredentials(String username, String password) throws CredentialException, NonUniqueResultException {
        List<User> userList;
        try {
            userList = entityManager.createNamedQuery("User.checkCredentials", User.class).setParameter("username", username).setParameter("password", password).getResultList();
        } catch (PersistenceException exception) {
            throw new CredentialNotFoundException("Wrong credentials");
        }
        if (userList.isEmpty())
            return null;
        else if (userList.size() != 1)
            throw new NonUniqueResultException("More than one User with this credentials");
        else
            return userList.get(0);
    }

    public void createUser(String username, String email, String password) throws CredentialException {
        User user = this.findByUsername(username);
        if (user != null) {
            throw new CredentialException("Existing username: try another one");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUser_type(false);
        this.entityManager.persist(newUser);
    }

    //Without refresh
    public User findByUsername (String username) {
        return (entityManager.find(User.class, username));
    }

    //With refresh
    public User findByUsernameNamedQuery (String username) {
        User user;
        try {
            user = entityManager.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).setHint(QueryHints.REFRESH, true).getSingleResult();
        } catch (NoResultException e)
        {
            user = null;
        }
        return user;
    }
}

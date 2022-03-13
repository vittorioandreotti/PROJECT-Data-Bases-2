package services;

import entity.User;
import exception.UserTypeException;
import org.eclipse.persistence.exceptions.DatabaseException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.security.auth.login.CredentialException;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;

@Stateless
public class UserService {
    @EJB(name = "services/UserService")
    private UserService userService;
    @PersistenceContext(unitName = "telcoservice")
    private EntityManager entityManager;

    public UserService(){
    }

    public User checkCredentials(String username, String password) throws CredentialException, NonUniqueResultException, UserTypeException {
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
        else if (userList.size() == 1 && !userList.get(0).isUser_type())
            return userList.get(0);
        else
            throw new UserTypeException("These credentials are related to an Employee. Please, login with Consumer's credential");
    }

    public void createUser(String username, String email, String password) throws CredentialException {
        User user = userService.findByUsername(username);
        if(user != null) {
            throw new CredentialException("Existing username: try another one");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUser_type(false);
        this.entityManager.persist(newUser);
    }

    public User findByUsername (String username) {
        return (entityManager.find(User.class, username));
    }
}

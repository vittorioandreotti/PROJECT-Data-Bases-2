package controllers;

import entity.User;
import exception.UserTypeException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.UserService;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.security.auth.login.CredentialException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Log-In", value = "/login")
public class Login extends HttpServlet {
    @EJB(name = "services/UserService")
    private UserService userService;
    private TemplateEngine templateEngine;
    public Login() {}

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        String username = request.getParameter("username_log");
        String password = request.getParameter("password_log");

        User user = null;
        try {
            user = userService.checkCredentials(username, password);
        } catch (CredentialException | NonUniqueResultException exception) {
//            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credential");
        }
        catch (UserTypeException exception) {
//            exception.printStackTrace();
            ctx.setVariable("errmsg", exception.getMessage());
            String path = servletContext.getContextPath() + "/loginregister.html";
            this.templateEngine.process(path, ctx, response.getWriter());
        }

        String path;
        if (user == null) {
            ctx.setVariable("errmsg", "Incorrect username or password");
            path = servletContext.getContextPath() + "/loginregister.html";
            this.templateEngine.process(path, ctx, response.getWriter());
        }
        else {
            request.getSession().setAttribute("user", user);
            path = servletContext.getContextPath() + "/getpackinfo";
            response.sendRedirect(path);
        }
    }
}

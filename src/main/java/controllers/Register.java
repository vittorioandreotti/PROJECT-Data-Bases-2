package controllers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.UserService;

import javax.ejb.EJB;
import javax.security.auth.login.CredentialException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Register", value = "/register")
public class Register extends HttpServlet {
    @EJB (name = "services/UserService")
    private UserService userService;
    private TemplateEngine templateEngine;

    public Register() {}

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username_reg");
        String email = request.getParameter("email_reg");
        String password = request.getParameter("password_reg");
        try {
            userService.createUser(username, email, password);
        } catch (CredentialException e) {

        }
        String path = getServletContext().getContextPath() + "/loginregister.html";
        response.sendRedirect(path);
    }
}

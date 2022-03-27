package controllers;

import entity.User;
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
    private static final long serialVersionUID = 1L;
    @EJB(name = "services/UserService")
    private UserService userService;
    private TemplateEngine templateEngine;

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
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        String username = request.getParameter("username_log");
        String password = request.getParameter("password_log");

        User user = null;
        try {
            user = userService.checkCredentials(username, password);
        } catch (CredentialException | NonUniqueResultException exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credential");
        }

        String path;

        //Error message
        if (user == null) {
            ctx.setVariable("loginmsg", "Incorrect username or password");
            path = "/LoginRegister";
            this.templateEngine.process(path, ctx, response.getWriter());
            return;
        }
        else {
            request.getSession().setAttribute("username", user.getUsername());

            //Check on UserType end redirect to correct HomePage
            if (!user.isUser_type()) {
                path = getServletContext().getContextPath() + "/getpackinfo";
                response.sendRedirect(path);
            }
            else {
                path = getServletContext().getContextPath() + "/GoToEmployeeHomePage";
                response.sendRedirect(path);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    public void destroy(){

    }
}

package controllers;

import entity.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.WebConnection;
import java.io.IOException;

@WebServlet(name = "Confirmation", value = "/confirmation")
public class Confirmation extends HttpServlet {
    @EJB(name = "services/UserServices")
    private UserService userService;
    private TemplateEngine templateEngine;

    public Confirmation () {}

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());

        User user = null;
        String username = (String)req.getSession().getAttribute("username");
        if (username != null){
            ctx.setVariable("package", (String)req.getSession().getAttribute("select_pack"));
            ctx.setVariable("validity", (String)req.getSession().getAttribute("select_pack"));

        } else {
            String path = servletContext.getContextPath() + "/buyservice";
            resp.sendRedirect(path);
        }
    }
}

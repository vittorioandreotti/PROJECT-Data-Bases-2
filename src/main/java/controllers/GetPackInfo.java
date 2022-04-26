package controllers;

import entity.Alert;
import entity.Order;
import entity.Package;
import entity.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.AlertService;
import services.OrderService;
import services.PackageService;
import services.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetPackInfo", value = "/getpackinfo")
public class GetPackInfo extends HttpServlet {
    @EJB (name = "services/PackageService")
    private PackageService packageService;
    @EJB (name = "services/UserService")
    private UserService userService;
    @EJB (name = "services/OrderService")
    private OrderService orderService;
    @EJB (name = "services/AlertServices")
    private AlertService alertService;
    private TemplateEngine templateEngine;

    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //Get Service Package and Orders Information
        List<Package> packages;
        List<Order> orders = null;
        User user = null;
        String username = null;
        Alert alert = null;
        boolean isInsolvent = false;

        if (request.getSession().getAttribute("username") != null) {
            user = userService.findByUsernameNamedQuery((String) request.getSession().getAttribute("username"));
            username = user.getUsername();
            isInsolvent = user.isInsolvent();
            orders = orderService.findByInsolventUser(user);
            alert = alertService.findByUser(user);
        }
        
        packages = packageService.findAllPackages();

        String path = "/index";
        ServletContext servletContext = this.getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("packages", packages);
        ctx.setVariable("username", username);
        ctx.setVariable("isInsolvent", isInsolvent);
        ctx.setVariable("orders", orders);
        ctx.setVariable("alert", alert);
        this.templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}

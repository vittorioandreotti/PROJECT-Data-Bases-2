package controllers;

import entity.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.*;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SalesReport", value = "/SalesReport")
public class SalesReport extends HttpServlet {
    @EJB(name = "services/ReportOpProdService")
    private ReportOpProdService reportOpProdService;
    @EJB(name = "services/ReportPackService")
    private ReportPackService reportPackService;
    @EJB(name = "services/ReportPackValPerService")
    private ReportPackValPerService reportPackValPerService;
    @EJB(name = "services/AlertService")
    private AlertService alertService;
    @EJB(name = "services/UserService")
    private UserService userService;
    @EJB(name = "services/OrderService")
    private OrderService orderService;
    private TemplateEngine templateEngine;

    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.addDialect(new Java8TimeDialect());
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List <Alert> alerts;
        List <ReportPack> reportPacks;
        ReportOptProd reportOptProds;
        List <ReportPackValPer> reportPackValPers;
        List <User> insolventUsers;
        List <Order> suspendedOrders;

        alerts = alertService.findAll();
        reportPacks = reportPackService.findAll();
        reportOptProds = reportOpProdService.bestSeller();
        reportPackValPers = reportPackValPerService.findAll();
        insolventUsers = userService.getInsolventUsers();
        suspendedOrders = orderService.getSuspendedOrders();

        String path = "/SalesReport";
        ServletContext servletContext= this.getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("alerts", alerts);
        ctx.setVariable("reportPacks", reportPacks);
        ctx.setVariable("reportOptProds", reportOptProds);
        ctx.setVariable("reportPackValPers", reportPackValPers);
        ctx.setVariable("insolventUsers", insolventUsers);
        ctx.setVariable("suspendedOrders", suspendedOrders);
        ctx.setVariable("username", request.getSession().getAttribute("usernameEmployee"));
        this.templateEngine.process(path, ctx, response.getWriter());
    }
}

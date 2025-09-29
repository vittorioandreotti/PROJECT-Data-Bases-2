package controllers;

import entity.*;
import entity.Package;
import exception.UserNotLoggedIn;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.*;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.WebConnection;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ConfirmationOrder", value = "/confirmationorder")
public class ConfirmationOrder extends HttpServlet {
    @EJB(name = "services/UserService")
    private UserService userService;
    @EJB(name = "services/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService;
    @EJB(name = "services/OrderService")
    private OrderService orderService;
    @EJB(name = "service/PackageService")
    private PackageService packageService;
    @EJB(name = "services/OptionalProductService")
    private OptionalProductService optionalProductService;
    private TemplateEngine templateEngine;

    public ConfirmationOrder () {}

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.addDialect(new Java8TimeDialect());
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = this.getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        //Declaration here because of try-catch block
        Order order;
        Package aPackage;
        ValidityPeriod valPer;
        List<OptionalProduct> optProds;
        ArrayList<String> optProdsNames;
        LocalDate dateStartSub;
        float totalPrice;

        order = orderService.findById(Integer.parseInt(request.getParameter("orderId")));
        aPackage = order.getServ_package();
        valPer = order.getValidity_period();
        optProds = order.getOptionalProducts();
        optProdsNames = optionalProductService.getOptProdsNames(optProds);
        dateStartSub = order.getDate_start_activation();
        totalPrice = order.getTotal_price();

        ctx.setVariable("username", request.getSession().getAttribute("usernameConsumer"));
        ctx.setVariable("package", aPackage);
        ctx.setVariable("validity", valPer);
        ctx.setVariable("opProds", optProdsNames);
        ctx.setVariable("dateSub", dateStartSub);
        ctx.setVariable("totalPrice",totalPrice);
        ctx.setVariable("orderId", order.getId());

        String path = "/Confirmation.html";
        this.templateEngine.process(path, ctx, response.getWriter());
    }
}


package controllers;

import entity.OptionalProduct;
import entity.Package;
import entity.User;
import entity.ValidityPeriod;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Confirmation", value = "/confirmation")
public class Confirmation extends HttpServlet {
    @EJB(name = "services/UserService")
    private UserService userService;
    @EJB(name = "services/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService;
    @EJB(name = "services/OrderService")
    private OrderService orderService;
    @EJB(name = "service/PackageService")
    private PackageService packageService;
    @EJB(name = "service/OptionalProductService")
    private OptionalProductService optionalProductService;
    private TemplateEngine templateEngine;

    public Confirmation () {}

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //Declaration here because of try-catch block
        Package aPackage;
        ValidityPeriod valPer;
        List<OptionalProduct> optProds;
        ArrayList<String> optProdsNames;
        LocalDate dateSub;
        String totalPrice;

        User user = null;
        if (request.getSession().getAttribute("usernameConsumer") != null)
            user = userService.findByUsername((String)request.getSession().getAttribute("usernameConsumer"));
        try {
            aPackage = packageService.findById(Integer.parseInt(request.getParameter("select_pack")));
            valPer = validityPeriodService.findByNum_Month(Integer.parseInt(request.getParameter("select_valPer")));
            optProds = optionalProductService.findSet(request.getParameterValues("select_optProd"));
            optProdsNames = optionalProductService.getOptProdsNames(optProds);
            dateSub = LocalDate.parse((request.getParameter("dateSub")));
            totalPrice = Float.toString(orderService.totalPrice(valPer, optProds));
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("msg", "--- is not valid, please select one element in the list.");
            String path = servletContext.getContextPath() + "/buyservice";
            response.sendRedirect(path);
            return;
        }

        if (dateSub.isBefore(LocalDate.now())) {
           request.getSession().setAttribute("msg", "The start date of subscription must be equal or later than the current date.");

           String path = servletContext.getContextPath() + "/buyservice";
           response.sendRedirect(path);
           return;
        }

        if (user != null){

            ctx.setVariable("username", user.getUsername());
            ctx.setVariable("package", aPackage);
            ctx.setVariable("validity", valPer);
            ctx.setVariable("opProds", optProdsNames);
            ctx.setVariable("dateSub", dateSub);
            ctx.setVariable("totalPrice",totalPrice);

            String path = "/Confirmation";
            this.templateEngine.process(path, ctx, response.getWriter());
        } else {
            request.getSession().setAttribute("msg", "You are not logged in, please Log-In before create an Order.");
            String path = servletContext.getContextPath() + "/buyservice";
            response.sendRedirect(path);
        }
    }
}

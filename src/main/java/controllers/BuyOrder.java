package controllers;

import entity.*;
import entity.Package;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.*;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "BuyOrder", value = "/buyorder")
public class BuyOrder extends HttpServlet {
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

    public BuyOrder() {}

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        User user = userService.findByUsername((String) request.getSession().getAttribute("username"));
        Package aPackage = packageService.findById(Integer.valueOf(request.getParameter("package")));
        ValidityPeriod valPer = validityPeriodService.findByNum_Month(Integer.parseInt(request.getParameter("validityPeriod")));
        List<OptionalProduct> optProds = optionalProductService.findSet(request.getParameterValues("opProds"));
        LocalDate dateStartSub = LocalDate.parse(request.getParameter("dateSub"));
        LocalDate dateEndSub = dateStartSub.plusMonths(valPer.getNum_month());
        float totalPrice = Float.parseFloat(request.getParameter("totalPrice"));
        boolean isValid = Boolean.parseBoolean(request.getParameter("validity"));
        String orderId = request.getParameter("orderId");

        String path = "/Confirmation.html";
        ctx.setVariable("username", user.getUsername());
        ctx.setVariable("package", aPackage);
        ctx.setVariable("validity", valPer);
        ctx.setVariable("opProds", request.getParameterValues("opProds"));
        ctx.setVariable("dateSub", dateStartSub);
        ctx.setVariable("totalPrice", totalPrice);

        if (Objects.equals(orderId, "")) {
            try {
                orderService.createOrder(dateStartSub, dateEndSub, totalPrice, valPer, aPackage, optProds, user, isValid);
            } catch (Exception e) {
                ctx.setVariable("ordermsg", "Something went wrong. Order not created");
                this.templateEngine.process(path, ctx, response.getWriter());
                return;
            }

            ctx.setVariable("ordermsg", "New order has been created");
            this.templateEngine.process(path, ctx, response.getWriter());
        }
        else {
            orderService.changeValidity(orderId, isValid);

            this.templateEngine.process(path, ctx, response.getWriter());
        }
    }
}

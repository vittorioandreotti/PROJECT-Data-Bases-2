package controllers;

import entity.OptionalProduct;
import entity.Package;
import entity.User;
import entity.ValidityPeriod;
import exception.UserNotLoggedIn;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = this.getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        User user = null;
        if (request.getSession().getAttribute("username") != null)
            user = userService.findByUsername((String)request.getSession().getAttribute("username"));
        Package aPackage = packageService.findById(Integer.parseInt(request.getParameter("select_pack")));
        ValidityPeriod valPer = validityPeriodService.findByNum_Month(Integer.parseInt(request.getParameter("select_valPer")));
        String[] optProds = request.getParameterValues("select_optProd");
        String dateSub = request.getParameter("dateSub");
        String totalPrice = Float.toString(orderService.totalPrice(valPer, optProds));

        if (user != null && !user.isUser_type()){
            ctx.setVariable("username", user.getUsername());
            ctx.setVariable("package", aPackage.getName());
            ctx.setVariable("validity", valPer.getNum_month());
            ctx.setVariable("opProd", optProds);
            ctx.setVariable("dateSub", dateSub);
            ctx.setVariable("totalPrice",totalPrice);

            String path = "/Confirmation.html";
            this.templateEngine.process(path, ctx, response.getWriter());
        } else {
            request.getSession().setAttribute("loginmsg", "You are not logged in, please Log-In before create an Order.");
            String path = servletContext.getContextPath() + "/buyservice";
            response.sendRedirect(path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

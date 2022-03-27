package controllers;

import entity.Package;
import entity.ValidityPeriod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.PackageService;
import services.ValidityPeriodService;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BuyService", value = "/buyservice")
public class BuyService extends HttpServlet {
    @EJB (name = "services/PackageService")
    private PackageService packageService;
    @EJB (name = "services/ValidityPeriod")
    private ValidityPeriodService validityPeriodService;
    private TemplateEngine templateEngine;

    public BuyService(){}

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
        //Get all Packages and Validity Periods to display in select
        List<Package> packages = null;
        List<ValidityPeriod> validityPeriods = null;
        try {
            packages = packageService.findAllPackages();
            validityPeriods = validityPeriodService.findAll();
        } catch (Exception e) {
            response.sendError(500, "Not possible to get data");
        }

        String path = "/BuyService.html";
        ServletContext servletContext = this.getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("packages", packages);
        ctx.setVariable("validityPeriods", validityPeriods);
        ctx.setVariable("loginmsg", request.getSession().getAttribute("loginmsg"));
        this.templateEngine.process(path, ctx, response.getWriter());

        request.getSession().removeAttribute("loginmsg");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}

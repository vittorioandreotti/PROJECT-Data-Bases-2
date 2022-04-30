package controllers;

import entity.OptionalProduct;
import entity.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.OptionalProductService;
import services.ServiceService;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GoToEmployeeHomePage", value = "/GoToEmployeeHomePage")
public class GoToEmployeeHomePage extends HttpServlet {
    @EJB(name = "services/ServiceService")
    private ServiceService serviceService;
    @EJB(name = "services/OptionalProductService")
    private OptionalProductService optionalProductService;
    private TemplateEngine templateEngine;

    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get all Services and Optional Products to display in select
        List<Service> services;
        List<OptionalProduct> optionalProducts;
        try {
            services = serviceService.findAll();
            optionalProducts = optionalProductService.findAll();
        } catch (Exception e) {
            response.sendError(500, "Not possible to get data");
            return;
        }

        String msgOptProd = (String) request.getSession().getAttribute("createOptProdmsg");
        String msgPack = (String) request.getSession().getAttribute("createPackmsg");
        String path = "EmployeeHomePage";
        ServletContext servletContext = this.getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("optionalProducts", optionalProducts);
        ctx.setVariable("services", services);
        ctx.setVariable("createOptProdmsg", msgOptProd);
        ctx.setVariable("createPackmsg", msgPack);
        ctx.setVariable("username", request.getSession().getAttribute("usernameEmployee"));
        this.templateEngine.process(path, ctx, response.getWriter());

        request.getSession().removeAttribute("createOptProdmsg");
        request.getSession().removeAttribute("createPackmsg");
    }
}

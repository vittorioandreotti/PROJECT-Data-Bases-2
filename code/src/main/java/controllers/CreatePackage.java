package controllers;

import entity.OptionalProduct;
import entity.Package;
import entity.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.OptionalProductService;
import services.PackageService;
import services.ServiceService;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CreatePackage", value = "/CreatePackage")
public class CreatePackage extends HttpServlet {
    @EJB(name = "services/ServiceService")
    private ServiceService serviceService;
    @EJB(name = "services/OptionalProductService")
    private OptionalProductService optionalProductService;
    @EJB(name = "services/PackageService")
    private PackageService packageService;
    private TemplateEngine templateEngine;

    public CreatePackage(){}

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String path;

        String name = request.getParameter("packName");
        String[] servIds = request.getParameterValues("services");
        String[] optProdNames = request.getParameterValues("optProd");

        List<Service> services = serviceService.findSet(servIds);
        List<OptionalProduct> optionalProducts = optionalProductService.findSet(optProdNames);

        packageService.createPackage(name, services, optionalProducts);

        request.getSession().setAttribute("createPackmsg", "New Package created.");
        path = getServletContext().getContextPath() + "/GoToEmployeeHomePage";
        response.sendRedirect(path);
    }
}

package controllers;

import exception.MoreThanOneElement;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.OptionalProductService;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateNewOptProd", value = "/CreateNewOptProd")
public class CreateOptProd extends HttpServlet {
    @EJB(name = "services/OptionalProductService")
    private OptionalProductService optionalProductService;
    private TemplateEngine templateEngine;

    public CreateOptProd() {}

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

        String name = request.getParameter("optProdName");
        float monthlyFee = 0;

        //Check if monthlyFee is a number
        try {
            monthlyFee = Float.parseFloat(request.getParameter("optProdMonthlyFee"));
        } catch (NumberFormatException exception) {
            request.getSession().setAttribute("createOptProdmsg", "Monthly Fee must be a number with Dot");
            path = getServletContext().getContextPath() + "/GoToEmployeeHomePage";
            response.sendRedirect(path);
            return;
        }
        //Check if there is an optional product with the same name
        try {
            optionalProductService.createOptProd(name, monthlyFee);
        } catch (MoreThanOneElement exception) {
            request.getSession().setAttribute("createOptProdmsg", exception.getMessage());
            path = getServletContext().getContextPath() + "/GoToEmployeeHomePage";
            response.sendRedirect(path);
            return;
        }
        //Product created
        request.getSession().setAttribute("createOptProdmsg", "New Optional Product created.");
        path = getServletContext().getContextPath() + "/GoToEmployeeHomePage";
        response.sendRedirect(path);
    }
}

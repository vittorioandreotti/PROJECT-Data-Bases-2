package controllers;

import entity.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import services.PackageService;
import services.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.WebConnection;
import java.io.IOException;

@WebServlet(name = "Confirmation", value = "/confirmation")
public class Confirmation extends HttpServlet {
    @EJB(name = "services/UserServices")
    private UserService userService;
    @EJB(name = "services/PackageServices")
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        String username = (String)request.getSession().getAttribute("username");
        String packageName = packageService.findById(Integer.valueOf(request.getParameter("select_pack"))).getName();
        if (username != null){
            ctx.setVariable("package", packageName);
            ctx.setVariable("validity", request.getParameter("select_valPer"));
            ctx.setVariable("opprod", request.getParameter("select_optProd"));
            ctx.setVariable("dateSub", request.getParameter("dateSub"));
            String path = "/Confirmation.html";
            this.templateEngine.process(path, ctx, response.getWriter());
        } else {
            String path = servletContext.getContextPath() + "/buyservice";
            //response.
            response.sendRedirect(path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

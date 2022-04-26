package controllers;

import entity.Alert;
import entity.ReportOptProd;
import entity.ReportPack;
import entity.ReportPackValPer;
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

        List <Alert> alerts;
        List <ReportPack> reportPacks;
        List <ReportOptProd> reportOptProds;
        List <ReportPackValPer> reportPackValPers;

        alerts = alertService.findAll();
        reportPacks = reportPackService.findAll();
        reportOptProds = reportOpProdService.findAll();
        reportPackValPers = reportPackValPerService.findAll();

        String path = "/SalesReport";
        ServletContext servletContext= this.getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("alerts", alerts);
        ctx.setVariable("reportPacks", reportPacks);
        ctx.setVariable("reportOptProds", reportOptProds);
        ctx.setVariable("reportPackValPers", reportPackValPers);
        this.templateEngine.process(path, ctx, response.getWriter());
    }
}

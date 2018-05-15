//package com.cxy.dao.basedao;
//
//import java.util.HashMap;
//import java.util.Map;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
///**
// * Created by Administrator on 2018/5/14.
// */
//public abstract class BaseStartupListener implements ServletContextListener
//{
//    public static final Log log = LogFactory.getLog(BaseStartupListener.class);
//
//    public void contextInitialized(ServletContextEvent event)
//    {
//        ServletContext context = event.getServletContext();
//        System.setProperty("basePath", context.getRealPath("/"));
//        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
//        Constants.ctx = ctx;
//
//        Map config = (HashMap)context.getAttribute("appConfig");
//        if (config == null) config = new HashMap();
//        if (context.getInitParameter("csstheme") != null)
//            config.put("csstheme", context.getInitParameter("csstheme"));
//        if (context.getInitParameter("casUserinfo") != null)
//            config.put("casUserinfo", context.getInitParameter("casUserinfo"));
//        context.setAttribute("appConfig", config);
//
//        String[] beans = ctx.getBeanDefinitionNames();
//        for (String bean : beans)
//            log.debug(bean);
//        startupInitialized(event);
//    }
//
//    public abstract void startupInitialized(ServletContextEvent paramServletContextEvent);
//}

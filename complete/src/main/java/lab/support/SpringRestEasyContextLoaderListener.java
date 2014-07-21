package lab.support;

import org.jboss.resteasy.plugins.spring.SpringContextLoaderSupport;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;

public class SpringRestEasyContextLoaderListener extends ContextLoaderListener {
    private SpringContextLoaderSupport springContextLoaderSupport = new SpringContextLoaderSupport();

    @Override
    protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext configurableWebApplicationContext) {
        super.customizeContext(servletContext, configurableWebApplicationContext);
        this.springContextLoaderSupport.customizeContext(servletContext, configurableWebApplicationContext);
    }
}

package com.sohu.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;  
  
@Configuration  
public class WebServerConfiguration  
{  
	@Bean
	public ServletWebServerFactory servletContainer() {
	    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
	    tomcat.addConnectorCustomizers(new GwsTomcatConnectionCustomizer());
	    return tomcat;
	}
	
	
    /**
    *
    * 默认http连接
    *
    *
    */
   public class GwsTomcatConnectionCustomizer implements TomcatConnectorCustomizer {

       public GwsTomcatConnectionCustomizer() {
       }

       @Override
       public void customize(Connector connector) {
           connector.setPort(Integer.valueOf(8080));
//           connector.setAttribute("connectionTimeout", connectionTimeout);
//           connector.setAttribute("acceptorThreadCount", acceptorThreadCount);
//           connector.setAttribute("minSpareThreads", minSpareThreads);
//           connector.setAttribute("maxSpareThreads", maxSpareThreads);
           connector.setAttribute("maxThreads", 2000);
           connector.setAttribute("maxConnections", 2000);
//           connector.setAttribute("protocol", protocol);
//           connector.setAttribute("redirectPort", "redirectPort");
//           connector.setAttribute("compression", "compression");
       }
   }
}  

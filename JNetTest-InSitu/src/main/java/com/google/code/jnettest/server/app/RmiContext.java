package com.google.code.jnettest.server.app;

import static com.google.code.jnettest.server.app.RmiExporter.SERVICE_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.google.code.jnettest.server.AppConfiguration;

@Configuration
public class RmiContext {
    
    @Autowired
    private AppConfiguration appConfiguration;
    
    /**
     * @see http://stackoverflow.com/questions/17857770/spring-properly-setup-componentscan
     */
    @Bean
    public RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxy = new RmiProxyFactoryBean();
        rmiProxy.setServiceUrl("rmi://" 
                + appConfiguration.getRemoteHostName() 
                + ":" 
                + appConfiguration.getRemoteRmiPort() 
                + "/" + SERVICE_NAME);
        rmiProxy.setServiceInterface(CommandService.class);
        rmiProxy.setLookupStubOnStartup(false);
        rmiProxy.afterPropertiesSet();
        return rmiProxy;
    }
}

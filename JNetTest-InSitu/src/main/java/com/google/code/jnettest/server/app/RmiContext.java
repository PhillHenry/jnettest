package com.google.code.jnettest.server.app;

import static com.google.code.jnettest.server.Main.CLIENT_PROXY_BEAN_NAME;
import static com.google.code.jnettest.server.app.RmiExporter.SERVICE_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.google.code.jnettest.server.AppConfiguration;

@Configuration(value=CLIENT_PROXY_BEAN_NAME)
public class RmiContext {
    
    @Autowired
    private AppConfiguration appConfiguration;
    private RmiProxyFactoryBean rmiProxy;
    
    /**
     * @see http://stackoverflow.com/questions/17857770/spring-properly-setup-componentscan
     */
    @Bean
    public RmiProxyFactoryBean service() {
        rmiProxy = new RmiProxyFactoryBean();
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
    
    public CommandService getClientProxy() {
        return (CommandService) rmiProxy.getObject();
    }
}

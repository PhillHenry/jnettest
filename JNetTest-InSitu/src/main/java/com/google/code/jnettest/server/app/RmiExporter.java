package com.google.code.jnettest.server.app;

import java.rmi.RemoteException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.stereotype.Component;

import com.google.code.jnettest.server.AppConfiguration;

/**
 * Unfortunately, there are currently no Spring annotations for exporting RMI objects.
 * @see https://jira.spring.io/browse/SPR-3926
 */
@Component
public class RmiExporter {
    
    public static final String SERVICE_NAME = "CommandService";
    
    private final RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();

    @Autowired
    private Server server;
    
    @Autowired
    private AppConfiguration appConfiguration;
    
    public RmiExporter() {
    }
    
    @PostConstruct
    public void export() throws RemoteException {
        rmiServiceExporter.setServiceInterface(CommandService.class);
        rmiServiceExporter.setService(server);
        rmiServiceExporter.setServiceName(SERVICE_NAME);
        rmiServiceExporter.setServicePort(appConfiguration.getLocalRmiPort());
//        rmiServiceExporter.setRegistryHost(appConfiguration.getLocalInterfaceName());
        rmiServiceExporter.afterPropertiesSet();
    }
    
    @PreDestroy
    public void shutdown() throws RemoteException {
        rmiServiceExporter.destroy();
    }
    
}

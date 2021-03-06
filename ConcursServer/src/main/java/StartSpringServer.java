import concurs.repo.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class StartSpringServer {
    public static void main(String[] args) throws RemoteException {
        LocateRegistry.createRegistry(1099);
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
        HibernateUtils hibernateUtils = (HibernateUtils)factory.getBean("hibernateUtils");
        hibernateUtils.initialize();
        System.out.println("Waiting...");
    }
}

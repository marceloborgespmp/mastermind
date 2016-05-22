package mastermind.controller;

import javax.persistence.*;
import javax.servlet.*;
 
public class DatabaseListener implements ServletContextListener {
 
    // Prepare the EntityManagerFactory & Enhance:
    public void contextInitialized(ServletContextEvent e) {
        com.objectdb.Enhancer.enhance("mastermind.model.*");
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("$objectdb/db/mastermind.odb");
        e.getServletContext().setAttribute("emf", emf);
    }
 
    // Release the EntityManagerFactory:
    public void contextDestroyed(ServletContextEvent e) {
        EntityManagerFactory emf =
            (EntityManagerFactory)e.getServletContext().getAttribute("emf");
        emf.close();
    }
}
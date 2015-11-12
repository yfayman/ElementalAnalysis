/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author yan
 */
public class InitSessionFactory {
   /** The single instance of hibernate SessionFactory */
   private static SessionFactory sessionFactory;
   private InitSessionFactory() {
   }
   static {
      final Configuration cfg = new Configuration();
      cfg.configure("/hibernate.cfg.xml");
      sessionFactory = cfg.buildSessionFactory();
   }
   public static SessionFactory getInstance() {
      return sessionFactory;
   }
}
package magic.draft.generator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/16/12
 * Time: 11:43 AM
 */
public final class EMFService
{
   private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("transactions-optional");
   
   private EMFService() {

   }

   public static EntityManagerFactory get(){
      return emf;
   }
}

package magic.draft.generator.models;

import magic.draft.generator.EMFService;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/16/12
 * Time: 10:20 AM
 */
@Entity
public class PlayerInfo
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private int seed;
   private int rank;
   private int roundWins;
   private int roundByes;
   private int roundLosses;
   private int individualWins;
   private int individualLosses;

   public PlayerInfo(String name, int seed)
   {
      this.name = name;
      this.seed = seed;
      rank = 0;
      roundWins = 0;
      roundLosses = 0;
      roundByes = 0;
      individualWins = 0;
      individualLosses = 0;
   }

   public PlayerInfo() {}

   public long getId()
   {
      return id;
   }

   public String getName()
   {
      return name;
   }

   public int getSeed()
   {
      return seed;
   }

   public int getRank()
   {
      return rank;
   }

   public int getRoundWins()
   {
      return roundWins;
   }

   public int getRoundByes()
   {
      return roundByes;
   }

   public int getRoundLosses()
   {
      return roundLosses;
   }

   public int getIndividualWins()
   {
      return individualWins;
   }

   public int getIndividualLosses()
   {
      return individualLosses;
   }

   public static void savePlayers()
   {
      int seed = 1;
      for (int i = 1; i <= 8; i++)
      {
         EntityManager em = EMFService.get().createEntityManager();
         try
         {
            seed *= 2;
            PlayerInfo player = new PlayerInfo("player" + i, seed);
            em.persist(player);
         }
         finally
         {
            em.close();
         }
      }
   }

   public static List findAllPlayers()
   {
      EntityManager em = EMFService.get().createEntityManager();
      List allPlayers = null;
      try
      {
         allPlayers = em.createQuery("select from " + PlayerInfo.class.getName()).getResultList();
         allPlayers.size();
      }
      finally
      {
         em.close();
      }
      return allPlayers;
   }
}

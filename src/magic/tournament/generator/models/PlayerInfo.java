package magic.tournament.generator.models;

import magic.tournament.generator.EMFService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

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
public class PlayerInfo {
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
   private String opponent;
   private ArrayList<String> possibleOpponents = new ArrayList<String>();
   private SortedMap<Integer, String> roundPairings = new TreeMap<Integer, String>();

   public PlayerInfo(String name, int seed, ArrayList<String> playerNames) {
      this.name = name;
      this.seed = seed;
      rank = 0;
      roundWins = 0;
      roundLosses = 0;
      roundByes = 0;
      individualWins = 0;
      individualLosses = 0;
      addPossibleOpponents(playerNames);
   }

   public PlayerInfo() {}

   public long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public int getSeed() {
      return seed;
   }

   public int getRank() {
      return rank;
   }

   public void setRank(int rank) {
      this.rank = rank;
   }

   public int getRoundWins() {
      return roundWins;
   }

   public int getRoundByes() {
      return roundByes;
   }

   public int getRoundLosses() {
      return roundLosses;
   }

   public int getIndividualWins() {
      return individualWins;
   }

   public int getIndividualLosses() {
      return individualLosses;
   }

   public String getOpponent() {
      return opponent;
   }

   public ArrayList<String> getPossibleOpponents() {
      return possibleOpponents;
   }

   public SortedMap<Integer, String> getRoundPairings() {
      return roundPairings;
   }

   public void wonRound() {
      this.roundWins += 1;
   }

   public void lostRound() {
      this.roundLosses += 1;
   }

   public void byeRound() {
      this.roundByes += 1;
   }

   public void addIndividualWins(int wins) {
      this.individualWins += wins;
   }

   public void addIndividualLosses(int losses) {
      this.individualLosses += losses;
   }

   public void addPossibleOpponents(ArrayList<String> playerNames) {
      possibleOpponents.add("Bye");
      for (String player : playerNames) {
         if (!player.equals(name)) {
            possibleOpponents.add(player);
         }
      }
   }

   public void removePossibleOpponent(String opponent) {
      //if the removed opponent is on the list of possible opponents, remove it
      int index = possibleOpponents.indexOf(opponent);
      if (index != -1) {
         possibleOpponents.remove(index);
      }
   }

   public boolean canPlayThisPlayer(String playerName) {
      boolean canPlay = false;
      for (String opponent : possibleOpponents) {
         if (playerName.equals(opponent)) {
            canPlay = true;
         }
      }
      return canPlay;
   }

   public void addRoundPairing(int round, String opponent) {
      roundPairings.put(round, opponent);
      this.opponent = opponent;
   }

   public void setRoundOutcome(int round, int wins, int losses) {
      String info = roundPairings.get(round);
      String outcome = wins > losses ? "Win" : "Loss";
      info = info + " / " + outcome + " / " + wins + "-" + losses;
      roundPairings.remove(round);
      roundPairings.put(round, info);
   }

   public boolean canUseBye() {
      return possibleOpponents.contains("Bye");
   }

   public boolean canOnlyGetBye() {
      return (canUseBye() && (possibleOpponents.size() == 1));
   }

   public void savePlayerInfo() {
      EntityManager em = EMFService.get().createEntityManager();
      try {
         em.persist(this);
      }
      finally {
         em.close();
      }
   }

   public static List findAllPlayers() {
      EntityManager em = EMFService.get().createEntityManager();
      List allPlayers = null;
      try {
         allPlayers = em.createQuery("select from " + PlayerInfo.class.getName()).getResultList();
         allPlayers.size();
      }
      finally {
         em.close();
      }
      return allPlayers;
   }
}

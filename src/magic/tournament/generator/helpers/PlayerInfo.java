package magic.tournament.generator.helpers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/2/12
 * Time: 11:11 AM
 */
public class PlayerInfo implements Serializable {
   private static final long serialVersionUID = 7526472295622776147L;

   private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
      aInputStream.defaultReadObject();
   }

   private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
      aOutputStream.defaultWriteObject();
   }

   private String name;
   private int seed;
   private int rank = 0;
   private int points = 0;
   private int roundWins = 0;
   private int roundLosses = 0;
   private int roundByes = 0;
   private int individualWins = 0;
   private int individualLosses = 0;

   private String opponent;
   private ArrayList<String> possibleOpponents = new ArrayList<String>();
   private SortedMap<Integer, String> roundPairings = new TreeMap<Integer, String>();

   public PlayerInfo(String name, int seed, ArrayList<String> playerNames)
   {
      this.name = name;
      this.seed = seed;
      addPossibleOpponents(playerNames);
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

   public int getPoints()
   {
      return points;
   }

   public int getRoundWins()
   {
      return roundWins;
   }

   public int getRoundLosses()
   {
      return roundLosses;
   }

   public int getRoundByes()
   {
      return roundByes;
   }

   public int getIndividualWins()
   {
      return individualWins;
   }

   public int getIndividualLosses()
   {
      return individualLosses;
   }

   public String getOpponent()
   {
      return opponent;
   }

   public ArrayList<String> getPossibleOpponents()
   {
      return possibleOpponents;
   }

   public SortedMap<Integer, String> getRoundPairings()
   {
      return roundPairings;
   }

   public void wonRound()
   {
      this.roundWins += 1;
      this.points += 3;
   }

   public void lostRound()
   {
      this.roundLosses += 1;
   }

   public void byeRound()
   {
      this.roundByes += 1;
   }

   public void addIndividualWins(int wins)
   {
      this.individualWins += wins;
   }

   public void addIndividualLosses(int losses)
   {
      this.individualLosses += losses;
   }

   public void addPossibleOpponents(ArrayList<String> playerNames)
   {
      possibleOpponents.add("Bye");
      for(String player : playerNames) {
         if (!player.equals(name)) {
            possibleOpponents.add(player);
         }
      }
   }

   public void removePossibleOpponent(String opponent)
   {
      //if the removed opponent is on the list of possible opponents, remove it
      int index = possibleOpponents.indexOf(opponent);
      if (index != -1)
      {
         possibleOpponents.remove(index);
      }
   }

   public boolean canPlayThisPlayer(String playerName)
   {
      boolean canPlay = false;
      for(String opponent : possibleOpponents){
         if (playerName.equals(opponent)) {
            canPlay = true;
         }
      }
      return canPlay;
   }

   public boolean canUseBye()
   {
      return possibleOpponents.contains("Bye");
   }

   public boolean canOnlyGetBye()
   {
      return (canUseBye() && (possibleOpponents.size() == 1));
   }

   public void addRoundPairing(int round, String opponent)
   {
      roundPairings.put(round, opponent);
      this.opponent = opponent;
   }

   public void setRoundOutcome(int round, int wins, int losses)
   {
      String info = roundPairings.get(round);
      String outcome = wins > losses ? "Win" : "Loss";
      info = info + " / " + outcome + " / " + wins + "-" + losses;
      roundPairings.remove(round);
      roundPairings.put(round, info);
   }

   public void setRank(int rank)
   {
      this.rank = rank;
   }
}

package magic.tournament.generator.helpers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class PlayerInfo implements Serializable {
   private static final long serialVersionUID = 7526472295622776147L;

   /**
    * method to de-serialize this object for app engine
    * @param aInputStream ObjectInputStream
    * @throws ClassNotFoundException exception
    * @throws IOException exception
    */
   private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
      aInputStream.defaultReadObject();
   }

   /**
    * method to serialize this object for app engine
    * @param aOutputStream ObjectOutputStream
    * @throws IOException exception
    */
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

   /**
    * constructor for PlayerInfo object
    * @param name name of the player
    * @param seed seed number
    * @param playerNames list of all other players in the tournament
    */
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

   /**
    * add all possible opponents to a list which each player keeps which includes "Bye"
    * @param playerNames a list of player names
    */
   public void addPossibleOpponents(ArrayList<String> playerNames) {
      possibleOpponents.add("Bye");
      for(String player : playerNames) {
         if (!player.equals(name)) {
            possibleOpponents.add(player);
         }
      }
   }

   /**
    * remove a player from the list of possible opponents
    * @param opponent the name of player to remove
    */
   public void removePossibleOpponent(String opponent) {
      //if the removed opponent is on the list of possible opponents, remove it
      int index = possibleOpponents.indexOf(opponent);
      if (index != -1)
      {
         possibleOpponents.remove(index);
      }
   }

   /**
    * check if the player exists in the possible opponents list
    * @param playerName list of the player to check
    * @return true if the player is on the list
    */
   public boolean canPlayThisPlayer(String playerName) {
      boolean canPlay = false;
      for(String opponent : possibleOpponents){
         if (playerName.equals(opponent)) {
            canPlay = true;
            break;
         }
      }
      return canPlay;
   }

   /**
    * check if "Bye" is still in the list of possible opponents
    * @return true if "Bye" is playable
    */
   public boolean canUseBye()
   {
      return possibleOpponents.contains("Bye");
   }

   /**
    * check if the player can only get a Bye round
    * @return true if "Bye" is the only thing in possible opponents
    */
   public boolean canOnlyGetBye()
   {
      return (canUseBye() && (possibleOpponents.size() == 1));
   }

   /**
    * add round pairing to the list and set the opponent's name
    * @param round number
    * @param opponent name
    */
   public void addRoundPairing(int round, String opponent)
   {
      roundPairings.put(round, opponent);
      this.opponent = opponent;
   }

   /**
    * set the outcome by getting he pairings and creating a string with the outcome and putting it back in the round pairing list
    * @param round
    * @param wins
    * @param losses
    */
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

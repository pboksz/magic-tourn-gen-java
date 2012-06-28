package magic.tournament.generator.helpers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class Tournament implements Serializable {
   private static final long serialVersionUID = 7526472295622776147L;

   /**
    * method to de-serialize this object for app engine
    *
    * @param aInputStream ObjectInputStream
    * @throws ClassNotFoundException exception
    * @throws IOException            exception
    */
   private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
      aInputStream.defaultReadObject();
   }

   /**
    * method to serialize this object for app engine
    *
    * @param aOutputStream ObjectOutputStream
    * @throws IOException exception
    */
   private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
      aOutputStream.defaultWriteObject();
   }

   private int prevRound = 0;
   private int round = 1;
   private int numPlayers;
   private int maxRound;
   private int bestOf;
   private int maxWins;

   private int maxDroppable = 1;
   private Random seed = new Random();
   private SortedMap<String, PlayerInfo> mapOfPlayers = new TreeMap<String, PlayerInfo>();

   /**
    * constructor for Tournament object
    *
    * @param numPlayers number of players
    * @param maxRound   number of rounds
    * @param bestOf     each round is best of 3 or 5, etc
    */
   public Tournament(int numPlayers, int maxRound, int bestOf) {
      this.numPlayers = numPlayers;
      this.maxRound = maxRound;
      this.bestOf = bestOf;
      this.maxWins = (int) Math.ceil(bestOf / 2.0);
   }

   public int getPrevRound() {
      return prevRound;
   }

   public int getRound() {
      return round;
   }

   public int getNumPlayers() {
      numPlayers = mapOfPlayers.size();
      return numPlayers;
   }

   public int getMaxRound() {
      return maxRound;
   }

   public int getBestOf() {
      return bestOf;
   }

   public int getMaxWins() {
      return maxWins;
   }

   public int getMaxDroppable() {
      return maxDroppable;
   }

   public void nextRound() {
      this.round++;
   }

   public void incPrevRound() {
      this.prevRound++;
   }

   public boolean isNextRound() {
      return round > prevRound;
   }

   public SortedMap<String, PlayerInfo> getMapOfPlayers() {
      return mapOfPlayers;
   }

   /**
    * clone the map of players into a another sorted map
    *
    * @return a cloned sorted map
    */
   public SortedMap<String, PlayerInfo> cloneMapOfPlayers() {
      SortedMap<String, PlayerInfo> cloneMap = new TreeMap<String, PlayerInfo>();
      for (Map.Entry<String, PlayerInfo> infoEntry : mapOfPlayers.entrySet()) {
         cloneMap.put(infoEntry.getKey(), infoEntry.getValue());
      }
      return cloneMap;
   }

   /**
    * return the player map as a list of PlayerInfo objects
    *
    * @return a list of PlayerInfo objects
    */
   public ArrayList<PlayerInfo> getListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = new ArrayList<PlayerInfo>();
      for (Map.Entry<String, PlayerInfo> infoEntry : mapOfPlayers.entrySet()) {
         listOfPlayers.add(infoEntry.getValue());
      }
      return listOfPlayers;
   }

   /**
    * register a list of players by creating new PlayerInfo objects for each
    *
    * @param playerNames a list of player names
    */
   public void registerPlayers(ArrayList<String> playerNames) {
      for (String name : playerNames) {
         mapOfPlayers.put(name, new PlayerInfo(name, seed.nextInt(100), playerNames));
      }
      maxDroppable = getNumPlayers() - getMaxRound();
   }

   private class SeedComparator implements Comparator<PlayerInfo> {
      public int compare(PlayerInfo player1, PlayerInfo player2) {
         return (player1.getSeed() - player2.getSeed());
      }
   }

   /**
    * return a seed sorted list of players
    *
    * @return arraylist, seed sorted
    */
   public ArrayList<PlayerInfo> getSeedSortedListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = getListOfPlayers();
      Collections.sort(listOfPlayers, new SeedComparator());
      return listOfPlayers;
   }

   public class RankComparator implements Comparator<PlayerInfo> {
      public int compare(PlayerInfo player1, PlayerInfo player2) {
         if (player1.getRoundWins() == player2.getRoundWins()) {
            if (player1.getRoundByes() == player2.getRoundByes()) {
               if (player1.getIndividualWins() == player2.getIndividualWins()) {
                  return (player1.getIndividualLosses() - player2.getIndividualLosses());
               } else {
                  return (player2.getIndividualWins() - player1.getIndividualWins());
               }
            } else {
               return (player2.getRoundByes() - player1.getRoundByes());
            }
         } else {
            return (player2.getRoundWins() - player1.getRoundWins());
         }
      }
   }

   /**
    * return a rank sorted list of players
    *
    * @return arraylist, rank sorted
    */
   public ArrayList<PlayerInfo> getRankSortedListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = getListOfPlayers();
      Collections.sort(listOfPlayers, new RankComparator());
      return listOfPlayers;
   }

   /**
    * sets each players final ranking when called on
    *
    * @return a list of players with final ranks in place
    */
   public ArrayList<PlayerInfo> getCurrentRankings() {
      ArrayList<PlayerInfo> listOfPlayers = getRankSortedListOfPlayers();
      int rank = 1;
      for (PlayerInfo player : listOfPlayers) {
         player.setRank(rank++);
      }
      return listOfPlayers;
   }

   /**
    * checks if Tournament has room to drop, if its not the first round or already showing the final results
    *
    * @return true if player can be dropped
    */
   public boolean canDropPlayer() {
      return ((maxDroppable > 0) && (round > 1) && (round <= maxRound));
   }

   /**
    * drops a player from the map of players and removes the player from each of the possible opponents lists
    *
    * @param dropped name of the player to be dropped
    */
   public void dropPlayer(String dropped) {
      mapOfPlayers.remove(dropped);
      maxDroppable--;

      ArrayList<PlayerInfo> listOfPlayers = getListOfPlayers();

      //for each player remove dropped player from the list of possible opponents
      for (PlayerInfo player : listOfPlayers) {
         player.removePossibleOpponent(dropped);
         save(player);
      }
   }

   /**
    * sets the outcome of the round for each player
    *
    * @param playerName   the player's playerName
    * @param opponentName the opponentName's playerName
    * @param wins         the player's wins
    * @param losses       the player's losses
    */
   public void setPlayerOutcome(String playerName, String opponentName, int wins, int losses) {
      PlayerInfo player = mapOfPlayers.get(playerName);

      //if the player exists
      if (player != null) {
         //if the opponent is not a Bye
         if (!opponentName.equals("Bye")) {
            //if the player won, set won round
            if (wins > losses) {
               player.wonRound();
            }
            //else set lost round
            else {
               player.lostRound();
            }
            //set individual wins and losses
            player.addIndividualWins(wins);
            player.addIndividualLosses(losses);
            player.setRoundOutcome(round, wins, losses);
         }
         //else set a bye round
         else {
            player.byeRound();
         }
         save(player);
      }
   }

   /**
    * Sets both players info object to record the round and opponentName for the round
    *
    * @param playerName   name of player
    * @param opponentName name of opponent
    */
   public void setPlayerPairing(String playerName, String opponentName) {
      //set the round pairing of the player and remove opponent name from possible opponents
      if (!playerName.equals("Bye")) {
         PlayerInfo player = mapOfPlayers.get(playerName);
         player.addRoundPairing(round, opponentName);
         player.removePossibleOpponent(opponentName);
         save(player);
      }

      //set the round pairing of the opponent and remove player name from possible opponents
      if (!opponentName.equals("Bye")) {
         PlayerInfo opponent = mapOfPlayers.get(opponentName);
         opponent.addRoundPairing(round, playerName);
         opponent.removePossibleOpponent(playerName);
         save(opponent);
      }
   }

   /**
    * saves the player to the map object by removing the old one, and adding the new one
    *
    * @param player the PlayerInfo object for that player
    */
   private void save(PlayerInfo player) {
      mapOfPlayers.remove(player.getName());
      mapOfPlayers.put(player.getName(), player);
   }
}

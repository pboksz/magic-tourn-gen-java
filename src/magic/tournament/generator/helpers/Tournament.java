package magic.tournament.generator.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/2/12
 * Time: 11:09 AM
 */
public class Tournament
{
   private static Tournament tournament;

   private int prevRound = 0;
   private int round = 1;
   private int numPlayers;
   private int maxRound;
   private int bestOf;
   private int maxWins;
   private String format;

   private int maxDroppable = 1;
   private Random seed = new Random();
   private SortedMap<String, PlayerInfo> mapOfPlayers = new TreeMap<String, PlayerInfo>();

   public Tournament(int numPlayers, int maxRound, int bestOf, String format) {
      this.numPlayers = numPlayers;
      this.maxRound = maxRound;
      this.bestOf = bestOf;
      this.maxWins = (int) Math.ceil(bestOf/2.0);
      this.format = format;
   }
   
   public static void newTournament(int numPlayers, int maxRound, int bestOf, String format){
      tournament = new Tournament(numPlayers, maxRound, bestOf, format);
   }

   public static Tournament getTournament() {
      return tournament;
   }

   public int getPrevRound() {
      return prevRound;
   }

   public int getRound() {
      return round;
   }

   public int getNumPlayers() {
      return mapOfPlayers.size();
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

   public String getFormat() {
      return format;
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

   public SortedMap<String, PlayerInfo> cloneMapOfPlayers() {
      SortedMap<String, PlayerInfo> cloneMap = new TreeMap<String, PlayerInfo>();
      for(Map.Entry<String, PlayerInfo> infoEntry : mapOfPlayers.entrySet()){
         cloneMap.put(infoEntry.getKey(), infoEntry.getValue());
      }
      return cloneMap;
   }

   public ArrayList<PlayerInfo> getListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = new ArrayList<PlayerInfo>();
      for(Map.Entry<String, PlayerInfo> infoEntry : mapOfPlayers.entrySet()){
         listOfPlayers.add(infoEntry.getValue());
      }
      return listOfPlayers;
   }

   public void registerPlayers(ArrayList<String> playerNames) {
      for(String name : playerNames){
         mapOfPlayers.put(name, new PlayerInfo(name, seed.nextInt(100), playerNames));
      }
      maxDroppable = getNumPlayers() - getMaxRound();
   }

   public ArrayList<PlayerInfo> getSeedSortedListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = getListOfPlayers();
      Collections.sort(listOfPlayers, new SeedComparator());
      return listOfPlayers;
   }

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
   public ArrayList<PlayerInfo> getCurrentRankings()
   {
      ArrayList<PlayerInfo> listOfPlayers = getRankSortedListOfPlayers();
      int rank = 1;
      for (PlayerInfo player : listOfPlayers)
      {
         player.setRank(rank++);
      }
      return listOfPlayers;
   }

   public boolean hasDroppable() {
      return (maxDroppable > 0);
   }

   public boolean dropPlayer(String dropped) {
      if((round > 1) && (maxDroppable > 0)) {
         mapOfPlayers.remove(dropped);
         maxDroppable--;

         //for each player remove dropped player and add "Bye" to list of possible opponents
         ArrayList<PlayerInfo> listOfPlayers = getListOfPlayers();
         for (PlayerInfo player : listOfPlayers)
         {
            player.removePossibleOpponent(dropped);
            save(player);
         }
         return true;
      }
      else {
         return false;
      }
   }

   /**
    * sets the outcome of the round for each player
    * @param playerName the player's playerName
    * @param opponentName the opponentName's playerName
    * @param wins the player's wins
    * @param losses the player's losses
    */
   public void setPlayerOutcome(String playerName, String opponentName, int wins, int losses) {
      PlayerInfo player = mapOfPlayers.get(playerName);
      if (player != null) {
         if (!opponentName.equals("Bye")) {
            if (wins > losses) {
               player.wonRound();
            }
            else {
               player.lostRound();
            }
            player.addIndividualWins(wins);
            player.addIndividualLosses(losses);
            player.setRoundOutcome(round, wins, losses);
         }
         else {
            player.byeRound();
         }
         save(player);
      }
   }

   /**
    * Sets both players info object to record the round and opponentName for the round
    * @param playerName name of player
    * @param opponentName name of opponent
    */
   public void setRoundPairing(String playerName, String opponentName) {
      if (!playerName.equals("Bye")) {
         PlayerInfo player = mapOfPlayers.get(playerName);
         player.addRoundPairing(round, opponentName);
         player.removePossibleOpponent(opponentName);
         save(player);
      }

      if (!opponentName.equals("Bye")) {
         PlayerInfo opponent = mapOfPlayers.get(opponentName);
         opponent.addRoundPairing(round, playerName);
         opponent.removePossibleOpponent(playerName);
         save(opponent);
      }
   }

   /**
    * saves the player to the map object by removing the old one, and adding the new one
    * @param player the PlayerInfo object for that player
    */
   private void save(PlayerInfo player) {
      mapOfPlayers.remove(player.getName());
      mapOfPlayers.put(player.getName(), player);
   }
}

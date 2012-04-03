package helpers;

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
 * Time: 11:19 AM
 */
public class PlayerPool
{
   private static SortedMap<String, PlayerInfo> mapOfPlayers = new TreeMap<String, PlayerInfo>();
   private static Random seed = new Random();
   private static int maxDroppable = 1;

   public static SortedMap<String, PlayerInfo> getMapOfPlayers() {
      return mapOfPlayers;
   }

   public static ArrayList<PlayerInfo> getListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = new ArrayList<PlayerInfo>();
      for(Map.Entry<String, PlayerInfo> infoEntry : mapOfPlayers.entrySet()){
         listOfPlayers.add(infoEntry.getValue());
      }
      return listOfPlayers;
   }
   
   public static ArrayList<PlayerInfo> getSeedSortedListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = getListOfPlayers();
      Collections.sort(listOfPlayers, new SeedComparator());
      return listOfPlayers;
   }

   public static ArrayList<PlayerInfo> getRankSortedListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = getListOfPlayers();
      Collections.sort(listOfPlayers, new RankComparator());
      return listOfPlayers;
   }

   public static int getNumPlayers() {
      return mapOfPlayers.size();
   }

   public static void registerPlayers(ArrayList<String> playerNames) {
      for(String name : playerNames){
         mapOfPlayers.put(name, new PlayerInfo(name, seed.nextInt(100), playerNames));

      }
      maxDroppable = getNumPlayers()-3;
   }

   public static boolean dropPlayer(int round, String dropped, String getsBye) {
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

   public static void dropAllPlayers() {
      mapOfPlayers.clear();
   }

   /**
    * sets the outcome of the round for each player
    * @param playerName the player's playerName
    * @param opponentName the opponentName's playerName
    * @param round the round number
    * @param wins the player's wins
    * @param losses the player's losses
    */
   public static void setPlayerOutcome(String playerName, String opponentName, int round, int wins, int losses) {
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
    * @param round number
    * @param playerName name of player
    * @param opponentName name of opponent
    */
   public static void setRoundPairing(int round, String playerName, String opponentName) {
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
   private static void save(PlayerInfo player) {
      mapOfPlayers.remove(player.getName());
      mapOfPlayers.put(player.getName(), player);
   }
}

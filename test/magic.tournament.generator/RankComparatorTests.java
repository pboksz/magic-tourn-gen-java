package magic.tournament.generator;

import magic.tournament.generator.helpers.PlayerInfo;
import magic.tournament.generator.helpers.RankComparator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class RankComparatorTests
{
   private PlayerInfo player1;
   private PlayerInfo player2;

   private ArrayList<String> getAllPlayers() {
      ArrayList<String> playerNames = new ArrayList<String>();
      for(int i=1; i <=6; i++){
         playerNames.add("player" + i);
      }
      return playerNames;
   }

   private String sortPlayers() {
      ArrayList<PlayerInfo> playerList = new ArrayList<PlayerInfo>();
      playerList.add(player1);
      playerList.add(player2);

      Collections.sort(playerList, new RankComparator());

      return playerList.get(0).getName();
   }

   @Before
   public void initializePlayers() {
      player1 = new PlayerInfo("player1", 23, getAllPlayers());
      player2 = new PlayerInfo("player1", 34, getAllPlayers());
   }

   @Test
   public void testCompareByWins() {
      player1.wonRound();
      String firstPlace = sortPlayers();
      
      assertEquals("player1", firstPlace);
   }

   @Test
   public void testCompareByByes() {
      player2.byeRound();
      String firstPlace = sortPlayers();

      assertEquals("player1", firstPlace);
   }

   @Test
   public void testCompareByIndividualWins() {
      player1.addIndividualWins(2);
      player2.addIndividualWins(1);
      String firstPlace = sortPlayers();

      assertEquals("player1", firstPlace);
   }
   
   @Test
   public void testCompareByIndividualLosses() {
      player2.addIndividualLosses(1);
      String firstPlace = sortPlayers();

      assertEquals("player1", firstPlace);
   }
}

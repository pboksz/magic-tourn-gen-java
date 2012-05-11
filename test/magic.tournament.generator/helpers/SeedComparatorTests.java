package magic.tournament.generator.helpers;

import magic.tournament.generator.helpers.PlayerInfo;
import magic.tournament.generator.helpers.SeedComparator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/9/12
 * Time: 1:48 PM
 */
public class SeedComparatorTests
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

      Collections.sort(playerList, new SeedComparator());

      return playerList.get(0).getName();
   }

   @Before
   public void initializePlayers() {
      player1 = new PlayerInfo("player1", 23, getAllPlayers());
      player2 = new PlayerInfo("player1", 34, getAllPlayers());
   }
   
   @Test
   public void testSeedCompare() {
      String firstPlace = sortPlayers();

      assertEquals("player1", firstPlace);
   }
}

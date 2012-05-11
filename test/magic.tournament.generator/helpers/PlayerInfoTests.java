package magic.tournament.generator.helpers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.SortedMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerInfoTests {

   private PlayerInfo player;

   private ArrayList<String> getAllPlayers() {
      ArrayList<String> playerNames = new ArrayList<String>();
      for(int i=1; i <=6; i++){
         playerNames.add("player" + i);
      }
      return playerNames;
   }

   @Before
   public void initializePlayer() {
      player = new PlayerInfo("player1", 23, getAllPlayers());
   }

   @Test
   public void testWonRound() {
      player.wonRound();

      assertEquals(1, player.getRoundWins());
      assertEquals(3, player.getPoints());
   }
   
   @Test
   public void testLostRound() {
      player.lostRound();
      player.lostRound();

      assertEquals(2, player.getRoundLosses());
   }
   
   @Test
   public void testByeRound() {
      player.byeRound();
      
      assertEquals(1, player.getRoundByes());
   }

   @Test
   public void testAddIndividualWins() {
      player.addIndividualWins(2);

      assertEquals(2, player.getIndividualWins());
   }

   @Test
   public void testAddIndividualLosses() {
      player.addIndividualLosses(0);

      assertEquals(0, player.getIndividualLosses());
   }

   @Test
   public void testAddPossibleOpponents() {
      ArrayList<String> possibleOpponents = player.getPossibleOpponents();
      
      assertFalse(possibleOpponents.contains("player1"));
      assertTrue(possibleOpponents.contains("Bye"));
      assertEquals(6, possibleOpponents.size());
   }

   @Test
   public void testRemovePossibleOpponent() {
      player.removePossibleOpponent("player3");
      ArrayList<String> possibleOpponents = player.getPossibleOpponents();

      assertFalse(possibleOpponents.contains("player3"));
   }

   @Test
   public void testRemoveInvalidOpponent() {
      player.removePossibleOpponent("InvalidPlayer");
      ArrayList<String> possibleOpponents = player.getPossibleOpponents();

      assertEquals(6, possibleOpponents.size());
   }

   @Test
   public void testCannotPlaySelf() {
      assertFalse(player.canPlayThisPlayer("player1"));
   }
   
   @Test
   public void testCanPlayAllOtherPlayers() {
      assertTrue(player.canPlayThisPlayer("Bye"));
      assertTrue(player.canPlayThisPlayer("player2"));
      assertTrue(player.canPlayThisPlayer("player3"));
      assertTrue(player.canPlayThisPlayer("player4"));
      assertTrue(player.canPlayThisPlayer("player5"));
      assertTrue(player.canPlayThisPlayer("player6"));
   }

   @Test
   public void testCannotPlayRemovedPlayer() {
      player.removePossibleOpponent("player3");
      assertFalse(player.canPlayThisPlayer("player3"));
   }

   @Test
   public void testCanPlayPlayerInvalidData() {
      assertFalse(player.canPlayThisPlayer("InvalidPlayer"));
   }

   @Test
   public void testCanUseBye() {
      assertTrue(player.canUseBye());
   }

   @Test
   public void testCannotUseBye() {
      player.removePossibleOpponent("Bye");

      assertFalse(player.canUseBye());
   }

   @Test
   public void testCanOnlyGetBye() {
      player.removePossibleOpponent("player2");
      player.removePossibleOpponent("player3");
      player.removePossibleOpponent("player4");
      player.removePossibleOpponent("player5");
      player.removePossibleOpponent("player6");

      assertTrue(player.canOnlyGetBye());
   }

   @Test
   public void testCannotOnlyGetBye() {
      assertFalse(player.canOnlyGetBye());
   }

   @Test
   public void testAddingRoundPairing() {
      player.addRoundPairing(1, "player4");
      SortedMap<Integer, String> roundPairings = player.getRoundPairings();

      assertEquals(1, roundPairings.size());
      assertEquals("player4", roundPairings.get(1));
      assertEquals("player4", player.getOpponent());
   }
   
   @Test
   public void testSetRoundOutcome() {
      player.addRoundPairing(1, "player4");
      player.setRoundOutcome(1, 2, 1);
      SortedMap<Integer, String> roundPairings = player.getRoundPairings();

      assertEquals(1, roundPairings.size());
      assertEquals("player4 / Win / 2-1", roundPairings.get(1));
   }
}
package magic.tournament.generator.helpers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.SortedMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/9/12
 * Time: 1:53 PM
 */
public class TournamentTests
{
   private Tournament tournament;

   private ArrayList<String> getAllPlayers() {
      ArrayList<String> playerNames = new ArrayList<String>();
      for(int i=1; i <=4; i++){
         playerNames.add("player" + i);
      }
      return playerNames;
   }

   private void setFirstRound() {
      tournament.setPlayerOutcome("player1", "player2", 2, 0);
      tournament.setPlayerOutcome("player2", "player1", 0, 2);
      tournament.setPlayerOutcome("player3", "player4", 2, 1);
      tournament.setPlayerOutcome("player4", "player3", 1, 2);
      tournament.incPrevRound();
      tournament.nextRound();
   }

   @Before
   public void initializeTournament() {
      tournament = new Tournament(4, 3, 3);
      tournament.registerPlayers(getAllPlayers());
   }

   @Test
   public void testNextRound() {
      tournament.nextRound();

      assertEquals(2, tournament.getRound());
   }

   @Test
   public void testIncPrevRound() {
      tournament.incPrevRound();

      assertEquals(1, tournament.getPrevRound());
   }

   @Test
   public void testIsNextRound() {
      assertTrue(tournament.isNextRound());
   }

   @Test
   public void testIsNotNextRound() {
      tournament.incPrevRound();

      assertFalse(tournament.isNextRound());
   }

   @Test
   public void testCloneMapOfPlayers() {
      SortedMap<String,PlayerInfo> originalMap = tournament.getMapOfPlayers();
      SortedMap<String, PlayerInfo> clonedMap = tournament.cloneMapOfPlayers();

      assertNotSame(originalMap, clonedMap);
   }

   @Test
   public void testGetListOfPlayers() {
      ArrayList<PlayerInfo> listOfPlayers = tournament.getListOfPlayers();

      assertEquals("ArrayList", listOfPlayers.getClass().getSimpleName());
   }

   @Test
   public void testRegisterPlayers() {
      SortedMap<String, PlayerInfo> mapOfPlayers = tournament.getMapOfPlayers();

      assertEquals(4, mapOfPlayers.size());
      assertEquals(1, tournament.getMaxDroppable());
   }

   @Test
   public void testGetCurrentRankings() {

      setFirstRound();
      ArrayList<PlayerInfo> rankings = tournament.getCurrentRankings();
      String first = rankings.get(0).getName();
      String second = rankings.get(1).getName();
      String third = rankings.get(2).getName();
      String fourth = rankings.get(3).getName();

      assertEquals("player1", first);
      assertEquals("player3", second);
      assertEquals("player4", third);
      assertEquals("player2", fourth);
   }
   
   @Test
   public void testCanDropPlayerRoundOne() {
      assertFalse(tournament.canDropPlayer());
   }

   @Test
   public void testCanDropPlayerRoundTwo() {
      setFirstRound();
      assertTrue(tournament.canDropPlayer());
   }

   @Test
   public void testDroppingPlayer() {

      setFirstRound();
      tournament.dropPlayer("player4");

      assertFalse(tournament.canDropPlayer());
      assertEquals(3, tournament.getNumPlayers());
      assertFalse(tournament.getMapOfPlayers().containsKey("player4"));
   }

   @Test
   public void testSetPlayerOutcome() {
      tournament.setPlayerOutcome("player1", "player2", 2, 0);
      PlayerInfo player1 = tournament.getMapOfPlayers().get("player1");

      assertEquals(1, player1.getRoundWins());
      assertEquals(0, player1.getRoundLosses());
      assertEquals(0, player1.getRoundByes());
      assertEquals(2, player1.getIndividualWins());
      assertEquals(0, player1.getIndividualLosses());
   }

   @Test
   public void testSetPlayerOutcomeAgainstBye() {
      tournament.setPlayerOutcome("player1", "Bye", -1, -1);
      PlayerInfo player1 = tournament.getMapOfPlayers().get("player1");

      assertEquals(1, player1.getRoundByes());
   }

   @Test
   public void testSetRoundPairing() {
      tournament.setPlayerPairing("player1", "player2");
      PlayerInfo player1 = tournament.getMapOfPlayers().get("player1");
      PlayerInfo player2 = tournament.getMapOfPlayers().get("player2");

      assertFalse(player1.getPossibleOpponents().contains("player2"));
      assertEquals("player2", player1.getRoundPairings().get(1));

      assertFalse(player2.getPossibleOpponents().contains("player1"));
      assertEquals("player1", player2.getRoundPairings().get(1));
   }
   
   @Test
   public void testSetRoundPairingAgainstBye() {
      tournament.setPlayerPairing("player1", "Bye");
      PlayerInfo player1 = tournament.getMapOfPlayers().get("player1");

      assertFalse(player1.getPossibleOpponents().contains("Bye"));
      assertEquals("Bye", player1.getRoundPairings().get(1));
      assertFalse(tournament.getMapOfPlayers().containsKey("Bye"));
   }

   @Test
   public void testSettingByePlayersOutcome() {
      tournament.setPlayerOutcome("Bye", "player1", 2, 1);

      assertFalse(tournament.getMapOfPlayers().containsKey("Bye"));
   }

   @Test
   public void testRoundPairingBye() {
      tournament.setPlayerPairing("Bye", "player1");
      PlayerInfo player = tournament.getMapOfPlayers().get("player1");

      assertTrue(player.getRoundPairings().containsValue("Bye"));
   }
}

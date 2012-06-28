package magic.tournament.generator.helpers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/10/12
 * Time: 11:41 AM
 */
public class RoundPairingsTests
{
   private RoundPairings pairings;
   private Tournament tournament;

   private ArrayList<String> getAllPlayers(int howMany) {
      ArrayList<String> playerNames = new ArrayList<String>();
      for(int i = 1; i <= howMany; i++){
         playerNames.add("player" + i);
      }
      return playerNames;
   }

   private void setFirstRoundWithFour() {
      tournament.setPlayerOutcome("player1", "player2", 2, 0);
      tournament.setPlayerOutcome("player2", "player1", 0, 2);
      tournament.setPlayerOutcome("player3", "player4", 2, 1);
      tournament.setPlayerOutcome("player4", "player3", 1, 2);
      tournament.incPrevRound();
      tournament.nextRound();
   }

   private void setFirstRoundWithFive() {
      tournament.setPlayerOutcome("player1", "player2", 2, 0);
      tournament.setPlayerOutcome("player2", "player1", 0, 2);
      tournament.setPlayerOutcome("player3", "player4", 2, 1);
      tournament.setPlayerOutcome("player4", "player3", 1, 2);
      tournament.setPlayerOutcome("player5", "Bye", -1, -1);
      tournament.incPrevRound();
      tournament.nextRound();
   }

   @Before
   public void initializeTournament() {
      tournament = new Tournament(4, 3, 3);
      tournament.registerPlayers(getAllPlayers(4));
      pairings = new RoundPairings(tournament);
   }

   @Test
   public void testInitialSeeding() {
      pairings.createRoundPairings();
      ArrayList<PlayerInfo> listOfPlayers = tournament.getSeedSortedListOfPlayers();

      for(PlayerInfo player : listOfPlayers) {
         assertFalse(player.getRoundPairings().get(1).isEmpty());
      }
      
      int first = listOfPlayers.get(0).getSeed();
      int second = listOfPlayers.get(1).getSeed();
      int third = listOfPlayers.get(2).getSeed();
      int fourth = listOfPlayers.get(3).getSeed();
      
      assertTrue(first <= second);
      assertTrue(second <= third);
      assertTrue(third <= fourth);
   }

   @Test
   public void testRankPairingRoundTwo() {
      pairings.createRoundPairings();
      setFirstRoundWithFour();
      pairings.createRoundPairings();
      ArrayList<PlayerInfo> listOfPlayers = tournament.getCurrentRankings();

      for(PlayerInfo player : listOfPlayers) {
         assertFalse(player.getRoundPairings().get(1).isEmpty());
      }

      int first = listOfPlayers.get(0).getRank();
      int second = listOfPlayers.get(1).getRank();
      int third = listOfPlayers.get(2).getRank();
      int fourth = listOfPlayers.get(3).getRank();

      assertEquals(1, first);
      assertEquals(2, second);
      assertEquals(3, third);
      assertEquals(4, fourth);
   }

   @Test
   public void testInitialSeedingWithBye() {
      tournament = new Tournament(5, 3, 3);
      tournament.registerPlayers(getAllPlayers(5));

      pairings = new RoundPairings(tournament);
      pairings.createRoundPairings();
      ArrayList<PlayerInfo> listOfPlayers = tournament.getSeedSortedListOfPlayers();

      for(PlayerInfo player : listOfPlayers) {
         assertFalse(player.getRoundPairings().get(1).isEmpty());
      }

      int first = listOfPlayers.get(0).getSeed();
      int second = listOfPlayers.get(1).getSeed();
      int third = listOfPlayers.get(2).getSeed();
      int fourth = listOfPlayers.get(3).getSeed();
      int fifth = listOfPlayers.get(4).getSeed();

      assertTrue(first <= second);
      assertTrue(second <= third);
      assertTrue(third <= fourth);
      assertTrue(fourth <= fifth);
      assertEquals("Bye", listOfPlayers.get(4).getOpponent());
   }

   @Test
   public void testByePairingRoundTwo() {
      tournament = new Tournament(5, 3, 3);
      tournament.registerPlayers(getAllPlayers(5));

      //round one pairings
      pairings = new RoundPairings(tournament);
      pairings.createRoundPairings();
      setFirstRoundWithFive();

      //round two pairings
      pairings = new RoundPairings(tournament);
      pairings.createRoundPairings();
      ArrayList<PlayerInfo> listOfPlayers = tournament.getCurrentRankings();

      for(PlayerInfo player : listOfPlayers) {
         assertFalse(player.getRoundPairings().get(1).isEmpty());
      }

      int first = listOfPlayers.get(0).getRank();
      int second = listOfPlayers.get(1).getRank();
      int third = listOfPlayers.get(2).getRank();
      int fourth = listOfPlayers.get(3).getRank();
      int fifth = listOfPlayers.get(4).getRank();

      assertEquals(1, first);
      assertEquals(2, second);
      assertEquals(3, third);
      assertEquals(4, fourth);
      assertEquals(5, fifth);
   }
   
   @Test
   public void testThreePlayerPairing() {
      tournament = new Tournament(4, 3, 3);
      tournament.registerPlayers(getAllPlayers(4));

      //round one pairings
      pairings = new RoundPairings(tournament);
      pairings.createRoundPairings();
      setFirstRoundWithFour();

      //drop a player
      tournament.dropPlayer("player4");

      //round two pairings
      pairings = new RoundPairings(tournament);
      pairings.createRoundPairings();

      //set outcome for round two
      tournament.setPlayerOutcome("player1", "player3", 2, 0);
      tournament.setPlayerOutcome("player3", "player1", 0, 2);
      tournament.setPlayerOutcome("player2", "Bye", -1, -1);
      tournament.nextRound();
      tournament.incPrevRound();

      //round three pairings
      pairings = new RoundPairings(tournament);
      pairings.createRoundPairings();
   }

   //TODO need some more corner cases
}

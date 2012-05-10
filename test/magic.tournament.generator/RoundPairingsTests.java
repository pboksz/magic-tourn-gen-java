package magic.tournament.generator;

import magic.tournament.generator.helpers.PlayerInfo;
import magic.tournament.generator.helpers.RoundPairings;
import magic.tournament.generator.helpers.Tournament;
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
      Tournament.newTournament(4, 3, 3, "Swiss");
      tournament = Tournament.getTournament();
      tournament.registerPlayers(getAllPlayers(4));
      pairings = new RoundPairings();
   }

   @Test
   public void testInitialSeeding() {
      pairings.setRoundPairings();
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
      pairings.setRoundPairings();
      setFirstRoundWithFour();
      pairings.setRoundPairings();
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
      Tournament.newTournament(5, 3, 3, "Swiss");
      tournament = Tournament.getTournament();
      tournament.registerPlayers(getAllPlayers(5));
      pairings = new RoundPairings();
      pairings.setRoundPairings();
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
      Tournament.newTournament(5, 3, 3, "Swiss");
      tournament = Tournament.getTournament();
      tournament.registerPlayers(getAllPlayers(5));
      pairings = new RoundPairings();
      pairings.setRoundPairings();
      setFirstRoundWithFive();
      pairings.setRoundPairings();
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
}

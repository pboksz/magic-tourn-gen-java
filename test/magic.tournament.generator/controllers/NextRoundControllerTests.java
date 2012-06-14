package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/14/12
 * Time: 11:19 AM
 */
public class NextRoundControllerTests
{
   private HttpServletRequest request;
   private NextRoundController nextRound;
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
   public void initializeRequest() {
      request = createStrictMock(HttpServletRequest.class);
      nextRound = new NextRoundController();

      tournament = new Tournament(4, 3, 3, "Swiss");
      tournament.registerPlayers(getAllPlayers());
   }

   @Test
   public void testNextRoundControllerValidValues() {
      String[] players = {"player1", "player3"};
      expect(request.getParameterValues("player")).andReturn(players);
      String[] opponents = {"player2", "player4"};
      expect(request.getParameterValues("opponent")).andReturn(opponents);
      String[] wins = {"2", "2"};
      expect(request.getParameterValues("wins")).andReturn(wins);
      String[] losses = {"1", "1"};
      expect(request.getParameterValues("losses")).andReturn(losses);

      request.setAttribute("title", "Round 1 Standings");
      request.setAttribute("droppable", true);

      request.setAttribute("results", tournament.getCurrentRankings());

      expect(request.getRequestDispatcher("/pages/show.jsp")).andReturn(createMock(RequestDispatcher.class));
   }
}

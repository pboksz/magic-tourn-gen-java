package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;
import org.junit.Before;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.createStrictMock;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/11/12
 * Time: 4:02 PM
 */
public class DropControllerTests
{
   private HttpServletRequest request;
   private DropController drop;
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
      drop = new DropController();
      Tournament.newTournament(4, 3, 3, "Swiss");
      tournament = Tournament.getTournament();
      tournament.registerPlayers(getAllPlayers());
   }

   //TODO fix this
//   @Test
//   public void testDropControllerOnFirstRound() throws Exception {
//      expect(request.getParameter("dropped")).andReturn("player1");
//
//      request.setAttribute("error ", "player1 cannot be dropped because there would not be enough players left to adequately pair everyone in the remaining rounds");
//
//      request.setAttribute("title", "Round 1 Standings");
//      request.setAttribute("droppable", tournament.hasDroppable());
//      request.setAttribute("results", tournament.getCurrentRankings());
//
//      expect(request.getRequestDispatcher("/pages/results.jsp")).andReturn(createMock(RequestDispatcher.class));
//
//      replay(request);
//      drop.doGet(request, null);
//      verify(request);
//   }
}

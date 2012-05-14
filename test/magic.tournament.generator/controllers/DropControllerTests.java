package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;

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

   @Before
   public void initializeRequest() {
      request = createStrictMock(HttpServletRequest.class);
      drop = new DropController();

      Tournament.newTournament(4, 3, 3, "Swiss");
      tournament = Tournament.getTournament();
      tournament.registerPlayers(getAllPlayers());
   }

   @Test
   public void testDropControllerError() throws Exception {
      expect(request.getParameter("dropped")).andReturn("player1");

      //This is the error that should be present
      request.setAttribute("error", "player1 cannot be dropped because there would not be enough players left to adequately pair everyone in the remaining rounds");

      request.setAttribute("title", "Round 0 Standings");
      request.setAttribute("droppable", true);
      request.setAttribute("results", tournament.getCurrentRankings());

      expect(request.getRequestDispatcher("/pages/results.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(request);
      drop.doGet(request, null);
      verify(request);
   }
}

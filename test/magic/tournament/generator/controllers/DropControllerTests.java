package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/11/12
 * Time: 4:02 PM
 */
public class DropControllerTests
{
   private HttpServletRequest request;
   private HttpSession session;
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
      session = createStrictMock(HttpSession.class);
      drop = new DropController();

      tournament = new Tournament(4, 3, 3);
      tournament.registerPlayers(getAllPlayers());
      setFirstRound();
   }

   @Test
   public void testDropPlayer() throws Exception {
      expect(request.getSession()).andReturn(session);
      expect(session.getAttribute("tournament")).andReturn(tournament);
      expect(request.getParameter("dropped")).andReturn("player1");

      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("droppable"), isA(Boolean.class));
      request.setAttribute(eq("results"), isA(ArrayList.class));

      expect(request.getSession()).andReturn(session);
      session.setAttribute(eq("tournament"), isA(Tournament.class));

      expect(request.getRequestDispatcher("/pages/results.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(session);
      replay(request);
      drop.doPost(request, null);
      verify(session);
      verify(request);

      assertEquals(3, tournament.getNumPlayers());
      assertTrue(tournament.getMapOfPlayers().containsKey("player2"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player3"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player4"));
   }
}

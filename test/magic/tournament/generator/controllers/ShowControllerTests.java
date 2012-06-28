package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.PlayerInfo;
import magic.tournament.generator.helpers.Tournament;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 6/26/12
 * Time: 2:26 PM
 */
public class ShowControllerTests {
   private HttpServletRequest request;
   private HttpSession session;
   private ShowController show;
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
      session = createStrictMock(HttpSession.class);
      show = new ShowController();

      tournament = new Tournament(4, 3, 3);
      tournament.registerPlayers(getAllPlayers());
   }

   @Test
   public void testShowController() throws Exception {
      expect(request.getSession()).andReturn(session);
      expect(session.getAttribute("tournament")).andReturn(tournament);

      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("message"), isA(String.class));

      request.setAttribute(eq("listOfPairs"), isA(ArrayList.class));
      request.setAttribute(eq("maxWins"), isA(Integer.class));
      request.setAttribute(eq("bestOf"), isA(Integer.class));

      expect(request.getSession()).andReturn(session);
      session.setAttribute(eq("tournament"), isA(Tournament.class));

      expect(request.getRequestDispatcher("/pages/show.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(session);
      replay(request);
      show.doPost(request, null);
      verify(session);
      verify(request);

      for(PlayerInfo player : tournament.getListOfPlayers()) {
         assertTrue(player.getRoundPairings().containsKey(1));
      }
   }
}

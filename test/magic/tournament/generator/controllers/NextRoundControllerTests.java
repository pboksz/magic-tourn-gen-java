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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/14/12
 * Time: 11:19 AM
 */
public class NextRoundControllerTests
{
   private HttpServletRequest request;
   private HttpSession session;
   private NextRoundController nextRound;
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
      nextRound = new NextRoundController();

      tournament = new Tournament(4, 3, 3);
      tournament.registerPlayers(getAllPlayers());
   }

   @Test
   public void testNextRoundControllerValidValues() throws Exception{
      expect(request.getSession()).andReturn(session);
      expect(session.getAttribute("tournament")).andReturn(tournament);

      expect(request.getParameterValues("player")).andReturn(new String[]{"player1", "player3"});
      expect(request.getParameterValues("opponent")).andReturn(new String[]{"player2", "player4"});
      expect(request.getParameterValues("wins")).andReturn(new String[]{"2", "2"});
      expect(request.getParameterValues("losses")).andReturn(new String[]{"1", "1"});
      
      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("droppable"), isA(Boolean.class));
      request.setAttribute(eq("results"), isA(ArrayList.class));

      expect(request.getSession()).andReturn(session);
      session.setAttribute(eq("tournament"), isA(Tournament.class));

      expect(request.getRequestDispatcher("/pages/results.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(session);
      replay(request);
      nextRound.doPost(request, null);
      verify(session);
      verify(request);

      assertEquals(2, tournament.getRound());
      for(PlayerInfo player : tournament.getListOfPlayers()) {
         assertTrue(player.getRoundPairings().containsKey(1));
      }
   }
}

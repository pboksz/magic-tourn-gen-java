package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 6/26/12
 * Time: 1:31 PM
 */
public class RegisterControllerTests {
   private HttpServletRequest request;
   private HttpSession session;
   private RegisterController register;
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
      register = new RegisterController();

      tournament = new Tournament(4, 3, 3);
   }

   @Test
   public void testRegisterProperNames() throws Exception {
      expect(request.getSession()).andReturn(session);
      expect(session.getAttribute("tournament")).andReturn(tournament);

      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("message"), isA(String.class));

      Map<String, String[]> paramMap = new TreeMap<String, String[]>();
      paramMap.put("player1", new String[]{"Player1"});
      paramMap.put("player2", new String[]{"Player2"});
      paramMap.put("player3", new String[]{"Player3"});
      paramMap.put("player4", new String[]{"Player4"});

      expect(request.getParameterMap()).andReturn(paramMap);
      
      request.setAttribute(eq("seedSorted"), isA(ArrayList.class));

      expect(request.getSession()).andReturn(session);
      session.setAttribute(eq("tournament"), isA(Tournament.class));
      
      expect(request.getRequestDispatcher("/pages/register.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(session);
      replay(request);
      register.doPost(request, null);
      verify(session);
      verify(request);

      //assert that the tournament has the player names set to the value of the paramMap
      assertTrue(tournament.getMapOfPlayers().containsKey("Player1"));
      assertTrue(tournament.getMapOfPlayers().containsKey("Player2"));
      assertTrue(tournament.getMapOfPlayers().containsKey("Player3"));
      assertTrue(tournament.getMapOfPlayers().containsKey("Player4"));
   }

   @Test
   public void testRegisterDefaultNames() throws Exception {
      expect(request.getSession()).andReturn(session);
      expect(session.getAttribute("tournament")).andReturn(tournament);

      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("message"), isA(String.class));

      Map<String, String[]> paramMap = new TreeMap<String, String[]>();
      paramMap.put("player1", new String[]{""});
      paramMap.put("player2", new String[]{""});
      paramMap.put("player3", new String[]{""});
      paramMap.put("player4", new String[]{""});

      expect(request.getParameterMap()).andReturn(paramMap);

      request.setAttribute(eq("seedSorted"), isA(ArrayList.class));

      expect(request.getSession()).andReturn(session);
      session.setAttribute(eq("tournament"), isA(Tournament.class));

      expect(request.getRequestDispatcher("/pages/register.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(session);
      replay(request);
      register.doPost(request, null);
      verify(session);
      verify(request);

      //assert that the tournament has the player names set to the value of the paramMap
      assertTrue(tournament.getMapOfPlayers().containsKey("player1"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player2"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player3"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player4"));
   }

   @Test
   public void testPlayersAlreadyExist() throws Exception {
      tournament.registerPlayers(getAllPlayers());
      expect(request.getSession()).andReturn(session);
      expect(session.getAttribute("tournament")).andReturn(tournament);

      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("message"), isA(String.class));

      request.setAttribute(eq("seedSorted"), isA(ArrayList.class));

      expect(request.getSession()).andReturn(session);
      session.setAttribute(eq("tournament"), isA(Tournament.class));

      expect(request.getRequestDispatcher("/pages/register.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(session);
      replay(request);
      register.doPost(request, null);
      verify(session);
      verify(request);

      //assert that the tournament has the player names set to the value of the paramMap
      assertTrue(tournament.getMapOfPlayers().containsKey("player1"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player2"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player3"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player4"));
   }

   @Test
   public void testVeryLongNameString() throws Exception {
      expect(request.getSession()).andReturn(session);
      expect(session.getAttribute("tournament")).andReturn(tournament);

      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("message"), isA(String.class));

      Map<String, String[]> paramMap = new TreeMap<String, String[]>();
      paramMap.put("player1", new String[]{"thisStringHasALengthOf24CharactersSoFarAnd44Now48NowAndItWillBeCutOff"});
      paramMap.put("player2", new String[]{""});
      paramMap.put("player3", new String[]{""});
      paramMap.put("player4", new String[]{""});

      expect(request.getParameterMap()).andReturn(paramMap);

      request.setAttribute(eq("seedSorted"), isA(ArrayList.class));

      expect(request.getSession()).andReturn(session);
      session.setAttribute(eq("tournament"), isA(Tournament.class));

      expect(request.getRequestDispatcher("/pages/register.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(session);
      replay(request);
      register.doPost(request, null);
      verify(session);
      verify(request);

      //assert that the tournament has the player names set to the value of the paramMap
      assertTrue(tournament.getMapOfPlayers().containsKey("thisStringHasALengthOf24CharactersSoFarAnd44Now..."));
      assertTrue(tournament.getMapOfPlayers().containsKey("player2"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player3"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player4"));
   }

   @Test
   public void testStringWithSpaces() throws Exception {
      expect(request.getSession()).andReturn(session);
      expect(session.getAttribute("tournament")).andReturn(tournament);

      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("message"), isA(String.class));

      Map<String, String[]> paramMap = new TreeMap<String, String[]>();
      paramMap.put("player1", new String[]{"The String Has Spaces"});
      paramMap.put("player2", new String[]{""});
      paramMap.put("player3", new String[]{""});
      paramMap.put("player4", new String[]{""});

      expect(request.getParameterMap()).andReturn(paramMap);

      request.setAttribute(eq("seedSorted"), isA(ArrayList.class));

      expect(request.getSession()).andReturn(session);
      session.setAttribute(eq("tournament"), isA(Tournament.class));

      expect(request.getRequestDispatcher("/pages/register.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(session);
      replay(request);
      register.doPost(request, null);
      verify(session);
      verify(request);

      //assert that the tournament has the player names set to the value of the paramMap
      assertTrue(tournament.getMapOfPlayers().containsKey("The String Has Spaces"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player2"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player3"));
      assertTrue(tournament.getMapOfPlayers().containsKey("player4"));
   }
}

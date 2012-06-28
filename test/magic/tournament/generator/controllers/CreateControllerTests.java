package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.easymock.EasyMock.*;

public class CreateControllerTests
{
   private HttpServletRequest request;
   private HttpSession session;
   private CreateController create;

   @Before
   public void initializeRequest() {
      request = createStrictMock(HttpServletRequest.class);
      session = createStrictMock(HttpSession.class);
      create = new CreateController();
   }

   @Test
   public void testCreateController() throws Exception {
      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("message"), isA(String.class));

      expect(request.getParameter("howManyPlayers")).andReturn("4");
      expect(request.getParameter("howManyRounds")).andReturn("3");
      expect(request.getParameter("bestOf")).andReturn("3");

      request.setAttribute(eq("howManyPlayers"), isA(Integer.class));

      expect(request.getSession()).andReturn(session);
      session.setAttribute(eq("tournament"), isA(Tournament.class));

      expect(request.getRequestDispatcher("/pages/addplayers.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(session);
      replay(request);
      create.doPost(request, null);
      verify(session);
      verify(request);
   }
}

package magic.tournament.generator.controllers;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;

public class CreateControllerTests
{
   private HttpServletRequest request;
   private CreateController create;

   @Before
   public void initializeRequest() {
      request = createStrictMock(HttpServletRequest.class);
      create = new CreateController();
   }

   @Test
   public void testCreateController() throws Exception {
      request.setAttribute("title", "Add Players");
      request.setAttribute("message", "Please enter each player's name. If left blank the player's name will default to the format [ player# ]");

      expect(request.getParameter("howManyPlayers")).andReturn("4");
      expect(request.getParameter("howManyRounds")).andReturn("3");
      expect(request.getParameter("bestOf")).andReturn("3");
      expect(request.getParameter("whichFormat")).andReturn("Swiss");

      request.setAttribute("howManyPlayers", 4);

      expect(request.getRequestDispatcher("/pages/addplayers.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(request);
      create.doGet(request, null);
      verify(request);
   }
}

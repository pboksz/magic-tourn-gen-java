package magic.tournament.generator.controllers;

import org.junit.Before;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.createMock;

public class CreateControllerTests
{
   private HttpServletRequest request;
   private CreateController create;

   @Before
   public void initializeRequest() {
      request = createMock(HttpServletRequest.class);
      create = new CreateController();
   }

//   @Test
//   public void testCreateController() throws Exception {
//
//      request.setAttribute("title", eq(isA(String.class)));
//      request.setAttribute("message", eq(isA(String.class)));
//
//      expect(request.getParameter("howManyPlayers")).andReturn("4");
//      expect(request.getParameter("howManyRounds")).andReturn("3");
//      expect(request.getParameter("bestOf")).andReturn("3");
//      expect(request.getParameter("whichFormat")).andReturn("Swiss");
//
//      request.setAttribute("howManyPlayers", eq(isA(Integer.class)));
//
//      expect(request.getRequestDispatcher("/pages/addplayers.jsp")).andReturn(createMock(RequestDispatcher.class));
//
//      replay(request);
//      create.doGet(request, null);
//      verify(request);
//   }
}

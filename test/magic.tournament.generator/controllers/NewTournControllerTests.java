package magic.tournament.generator.controllers;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/14/12
 * Time: 11:11 AM
 */
public class NewTournControllerTests
{
   private HttpServletRequest request;
   private NewTournController newTourn;

   @Before
   public void initializeRequest() {
      request = createStrictMock(HttpServletRequest.class);
      newTourn = new NewTournController();
   }

   @Test
   public void testNewTourController() throws Exception{
      request.setAttribute("title", "Tournament Settings");
      request.setAttribute("message", "Please select the tournament settings");
      request.setAttribute("error", "The tournament has been reset");

      expect(request.getRequestDispatcher("/pages/index.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(request);
      newTourn.doGet(request, null);
      verify(request);
   }
}

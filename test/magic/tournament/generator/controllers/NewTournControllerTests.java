package magic.tournament.generator.controllers;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.isA;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/14/12
 * Time: 11:11 AM
 */
public class NewTournControllerTests {
   private HttpServletRequest request;
   private NewTournController newTourn;

   @Before
   public void initializeRequest() {
      request = createStrictMock(HttpServletRequest.class);
      newTourn = new NewTournController();
   }

   @Test
   public void testNewTourController() throws Exception {
      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("message"), isA(String.class));
      request.setAttribute(eq("error"), isA(String.class));

      expect(request.getRequestDispatcher("/pages/index.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(request);
      newTourn.doPost(request, null);
      verify(request);
   }
}

package magic.tournament.generator.controllers;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;

public class IndexControllerTests
{
   private HttpServletRequest request;
   private IndexController index;

   @Before
   public void initializeRequest() {
      request = createStrictMock(HttpServletRequest.class);
      index = new IndexController();
   }

   @Test
   public void testIndexController() throws Exception {
      request.setAttribute(eq("title"), isA(String.class));
      request.setAttribute(eq("message"), isA(String.class));

      expect(request.getRequestDispatcher("/pages/index.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(request);
      index.doPost(request, null);
      verify(request);
   }
}

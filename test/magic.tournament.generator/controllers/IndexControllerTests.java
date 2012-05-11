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
      request.setAttribute("title", "Tournament Settings");
      request.setAttribute("message", "Please select the tournament settings");

      expect(request.getRequestDispatcher("/pages/index.jsp")).andReturn(createMock(RequestDispatcher.class));

      replay(request);
      index.doGet(request, null);
      verify(request);
   }
}

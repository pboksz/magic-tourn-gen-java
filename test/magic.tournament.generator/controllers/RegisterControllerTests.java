package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;
import org.junit.Before;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.createStrictMock;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/14/12
 * Time: 3:49 PM
 */
public class RegisterControllerTests
{
   private HttpServletRequest request;
   private RegisterController register;
   private Tournament tournament;

   @Before
   public void initializeRequest() {
      request = createStrictMock(HttpServletRequest.class);
      register = new RegisterController();

      tournament = new Tournament(4, 3, 3, "Swiss");
   }

//   @Test
//   public void testRegisterController() throws Exception{
//      request.setAttribute("title", "Registered Players");
//      request.setAttribute("message", "Players will be paired by the order in which they are seeded");
//
//      SortedMap<String, String[]> paramMap = new TreeMap<String, String[]>();
//      for(int i=1; i<=4; i++) {
//         paramMap.put("player" + i, new String[] {"Player1" + i});
//      }
//
//      expect(request.getParameterMap()).andReturn(paramMap);
//
//      expect(request.getRequestDispatcher("/pages/register.jsp")).andReturn(createMock(RequestDispatcher.class));
//
//      replay(request);
//      register.doGet(request, null);
//      verify(request);
//   }
}

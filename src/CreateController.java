import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Phill
 * Date: 4/2/12
 * Time: 8:03 PM
 */
public class CreateController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
   {
      String numPlayers = request.getParameter("howManyPlayers");
      String maxRounds = request.getParameter("howManyRounds");
      String bestOf = request.getParameter("bestOf");
      String format = request.getParameter("whichFormat");

      PrintWriter out = response.getWriter();
      out.println("<html><body>" + numPlayers + " " + maxRounds + " " + bestOf + " " + format + "</body></html>");
   }
}

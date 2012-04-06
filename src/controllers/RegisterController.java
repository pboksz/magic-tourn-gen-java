package controllers;

import helpers.PlayerPool;
import helpers.Tournament;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/3/12
 * Time: 11:57 AM
 */
public class RegisterController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
   {
      Tournament tournament = Tournament.getTournament();
      PlayerPool playerPool = tournament.getPlayerPool();

      request.setAttribute("title", "Registered Players");
      request.setAttribute("message", "Players will be paired by the order in which they are seeded");

      if(playerPool.getListOfPlayers().isEmpty()){
         ArrayList<String> playerNames = new ArrayList<String>();
         Map params = request.getParameterMap();
         for (Object key : params.keySet())
         {
            String value = ((String[]) params.get(key))[0];
            String name = value.equals("") ? (String) key : value;
            playerNames.add(name);
         }
         playerPool.registerPlayers(playerNames);
      }
      request.setAttribute("seedSorted", playerPool.getSeedSortedListOfPlayers());

      getServletContext().getRequestDispatcher("/pages/register.jsp").forward(request, response);
   }
}

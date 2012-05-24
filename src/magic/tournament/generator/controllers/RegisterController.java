package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;

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
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
   {
      Tournament tournament = Tournament.getTournament();

      request.setAttribute("title", "Registered Players");
      request.setAttribute("message", "Players will be paired by the order in which they are seeded.");

      if(tournament.getListOfPlayers().isEmpty()){
         ArrayList<String> playerNames = new ArrayList<String>();
         Map params = request.getParameterMap();
         for (Object key : params.keySet())
         {
            String value = ((String[]) params.get(key))[0];
            playerNames.add(validateName(value, (String) key));
         }
         
         tournament.registerPlayers(playerNames);
      }
      request.setAttribute("seedSorted", tournament.getSeedSortedListOfPlayers());

      request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
   }
   
   public String validateName(String value, String key) {
      String name = key;
      //if its alphanumeric the name is the value not the key
      if(value.matches("[a-zA-Z0-9]+")) {
         name = value;
         //if its super long just truncate it to 47 characters plus a "..."
         if(name.length() > 47) {
            name = name.substring(0,47) + "...";
         }
      }
      return name;
   }
}

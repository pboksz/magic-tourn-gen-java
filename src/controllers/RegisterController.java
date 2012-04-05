package controllers;

import helpers.PlayerInfo;
import helpers.PlayerPool;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/3/12
 * Time: 11:57 AM
 */
public class RegisterController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
   {
      HttpSession session = request.getSession();
      session.setAttribute("title", "Registered Players");
      session.setAttribute("message", "Players will be paired by the order in which they are seeded.");

      if(PlayerPool.getListOfPlayers() == null){
         ArrayList<String> playerNames = new ArrayList<String>();
         String[] params = request.getParameterValues("player");
         for(String param : params) {
            playerNames.add(param);
         }
         PlayerPool.registerPlayers(playerNames);
      }

      ArrayList<PlayerInfo> listOfPlayers = PlayerPool.getRankSortedListOfPlayers();
      session.setAttribute("players", listOfPlayers);
      response.sendRedirect("/pages/registered.jsp");
   }
}

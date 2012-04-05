package controllers;

import helpers.PlayerPool;
import helpers.Tournament;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/5/12
 * Time: 2:16 PM
 */
public class DropPlayerController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
   {
      HttpSession session = request.getSession();
      Tournament tournament = Tournament.getTournament();
      
      String dropped = (String) session.getAttribute("dropped");
      String getsBye = (String) session.getAttribute("getsbye");
      
      if(!PlayerPool.dropPlayer(tournament.getRound(), dropped, getsBye)){
         session.setAttribute("error", dropped + " cannot be dropped because there would not be enough players left to adequately pair everyone in the remaining rounds.");
      }
      response.sendRedirect("/pages/results.jsp");
   }
}

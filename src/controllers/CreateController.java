package controllers;

import helpers.Tournament;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
      int numPlayers = Integer.valueOf(request.getParameter("howManyPlayers"));
      int maxRounds = Integer.valueOf(request.getParameter("howManyRounds"));
      int bestOf = Integer.valueOf(request.getParameter("bestOf"));
      String format = request.getParameter("whichFormat");

      Tournament.newTournament(numPlayers, maxRounds, bestOf, format);

      HttpSession session = request.getSession();
      session.setAttribute("howManyPlayers", numPlayers);
      session.setAttribute("title", "Add Players");
      session.setAttribute("message", "Please enter each player's name. If left blank the player's name will default to the format [ player# ].");
      response.sendRedirect("/pages/addplayers.jsp");
   }
}

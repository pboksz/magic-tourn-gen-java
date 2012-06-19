package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/5/12
 * Time: 2:16 PM
 */
public class DropController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      Tournament tournament = (Tournament) request.getSession().getAttribute("tournament");
      String dropped = request.getParameter("dropped");

      //drop the player from the tournment
      tournament.dropPlayer(dropped);

      //set the items to be displayed on the results.jsp page
      String title = "Round " + tournament.getPrevRound() + " Standings";
      request.setAttribute("title", title);
      request.setAttribute("droppable", tournament.canDropPlayer());
      request.setAttribute("results", tournament.getCurrentRankings());

      //set the tournament object so it can be passed to the view
      request.getSession().setAttribute("tournament", tournament);
      request.getRequestDispatcher("/pages/results.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      doGet(request, response);
   }
}

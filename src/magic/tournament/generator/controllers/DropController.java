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
      Tournament tournament = Tournament.getTournament();
      String dropped = request.getParameter("dropped");

      if(!tournament.dropPlayer(dropped)){
         request.setAttribute("error", dropped + " cannot be dropped because there would not be enough players left to adequately pair everyone in the remaining rounds.");
      }
      String title = "Round " + tournament.getPrevRound() + " Standings";
      if(tournament.getRound() > tournament.getMaxRound()) {
         title = "Final Results";
      }
      request.setAttribute("title", title);
      request.setAttribute("droppable", tournament.hasDroppable());
      request.setAttribute("results", tournament.getCurrentRankings());

      request.getRequestDispatcher("/pages/results.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
   {
      doGet(request, response);
   }
}

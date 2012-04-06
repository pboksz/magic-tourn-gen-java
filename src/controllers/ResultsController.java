package controllers;

import helpers.RoundPairings;
import helpers.Tournament;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/4/12
 * Time: 3:55 PM
 */
public class ResultsController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
   {
      Tournament tournament = Tournament.getTournament();
      RoundPairings roundPairings = new RoundPairings();

      String title = "Round " + tournament.getRound() + " Standings";
      if(tournament.getRound() == tournament.getMaxRound()) {
         title = "Final Results";
      }
      request.setAttribute("title", title);
      request.setAttribute("results", tournament.getCurrentRankings());

      getServletContext().getRequestDispatcher("/pages/results.jsp").forward(request, response);
   }
}

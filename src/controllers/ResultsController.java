package controllers;

import helpers.RoundPairings;
import helpers.Tournament;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/4/12
 * Time: 3:55 PM
 */
public class ResultsController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
   {
      Tournament tournament = Tournament.getTournament();
      RoundPairings roundPairings = Tournament.getRoundPairings();

      HttpSession session = request.getSession();
      String title = "Round " + tournament.getRound() + " Standings";
      if(tournament.getRound() == tournament.getMaxRound()) {
         title = "Final Results";
      }
      session.setAttribute("title", title);
      session.setAttribute("results", roundPairings.getCurrentRankings());

      response.sendRedirect("/pages/results.jsp");
   }
}

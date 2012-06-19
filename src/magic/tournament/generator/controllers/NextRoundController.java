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
 * Time: 1:29 PM
 */
public class NextRoundController extends HttpServlet {
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      Tournament tournament = (Tournament) request.getSession().getAttribute("tournament");

      //get array of all the values entered
      String[] players = request.getParameterValues("player");
      String[] opponents = request.getParameterValues("opponent");
      String[] playerWins = request.getParameterValues("wins");
      String[] playerLosses = request.getParameterValues("losses");

      //for each match set the outcomes
      for (int i = 0; i < playerWins.length; i++) {
         String playerName = players[i];
         String opponentName = opponents[i];
         int playerWon = Integer.valueOf(playerWins[i]);
         int playerLost = Integer.valueOf(playerLosses[i]);

         tournament.setPlayerOutcome(playerName, opponentName, playerWon, playerLost);
         tournament.setPlayerOutcome(opponentName, playerName, playerLost, playerWon);
      }
      tournament.nextRound();

      //set the correct title based on if it is the last round or not
      String title = "Round " + tournament.getPrevRound() + " Standings";
      if (tournament.getRound() > tournament.getMaxRound()) {
         title = "Final Results";
      }

      //set all the items to be displayed in the results.jsp page
      request.setAttribute("title", title);
      request.setAttribute("droppable", tournament.canDropPlayer());
      request.setAttribute("results", tournament.getCurrentRankings());

      //set the tournament object so it can be passed to the view
      request.getSession().setAttribute("tournament", tournament);
      getServletContext().getRequestDispatcher("/pages/results.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      doGet(request, response);
   }
}

package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.PlayerInfo;
import magic.tournament.generator.helpers.RoundPairings;
import magic.tournament.generator.helpers.Tournament;

import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedMap;

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
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      Tournament tournament = Tournament.getTournament();
      RoundPairings roundPairings = new RoundPairings();

      String[] players = request.getParameterValues("player");
      String[] opponents = request.getParameterValues("opponent");
      String[] playerWins = request.getParameterValues("wins");
      String[] playerLosses = request.getParameterValues("losses");
      int maxWins = tournament.getMaxWins();
      int bestOf = tournament.getBestOf();

      if (hasValidValues(playerWins, playerLosses, maxWins, bestOf)) {
         for (int i = 0; i < playerWins.length; i++) {
            String playerName = players[i];
            String opponentName = opponents[i];
            int playerWon = Integer.valueOf(playerWins[i]);
            int playerLost = Integer.valueOf(playerLosses[i]);

            tournament.setPlayerOutcome(playerName, opponentName, playerWon, playerLost);
            tournament.setPlayerOutcome(opponentName, playerName, playerLost, playerWon);
         }
         tournament.nextRound();

         String title = "Round " + tournament.getPrevRound() + " Standings";
         if (tournament.getRound() > tournament.getMaxRound()) {
            title = "Final Results";
         }
         request.setAttribute("title", title);
         request.setAttribute("droppable", (tournament.hasDroppable() && (tournament.getRound() <= tournament.getMaxRound())));
         request.setAttribute("results", tournament.getCurrentRankings());

         getServletContext().getRequestDispatcher("/pages/results.jsp").forward(request, response);
      } else {
         request.setAttribute("title", "Round " + tournament.getRound());
         request.setAttribute("message", "Please enter the wins of each player and opponent.");
         request.setAttribute("error", "The values for wins for each player has to be a number, cannot be blank, and should sum to between " + tournament.getMaxWins() + " and " + tournament.getBestOf() + ".");

         if (tournament.isNextRound()) {
            roundPairings.setRoundPairings();
            tournament.incPrevRound();
         }

         ArrayList<PlayerInfo> listOfPairs = new ArrayList<PlayerInfo>();
         SortedMap<String, PlayerInfo> clonedMapOfPlayers = tournament.cloneMapOfPlayers();
         while (clonedMapOfPlayers.size() != 0) {
            PlayerInfo player = clonedMapOfPlayers.get(clonedMapOfPlayers.firstKey());
            listOfPairs.add(player);
            clonedMapOfPlayers.remove(player.getName());
            clonedMapOfPlayers.remove(player.getOpponent());
         }
         request.setAttribute("listOfPairs", listOfPairs);
         request.setAttribute("maxWins", tournament.getMaxWins());
         request.setAttribute("bestOf", tournament.getBestOf());

         request.getRequestDispatcher("/pages/show.jsp").forward(request, response);
      }
   }

   private boolean hasValidValues(String[] playerWins, String[] playerLosses, int maxWins, int bestOf) {
      for (int i = 0; i < playerWins.length; i++) {
         //if something is blank, return error
         //CASE: nothing is filled in for values
         if ((playerWins[i].equals("")) || (playerLosses[i].equals(""))) {
            return false;
         }

         //get the integer values of the wins and losses
         int wins = Integer.valueOf(playerWins[i]);
         int losses = Integer.valueOf(playerLosses[i]);

         //skip over bye round data
         if ((wins != -1) && (losses != -1)) {
            //if values are greater than maxWins, return error
            //CASE: wins is 4 and maxWins is 2 in a bestOf 3 tournament
            if ((wins > maxWins) || (losses > maxWins)) {
               return false;
            }

            //if the sum of the wins and losses is greater than bestOf, return error
            //CASE: wins and losses are both 2 which cannot be the case in a bestOf 3 tournament
            if ((wins + losses) > bestOf) {
               return false;
            }

            //assert that one of the values is equal to maxWins, else return error
            //CASE: wins and losses are both 1 which would get through all the other tests and pass, but it is not right
            if (!((wins == maxWins) || (losses == maxWins))) {
               return false;
            }
         }
      }
      return true;
   }
}

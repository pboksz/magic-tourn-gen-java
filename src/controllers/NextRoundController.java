package controllers;

import helpers.PlayerInfo;
import helpers.PlayerPool;
import helpers.RoundPairings;
import helpers.Tournament;

import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/5/12
 * Time: 1:29 PM
 */
public class NextRoundController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
   {
      Tournament tournament = Tournament.getTournament();
      RoundPairings roundPairings = Tournament.getRoundPairings();

      HttpSession session = request.getSession();
      String[] players = (String[]) session.getAttribute("players");
      String[] opponents = (String[]) session.getAttribute("opponent");
      String[] playerWins = (String[]) session.getAttribute("wins");
      String[] playerLosses = (String[]) session.getAttribute("losses");
      int round = tournament.getRound();
      int maxWins = (int) Math.ceil(tournament.getBestOf() / 2);
      int bestOf = tournament.getBestOf();

      boolean hasErrors = validateValues(playerWins, playerLosses, maxWins, bestOf);

      if(!hasErrors){
         for (int i = 0; i < playerWins.length; i++) {
            String playerName = players[i];
            String opponentName = opponents[i];
            int playerWon = Integer.valueOf(playerWins[i]);
            int playerLost = Integer.valueOf(playerLosses[i]);

            PlayerPool.setPlayerOutcome(playerName, opponentName, round, playerWon, playerLost);
            PlayerPool.setPlayerOutcome(opponentName, playerName, round, playerLost, playerWon);
         }
         tournament.nextRound();

         String title = "Round " + tournament.getRound() + " Standings";
         if(tournament.getRound() == tournament.getMaxRound()) {
            title = "Final Results";
         }
         session.setAttribute("title", title);
         session.setAttribute("results", roundPairings.getCurrentRankings());

         response.sendRedirect("/pages/results.jsp");

      }
      else {
         session.setAttribute("title", "Round" + tournament.getRound());
         session.setAttribute("message", "Please enter the wins of each player and opponent.");
         session.setAttribute("error", "The values for wins for each player has to be a number, cannot be blank, cannot be less than 0 and should sum to " + tournament.getBestOf() + " or less.");

         if (tournament.isNextRound())
         {
            roundPairings.setRoundPairings();
            tournament.incPrevRound();
         }

         ArrayList<PlayerInfo> listOfPairs = new ArrayList<PlayerInfo>();
         SortedMap<String, PlayerInfo> clonedMapOfPlayers = PlayerPool.cloneMapOfPlayers();
         while (clonedMapOfPlayers.size() != 0)
         {
            PlayerInfo player = clonedMapOfPlayers.get(clonedMapOfPlayers.firstKey());
            listOfPairs.add(player);
            clonedMapOfPlayers.remove(player.getName());
            clonedMapOfPlayers.remove(player.getOpponent());
         }
         session.setAttribute("listOfPairs", listOfPairs);
         session.setAttribute("maxWin", Math.ceil(tournament.getBestOf() / 2));
         session.setAttribute("bestOf", tournament.getBestOf());

         response.sendRedirect("/pages/show.jsp");
      }
   }

   private boolean validateValues(String[] playerWins, String[] playerLosses, int maxWins, int bestOf)
   {
      boolean hasErrors = false;
      for (int i = 0; i < playerWins.length; i++)
      {
         if (playerWins[i].matches("[0-maxWins]") && (playerLosses[i].matches("[0-maxWins]")))
         {
            int wins = Integer.valueOf(playerWins[i]);
            int losses = Integer.valueOf(playerLosses[i]);
            if ((wins + losses > bestOf))
            {
               hasErrors = true;
            }
         }
         else
         {
            hasErrors = true;
         }
      }
      return hasErrors;
   }
}

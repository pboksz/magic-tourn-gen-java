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
 * Date: 4/3/12
 * Time: 2:43 PM
 */
public class ShowController extends HttpServlet {
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      Tournament tournament = (Tournament) request.getSession().getAttribute("tournament");
      RoundPairings roundPairings = new RoundPairings(tournament);

      request.setAttribute("title", "Round " + tournament.getRound());
      request.setAttribute("message", "Please enter the wins of each player and opponent.");

      //if this is the next round, set the pairings and increment the prevRound counter
      if (tournament.isNextRound()) {
         roundPairings.setRoundPairings();
         tournament.incPrevRound();
      }

      //clone the player map and use it to add pairs to the list that will be displayed on the next page
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

      //set the tournament object so it can be passed to the view
      request.getSession().setAttribute("tournament", tournament);
      request.getRequestDispatcher("/pages/show.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      doGet(request, response);
   }
}

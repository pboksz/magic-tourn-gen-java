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
 * Date: 4/3/12
 * Time: 2:43 PM
 */
public class ShowController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
   {
      Tournament tournament = Tournament.getTournament();
      RoundPairings roundPairings =  Tournament.getRoundPairings();

      HttpSession session = request.getSession();
      session.setAttribute("title", "Round" + tournament.getRound());
      session.setAttribute("message", "Please enter the wins of each player and opponent.");

      if(tournament.isNextRound()){
         roundPairings.setRoundPairings();
         tournament.incPrevRound();
      }

      ArrayList<PlayerInfo> listOfPairs = new ArrayList<PlayerInfo>();
      SortedMap<String, PlayerInfo> clonedMapOfPlayers = PlayerPool.cloneMapOfPlayers();
      while(clonedMapOfPlayers.size() != 0) {
         PlayerInfo player = clonedMapOfPlayers.get(clonedMapOfPlayers.firstKey());
         listOfPairs.add(player);
         clonedMapOfPlayers.remove(player.getName());
         clonedMapOfPlayers.remove(player.getOpponent());
      }
      session.setAttribute("listOfPairs", listOfPairs);
      session.setAttribute("maxWin", Math.ceil(tournament.getBestOf()/2));
      session.setAttribute("bestOf", tournament.getBestOf());

      response.sendRedirect("/pages/show.jsp");
   }
}

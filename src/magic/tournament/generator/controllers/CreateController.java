package magic.tournament.generator.controllers;

import magic.tournament.generator.helpers.Tournament;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Phill
 * Date: 4/2/12
 * Time: 8:03 PM
 */
public class CreateController extends HttpServlet {
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      request.setAttribute("title", "Add Players");
      request.setAttribute("message", "Please enter each player's name. If left blank the player's name will default to the respective player number.");

      //get all the data from the index.jsp page
      int numPlayers = Integer.valueOf(request.getParameter("howManyPlayers"));
      int maxRounds = Integer.valueOf(request.getParameter("howManyRounds"));
      int bestOf = Integer.valueOf(request.getParameter("bestOf"));

      //create a new tournament object with the parameters input
      Tournament tournament = new Tournament(numPlayers, maxRounds, bestOf);
      request.setAttribute("howManyPlayers", numPlayers);

      //set the tournament object so it can be passed to the view
      request.getSession().setAttribute("tournament", tournament);
      request.getRequestDispatcher("/pages/addplayers.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      doGet(request, response);
   }
}

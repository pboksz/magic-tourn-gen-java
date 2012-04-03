package controllers;

import helpers.Tournament;

import java.io.IOException;

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

      HttpSession session = request.getSession();
      session.setAttribute("title", "Round" + tournament.getRound());
      session.setAttribute("message", "Please enter the wins of each player and opponent.");

      response.sendRedirect("/pages/show.jsp");
   }
}

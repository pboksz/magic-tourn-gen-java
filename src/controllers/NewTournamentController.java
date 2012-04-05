package controllers;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/5/12
 * Time: 2:21 PM
 */
public class NewTournamentController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
   {
      HttpSession session = request.getSession();
      session.setAttribute("title", "Tournament Settings");
      session.setAttribute("message", "Please select the tournament settings.");
      response.sendRedirect("/pages/index.jsp");
   }
}
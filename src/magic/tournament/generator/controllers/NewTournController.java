package magic.tournament.generator.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/5/12
 * Time: 2:21 PM
 */
public class NewTournController extends HttpServlet {
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      request.setAttribute("title", "Tournament Settings");
      request.setAttribute("message", "Please select the tournament settings.");
      request.setAttribute("error", "The tournament has been reset.");

      request.getRequestDispatcher("/pages/index.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      doGet(request, response);
   }
}
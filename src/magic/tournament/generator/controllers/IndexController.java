package magic.tournament.generator.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/10/12
 * Time: 10:33 AM
 */
public class IndexController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
   {
      request.setAttribute("title", "Tournament Settings");
      request.setAttribute("message", "Please select the tournament settings.");

      request.getRequestDispatcher("/pages/index.jsp").forward(request, response);
   }
}

package controllers;

import models.PlayerInfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/16/12
 * Time: 2:25 PM
 */
public class SaveController extends HttpServlet
{
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
   {
      PlayerInfo.savePlayers();
      request.setAttribute("message", "Players have been saved");
      getServletContext().getRequestDispatcher("/pages/index.jsp").forward(request, response);
   }
}

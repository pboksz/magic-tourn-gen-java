<%@ page import="helpers.PlayerInfo" %>
<%@ page import="helpers.PlayerPool" %>
<%--
  Created by IntelliJ IDEA.
  User: phillip
  Date: 4/2/12
  Time: 4:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/includes/header.jsp"%>
<html>
<body>
<div class="main">
   <form name="show" action="nextround">
      <div class="show">
         <table>
            <thead>
            <tr>
               <th>
                  Player Name
               </th>
               <th>
                  Player Wins
               </th>
               <th class="versus">
                  VS
               </th>
               <th>
                  Opponent Name
               </th>
               <th>
                  Opponent Wins
               </th>
            </tr>
            </thead>
            <%--TODO--%>
            <% for(PlayerInfo pair : PlayerPool.getListOfPlayers()){ %>
            <tr>
               <td>
                  <%= pair.getName() %>
                  <input type="hidden" name="player" value="<%= pair.getName() %>">
               </td>
               <td>
                  <% if(!pair.getName().equals("Bye")) { %>
                     <input type="text" name="wins" maxlength="1" onchange="verifyValue(this, 3)">
                  <% } else { %>
                     <input type="hidden" name="wins" value="-1">
                  <% } %>
               </td>
               <td class="versus">
                  <strong>VS</strong>
               </td>
               <td>
                  <%= pair.getOpponent() %>
                  <input type="hidden" name="opponent" value="<%= pair.getOpponent() %>">
               </td>
               <td>
                  <% if(!pair.getOpponent().equals("Bye")) { %>
                  <input type="text" name="losses" maxlength="1" onchange="verifyValue(this, 3)">
                  <% } else { %>
                  <input type="hidden" name="losses" value="-1">
                  <% } %>
               </td>
            </tr>
            <% } %>
         </table>
      </div>
      <input type="submit" value="Submit Round Results">
   </form>
</div>
</body>
</html>
<%@ include file="/includes/footer.jsp"%>
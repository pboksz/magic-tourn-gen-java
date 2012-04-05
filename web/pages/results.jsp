<%@ page import="helpers.PlayerInfo" %>
<%@ page import="helpers.PlayerPool" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: phillip
  Date: 4/2/12
  Time: 4:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/includes/header.jsp"%>
<html>
<body>
<div class="main">
   <form name="results" action="show">
      <div class="results">
         <table>
            <thead>
            <tr>
               <th colspan="3">
                  Player Information
               </th>
               <th colspan="3">
                  Round Stats
               </th>
               <th colspan="2">
                  Individual Stats
               </th>
            </tr>
            <tr>
               <th class="value">
                  Rank
               </th>
               <th class="names">
                  Name
               </th>
               <th class="names">
                  Opponents
               </th>
               <th class="value">
                  Wins
               </th>
               <th class="value">
                  Byes
               </th>
               <th class="value">
                  Losses
               </th>
               <th class="value">
                  Wins
               </th>
               <th class="value">
                  Losses
               </th>
            </tr>
            </thead>
            <% for(PlayerInfo result : (ArrayList<PlayerInfo>) session.getAttribute("results")){ %>
            <tr>
               <td class="value">
                  <%= result.getRank() %>
               </td>
               <td class="names">
                  <%= result.getName() %>
                  <% if (true) { %>
                     <form class="drop" action="dropplayer">
                        <input type="image" src="/images/delete.png" alt="Drop Player">
                     </form>
                  <% } %>
               </td>
               <td class="opponents">
                  <div class="title">View Opponents</div>
                  <div class="list">
                     <% for(int i = 1; i < result.getRoundPairings().size(); i++) { %>
                        <%= i + ")" + result.getRoundPairings().get(i) %><br>
                     <% } %>
                  </div>
               </td>
               <td class="value">
                  <%= result.getRoundWins() %>
               </td>
               <td class="value">
                  <%= result.getRoundByes() %>
               </td>
               <td class="value">
                  <%= result.getRoundLosses() %>
               </td>
               <td class="value">
                  <%= result.getIndividualWins() %>
               </td>
               <td class="value">
                  <%= result.getIndividualLosses() %>
               </td>
            </tr>
            <% } %>
         </table>
      </div>
      <% if(session.getAttribute("title") != "Final Results") { %>
         <input type="submit" value="Submit Round Results">
      <% } %>
   </form>
</div>
</body>
</html>
<%@ include file="/includes/footer.jsp"%>
<%@ page import="magic.tournament.generator.PlayerInfo" %>
<%@ page import="magic.tournament.generator.PlayerPool" %>
<%--
  Created by IntelliJ IDEA.
  User: phillip
  Date: 4/2/12
  Time: 4:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<html>
<body>
<div class="main">
   <form name="register">
      <div class="players">
         <table>
            <thead>
            <tr>
               <th>
                  Player Number
               </th>
               <th>
                  Player Name
               </th>
            </tr>
            </thead>
            <% for(PlayerInfo player : PlayerPool.getListOfPlayers()){ %>
            <tr>
               <td>
                  <%= player.getName() %>
               </td>
               <td>
                  <%= player.getSeed() %>
               </td>
            </tr>
            <% } %>
         </table>
      </div>
      <input type="submit" value="Start First Round">
   </form>
</div>
</body>
</html>
<%@ include file="footer.jsp"%>

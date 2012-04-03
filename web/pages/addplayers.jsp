<%--
  Created by IntelliJ IDEA.
  User: phillip
  Date: 4/2/12
  Time: 4:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/includes/header.jsp"%>
<html>
<body>
<div class="main">
   <form name="addplayers">
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
            <% for(int i = 1; i < Integer.valueOf(request.getSession().getAttribute("howManyPlayers").toString()); i++){ %>
            <tr>
               <td>
                  Player <%= i %>
               </td>
               <td>
                  <input type="text" name="player <%= i %>">
               </td>
            </tr>
            <% } %>
         </table>
      </div>
      <input type="submit" value="Register Players">
   </form>
</div>
</body>
</html>
<%@ include file="/includes/footer.jsp"%>

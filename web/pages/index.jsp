<%--
  Created by IntelliJ IDEA.
  User: phillip
  Date: 4/2/12
  Time: 11:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/includes/header.jsp"%>
<html>
<body>
<div class="main">
   <form name="settings" action="create">
      <div class="settings">
         <table>
            <thead>
            <tr>
               <th>
                  How Many Players?
               </th>
               <th>
                  How Many Rounds?
               </th>
               <th>
                  Play To Best Of?
               </th>
               <th>
                  Which Format?
               </th>
            </tr>
            </thead>
            <tr>
               <td>
                  <select name="howManyPlayers" onchange="addRounds(this.value)">
                     <% for(int i = 4; i <= 16; i++ ) {%>
                        <option><%= i%></option>
                     <% } %>
                  </select>
               </td>
               <td>
                  <select name="howManyRounds">
                     <% for(int i = 3; i <= 3; i++ ) {%>
                        <option><%= i%></option>
                     <% } %>s
                  </select>
               </td>
               <td>
                  <select name="bestOf">
                     <% for(int i = 3; i <= 5; i += 2 ) {%>
                        <option><%= i%></option>
                     <% } %>
                  </select>
               </td>
               <td>
                  <select name="whichFormat">
                     <option>Swiss</option>
                  </select>
               </td>
            </tr>
         </table>
      </div>
      <input type="submit" value="Create Tournament">

   </form>
   <form action="save">
      <input type="submit" value="Save Players">
   </form>
</div>
</body>
</html>
<%@ include file="/includes/footer.jsp"%>

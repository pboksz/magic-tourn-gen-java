<html>

<head>
   <script language="JavaScript" src="/js/application.js"></script>

   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
   <link rel="stylesheet" href="/css/main.css" type="text/css">

   <%--TODO flash messages?--%>
   <title>Magic Tournament Generator | <%= request.getAttribute("title") %></title>
</head>

<body>
   <div class="banner">
      <div class="newbutton">
         <form action="new">
            <input type="submit" value="Reset Tournament" style="width: auto; font-size: 0.75em">
         </form>
      </div>
      <img src="/images/mtg-header.png" alt="Magic Tournament Generator" style="border: 0; padding: 0;">
      <%--<form class="new" action="new">--%>
         <%--<input type="image" src="/images/mtg-header.png" alt="Magic Tournament Generator" style="border: 0; padding: 0;">--%>
      <%--</form>--%>
   </div>
   <div class="header">
      <%= request.getAttribute("title") %>
   </div>

   <% if(request.getAttribute("message") != null) { %>
      <div class="message">
         <%= request.getAttribute("message") %>
      </div>
   <% } %>

   <% if(request.getAttribute("error") != null) { %>
      <div class="errors">
         <%= request.getAttribute("error") %>
      </div>
   <% } %>

</body>
</html>
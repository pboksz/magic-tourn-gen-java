<html>

<head>
   <script language="JavaScript" src="/js/application.js"></script>

   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
   <link rel="stylesheet" href="/css/main.css" type="text/css">

   <%--TODO flash messages?--%>
   <title>Magic Tournament Generator | <%= request.getAttribute("title")!= null ? request.getAttribute("title") : "Tournament Settings" %></title>
</head>

<body>
   <div class="banner">
      <img src="/images/mtg-header.png" alt="Magic Tournament Generator"/>
   </div>
   <div class="header">
      <%= request.getAttribute("title")!= null ? request.getAttribute("title") : "Tournament Settings" %>
   </div>

   <% if(request.getAttribute("message") != null) { %>
      <div class="message">
         <%= request.getAttribute("message") %>
      </div>
   <% } %>

   <% if(request.getAttribute("error") != null) { %>">
   <div class="error">
      <%= request.getAttribute("error") %>
   </div>
   <% } %>
</body>
</html>
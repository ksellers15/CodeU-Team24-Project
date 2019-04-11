<% boolean isUserLoggedIn = (boolean) request.getAttribute("isUserLoggedIn"); %>
<html>
  <head>
    <meta charset="UTF-8">
    <title>CodeU 2019 Starter Project</title>
    <link rel="stylesheet" href="/css/main.css">
  </head>
  <body style="text-align: center;">
    <nav>
      <ul id="navigation">
        <li><a href="/home">Home</a></li>
        <li><a href="/feed.html">Public Feed</a></li>

         <% if(isUserLoggedIn){
          String username = (String) request.getAttribute("username");
         %>
         <li><a href="/user-page.html?user=<%= username %>">Your Page</a></li>
         <li><a href="/logout">Logout</a></li>
         <% }else{ %>
           <li><a href="/login">Login</a></li>
           <li><a href="/signup">Sign up</a></li>
         <% } %>

      </ul>
    </nav>
    <h1>CodeU Starter Project</h1>
    <p>This is the CodeU starter project. Click the links above to login and visit your page.
    You can post messages on your page, and you can visit other user pages if you have
    their URL.</p>
    <p>This is your code now! Feel free to make changes, and don't be afraid to get creative!
    You could start by modifying this page to tell the world more about your team.</p>
  </body>
</html>

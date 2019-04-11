<% boolean error = (boolean) request.getAttribute("error"); %>

<html>
  <head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
  </head>
  <body>
    <form action="/login" method="POST" style="text-align:center;font: 15px sans-serif;">
      <div style="width: 25em;margin: 1em auto;text-align: center;padding: 0 2em 1.25em 2em;background-color: #d6e9f8;border: 2px solid #67a7e3;border-radius: 15px;min-height: 200px;">
        <h1>Login</h1>
        <%if(error){
          String message = (String) request.getAttribute("error_message");
          %>
          <div style="font-size: small; margin: 8px; background: red;
                    color: white; border: solid 1px black;"><%= message %>
                  </div>
        <%}%>
        <p style="padding: 0;margin: 0;">
          <label for="email" style="width: 3em">Email:</label>
          <input type="text" name="email" id="email" style="font-size: inherit;">
        </p>
        <input type="hidden" name="continue" value="/login">
      <p style="/* margin-left: 3em; */">
      </p>
      <p style="padding: 0;margin: auto;">
        <label for="password" style="width: 3em">Password:</label>
        <input type="password" name="password" id="password" style="font-size: inherit;">
      </p>
      <br>
      <input name="action" type="submit" value="Log In" id="btn-login">
      </div>
    </form>
  </body>
</html>

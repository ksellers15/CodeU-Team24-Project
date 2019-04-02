<html>
  <head>
    <title>User Page</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/user-page.css">
    <script src="https://cdn.ckeditor.com/ckeditor5/11.2.0/classic/ckeditor.js"></script>
  </head>
  <body>
    <nav>
      <ul id="navigation">
        <li><a href="/">Home</a></li>
      </ul>
    </nav>
    <h1 id="page-title">User Page</h1>
    <div style="display: -webkit-inline-box; width: 75%;">
      <img src="https://www.manufacturingusa.com/sites/manufacturingusa.com/files/default.png" style="
          height: 150px;
          width: 150px;
          border-radius: 100px;" img="">
      <div id="about_me_container" style="margin: 16px; width: 600px;
       height: 125px;">
        <div id="about_me_text" style="width=inherit; margin-top=10px; margin-bottom=15px;"></div>
          <div id="about_me_form" class="">
              <form action="/about" method="POST">
                <textarea name="about-me" placeholder="about me" rows="4" required="" style="margin: 0px; width: 587px; height: 77px;"></textarea>
                <br>
                <input type="submit" value="Submit">
              </form>
          </div>
      </div>
    </div>
    <form id="message-form" action="/messages" method="POST" class="hidden" enctype="multipart/form-data" style="max-width: 700px;">
	  <ul id="languages"></ul>
      Enter a new message:
      <br/>
      <textarea name="text" id="message-input"></textarea>
      <br/>
      Add an image to your message.
      <input type="file" name="image">
      <br/>
      <input type="hidden" value="" name="recipient" id="recipientInput">
      <script>
	      const config = {removePlugin: [  'List', 'ImageUpload', 'Link' ]};
	      ClassicEditor.create( document.getElementById('message-input'), config );
      </script>
      <input type="submit" value="Submit">
    </form>
    <hr/>

    <div id="message-container">Loading...</div>

  </body>
</html>

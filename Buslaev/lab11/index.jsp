<%--
  Created by IntelliJ IDEA.
  User: scoob
  Date: 06.05.2019
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML><html lang="en"><head>
<meta charset="UTF-8">
<title>Servlet Project</title>
</head>
<body>
<p><h1 align="center">Servlet Project</h1>
<hr><br>
<form action="/Unnamed" method="POST">
  <h3>Enter angle in RAD : <input type="text" name="Angle" value="0"> <br>
    Enter amount of signs after comma : <input type="text" name="Epsilon" value="3"> <br>
    <label>Select function: </label>
    <select id="selectFunc" name="Function">
      <option value="sin(x)">sin(x)</option>
      <option value="cos(x)">cos(x)</option>
      <option value="tg(x)">tg(x)</option>
      <option value="ctg(x)">ctg(x)</option>
    </select>
  </h3>
  <input type="submit" value="Submit"> <br>
</form>
<br><br>
</body>
</html>
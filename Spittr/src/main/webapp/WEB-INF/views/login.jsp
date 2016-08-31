<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/style.css" />" >
  </head>
  <body>
    <h1>Register</h1>

    <sf:form method="POST" commandName="spitter">
      <sf:errors path="*" element="div" cssClass="errors" />

      <sf:label path="username" cssErrorClass="error">Username</sf:label>:
      <sf:input path="username" /><br/>
      <sf:label path="password" cssErrorClass="error">Password</sf:label>:
      <input type="password" name="password" /><br/>
      <input id="remember_me" name="remember-me" type="checkbox"/>
      <label for="remember_me" class="inline">Remember me</label>

      <input type="submit" value="Register" />
    </sf:form>
  </body>
</html>

<%@ taglib url="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib url="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Spittr</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" >"/>
  </head>
  <body>
    <sec:authorize access="isAuthenticated()">
      <h3>Hello <sec:authentication property="principal.username" />!</h3>
    </sec:authorize>

    <hr/>
    <h1>Welcome to Spittr</h1>

    <a ref="<c:url value="/spittles" >">Spittles</a> |
    <a ref="<c:url value="/spitter/register" >">Register</a>
  </body>
</html>
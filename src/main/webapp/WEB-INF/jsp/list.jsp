<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="head.jsp" />
<section>
<h3>Список:</h3>
<div class="row-fluid">
<div class="span4 pagination-centered">
<ul class="unstyled">
<c:forEach var = "user" items = "${users}">
<li><a href="people/${user[1]}">${user[0]}</a></li>
</c:forEach> 
</ul>
</div>
</div>
<hr />
<p>SQL:</p>
<pre>${sql}${sort}</pre>
</section>
<jsp:include page="foot.jsp" />

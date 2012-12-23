<%@page language="java" contentType="text/html" isErrorPage="true" pageEncoding="UTF-8"%>
<jsp:include page="head.jsp" />
<section>
<h1>Error ${pageContext.errorData.statusCode}</h1>
<p>Something went wrong!</p>
</section>
<jsp:include page="foot.jsp" />
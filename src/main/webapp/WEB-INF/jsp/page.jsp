<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="head.jsp" />
<ul class="pager">
<li class="previous">
<a href="<%= request.getContextPath() %>/people" class="pagination-centered">&larr; Back to list</a>
</li>
</ul>
<section>
<div class="row-fluid">
<div class="span4 pagination-centered">
<p>Avatar goes here</p>
</div>
<div class="span8">
<h4>${name}</h4>
<p><em>${info}</em></p>
</div>
</div>
<hr />
<p>SQL:</p>
<pre>${sql}</pre>
</section>
<jsp:include page="foot.jsp" />

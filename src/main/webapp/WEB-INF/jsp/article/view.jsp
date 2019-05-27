<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>게시판</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<h2>글 보기</h2>
	<p>
		<a href="./app/article/list">글 목록</a>
	</p>
	
		<p>글번호 :
		<span>${article.articleId }</span> | <span style="font-weight: bold;">${article.title}</span>
		</p>
		<p>날짜 :
		<span>${article.cdate}</span> | <span>${article.name}</span>
		</p>
		<hr />
		<p>${article.contentHtml}</p>
		<hr />
		<p>
			<a href="./app/article/editForm?articleId=${article.articleId}">수정하기</a>
			<a href="./app/article/delete?articleId=${article.articleId}">삭제하기</a>
		</p>
</body>
</html>
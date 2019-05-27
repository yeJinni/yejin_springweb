<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>글 수정</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<h2>글 수정</h2>
	<p>
		<a href="./app/article/list">글 목록</a>
	</p>
	<form action="./app/article/edit?articleId=${article.articleId}" method="post">
		<p>
			제목 : <input type="text" name="title" value="${article.title}">
		</p>
		<p>
			내용 :
			<textarea name="content" style="width: 100%; height: 200px;" required>${article.content}</textarea>
		</p>
			<button type="submit">등록</button>
		</form>
</body>
</html>
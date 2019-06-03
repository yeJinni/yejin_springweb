<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>게시판</title>
<script type="text/javascript">
		function confirmDelete() {
			if (confirm("삭제하시겠습니까?"))
				return true;
			else
				return false;
		}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<h2>편지 보기</h2>
	
		<p>글번호 :
		<span>${letter.letterId }</span> | <span style="font-weight: bold;">${letter.title}</span>
		</p>
		<p>날짜 :
		<span>${letter.cdate}</span> | <span>${letter.name}</span>
		</p>
		<hr />
		<p>${letter.contentHtml}</p>
		<hr />
		
		
		
</body>
</html>
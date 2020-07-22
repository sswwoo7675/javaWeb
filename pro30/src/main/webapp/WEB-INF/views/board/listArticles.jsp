<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="articlesList" value="${articlesMap.articlesList}" />
<c:set var="section" value="${articlesMap.section}" />
<c:set var="pageNum" value="${articlesMap.pageNum}" />
<c:set var="Lsection" value="${articlesMap.Lsection}" />
<c:set var="Lpage" value="${articlesMap.Lpage}" />
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<style>
	.no-uline {text-decoration:none;}
    .sel-page{text-decoration:none;color:red;}
	.cls1 {text-decoration:none;}
	.cls2{text-align:center; font-size:30px;}
</style>
<script>
	function fn_articleForm(isLogOn,articleForm,loginForm){
		if(isLogOn!='' && isLogOn != 'false'){
			location.href=articleForm;
		}else{
			alert("로그인 후 글쓰기가 가능합니다.");
			location.href=loginForm+'?action=/board/articleForm.do';
		}
	}
</script>
<meta charset="UTF-8">
<title>글목록창</title>
</head>
<body>
	<table align="center" border="1"  width="80%">
		<tr height="10" align="center"  bgcolor="lightgreen">
			<td>글번호</td>
			<td>작성자</td>
			<td>제목</td>
			<td>작성일</td>
		</tr>
	<c:choose>
		<c:when test="${empty articlesList}">
			<tr height="10">
				<td colspan="4">
					<p align="center">
						<b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
					</p>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="article" items="${articlesList}" varStatus="articleNum">
				<tr align="center">
					<td width="5%">${(section-1)*100 + (pageNum-1)*10 + articleNum.count}</td>
					<td width="10%">${article.id}</td>
					<td align="left" width="35%">
						<span style="padding-right:30px;"></span>
					<c:choose>
						<c:when test="${article.level > 1}">
							<c:forEach begin="1" end="${article.level}" step="1">
								<span style="padding-left:20px"></span>
							</c:forEach>
							<span style="font-size:12px;">[답변]</span>
							<a class="cls1" href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
						</c:when>
						<c:otherwise>
							<a class="cls1" href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
						</c:otherwise>
					</c:choose>
					</td>
					<td width="10%"><fmt:formatDate value="${article.writeDate}"/></td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	</table>
	<div class="cls2">
		<c:if test="${not empty articlesList}">
			<c:if test="${section>1}">
				<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${section-1}&pageNum=10">&nbsp; pre </a>
			</c:if>

			<c:choose>
				<c:when test="${section == Lsection}">
					<c:forEach var="page" begin="${(section-1)*10+1}" end="${Lpage}" step="1" varStatus="p">
						<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${section}&pageNum=${p.count}">${page} </a>
					</c:forEach>	
				</c:when>
				<c:otherwise>
					<c:forEach var="page" begin="${(section-1)*10+1}" end="${section*10}" step="1" varStatus="p">
						<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${section}&pageNum=${p.count}">${page} </a>
					</c:forEach>
					<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${section+1}&pageNum=1">&nbsp; next</a>
				</c:otherwise>
			</c:choose>
			
		</c:if>
	</div>
	<br/><br/>
	<a class="cls1" href="javascript:fn_articleForm('${isLogOn}','${contextPath}/board/articleForm.do','${contextPath}/member/loginForm.do')">
		<p class="cls2">글쓰기</p>
	</a>
</body>
</html>























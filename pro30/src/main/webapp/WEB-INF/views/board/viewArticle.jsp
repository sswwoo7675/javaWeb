<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	#tr_btn_modify{
		display:none;
    }
    #tr_file_upload{
    	display:none;
    }
    #tr_file_upload_button{
    	display:none;
    }
</style>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script> 
<script type="text/javascript">
	function backToList(obj){
	    obj.action="${contextPath}/board/listArticles.do";
	    obj.submit();
	}
	
	function fn_enable(obj,isEmptyImg){
		document.getElementById("i_title").disabled=false;
		document.getElementById("i_content").disabled=false;
		document.getElementById("i_imageFileName").disabled=false;
		if(isEmptyImg){
			document.getElementById("tr_file_upload").style.display="table-row";
			document.getElementById("tr_file_upload_button").style.display="table-row";
		}
		document.getElementById("tr_btn_modify").style.display="block";
		document.getElementById("tr_btn").style.display="none";
	}
	
	function fn_modify_article(obj){
		obj.action="${contextPath}/board/modArticle.do";
		obj.submit();
	}
	
	function readURL(input) {
	     if (input.files && input.files[0]) {
	         var reader = new FileReader();
	         reader.onload = function (e) {
	             $('#preview').attr('src', e.target.result);
	         }
	         reader.readAsDataURL(input.files[0]);
	     }
	 }
	
	function fn_remove_article(url,articleNO){
		var form = document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", url);
		var articleNOInput = document.createElement("input");
		articleNOInput.setAttribute("type","hidden");
	    articleNOInput.setAttribute("name","articleNO");
	    articleNOInput.setAttribute("value", articleNO);
	    
	    form.appendChild(articleNOInput);
	    document.body.appendChild(form);
	    form.submit();
	}
	
	function fn_reply_form(url, parentNO){
		var form2 = document.createElement("form");
		form2.setAttribute("method","post");
		form2.setAttribute("action",url);
		form2.setAttribute("name","replyForm");
		var parentNOInput = document.createElement("input");
		parentNOInput.setAttribute("type","hidden");
		parentNOInput.setAttribute("name","parentNO");
		parentNOInput.setAttribute("value",parentNO);
		
		form2.appendChild(parentNOInput);
	    document.body.appendChild(form2);
	    form2.submit();
	}
</script>
<title>글보기</title>
</head>
<body>
	<form name="frmArticle" method="post" enctype="multipart/form-data">
		<table border="0" align="center">
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">글번호</td>
				<td align="left">
					<input type="text" value="${article.articleNO}" disabled/>
					<input type="hidden" name="articleNO" value="${article.articleNO}" />
				</td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">작성자 아이디</td>
				<td align="left"><input type="text" value="${article.id }" name="id" disabled /></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">제목 </td>
				<td align="left">
					<input type="text" value="${article.title }" name="title" id="i_title" disabled />
				</td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">내용</td>
				<td align="left">
    				<textarea rows="20" cols="60" name="content" id="i_content" disabled>${article.content }</textarea>
   				</td> 
			</tr>
		<c:choose>
			<c:when test="${not empty article.imageFileName && article.imageFileName!='null'}">
				<tr>
					<td width="20%" align="center" bgcolor="#FF9933" rowspan="2">이미지</td>
					<td align="left">
						<input type="hidden" name="originalFileName" value="${article.imageFileName}" />
						<img width="300" height="300" src="${contextPath}/download.do?imageFileName=${article.imageFileName}&articleNO=${article.articleNO}" id="preview" /><br/>
					</td>
				</tr>
				<tr>
					<td align="left">
						<input type="file" name="imageFileName" id="i_imageFileName" disabled onchange="readURL(this);" />
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr id="tr_file_upload">
					<td width="20%" align="center" bgcolor="#FF9933" rowspan="2">이미지</td>
					<td align="left">
						<img width="300" height="300" id="preview" />
					</td>
				</tr>
				<tr id="tr_file_upload_button">
					<td align="left">
						<input type="file" name="imageFileName" id="i_imageFileName" disabled onchange="readURL(this);" />
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933">등록일자</td>
				<td align="left">
					<input type=text value="<fmt:formatDate value="${article.writeDate}" />" disabled />
				</td>
			</tr>
			<tr id="tr_btn_modify">
				<td colspan="2" align="center">
					<input type=button value="수정반영하기" onClick="fn_modify_article(frmArticle)"  >
         			<input type=button value="취소" onClick="backToList(frmArticle)">
				</td>
			</tr>
			<tr id="tr_btn">
				<td colspan="2" align="center">
					<c:if test="${member.id==article.id}">
						<input type=button value="수정하기" onClick="fn_enable(this.form, ${empty article.imageFileName});">
	  					<input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNO});">
					</c:if>
					<input type=button value="리스트로 돌아가기"  onClick="backToList(this.form);">
	  				<input type=button value="답글쓰기"  onClick="fn_reply_form('${contextPath}/board/replyForm.do', ${article.articleNO});">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
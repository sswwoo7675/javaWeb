<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  /><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSONTest2</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
    $(function(){
        $("#checkJson").click(function(){
            var article = {articleNO:"114", writer:"박지성", title:"안녕하세요", content: "상품 소개 글입니다."};
            
            $.ajax({
                //type:"POST",
                //url:"${contextPath}/boards",
                type:"PUT",
                url:"${contextPath}/boards/114",
                contentType:"application/json",
                data: JSON.stringify(article),
                success:function(data){
                    alert(data)
                },
                error:function(data){
                    alert("에러가 발생했습니다.");
                }
            });
        });
    })
</script>
</head>
<body>
    <input type="button" id="checkJson" value="새글쓰기" /><br/><br/>
    <div id="output"></div>
</body>
</html>
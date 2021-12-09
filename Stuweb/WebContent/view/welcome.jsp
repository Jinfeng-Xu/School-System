<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
    $(function () {
        var txt = "<span class='txt'></span>";
        $('body').append(txt);
        var txted = $(".txt");
        txted.css({
            position: "absolute",
            color: "#008ed4"
        });
        var Animated = function (x) {
            x.stop().animate({
                top: "-=80px",
                opacity: '1'
            }, 500, function () {
                $(this).animate({
                    opacity: "0"
                }, 500);
            });
        };
        $(document).on("click", function (e) {
            var attr = ["xjf666", "xjfnb"];
            var mathText = attr[Math.floor(Math.random() * attr.length)];
            //获取鼠标的位置
            var x = e.pageX - 32 + "px";
            var y = e.pageY - 18 + "px";
            txted.text(mathText);
            txted.css({
                "left": x,
                "top": y
            });
            Animated(txted);
        });
    });
</script>

<div title="Welcome" style="padding:20px;overflow:hidden; color:Green; " >
	<p style="font-size: 30px; line-height: 20px; height: 30px;">Welcome to use check-in system</p>
  	<p style="font-size: 15px; color: Purple;">The developer：Software-Engproject-Group9</p>
  	<hr />
  	<h2 style="font-size: 20px;" >Introduction</h2>
  	<p style="font-size: 15px;"> This is the system management interface of the student check-in system.
  	</p>
  	<hr />
  	<c:if test="${userType == 2}">		
  	<p style="font-size: 30px; color: red;">${sessionScope.Time}</p>
  	</c:if>
  	<c:if test="${userType == 2 || userType == 3}">		
  	<p style="font-size: 30px; color: red">Next CourseName: ${sessionScope.CourseName} <br/>
  	Next Check-In Time: ${sessionScope.Date}</p>
  	</c:if>
  	
</div>
</body>
</html>
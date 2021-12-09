<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>System</title>
    <link rel="shortcut icon" href="favicon.ico"/>
	<link rel="bookmark" href="favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="easyui/css/default.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='easyui/js/outlook2.js'> </script>
    <script type="text/javascript">
	 var _menus = {"menus":[
						
						{"menuid":"2","icon":"","menuname":"Manage student",
							"menus":[
									{"menuid":"21","menuname":"Student List","icon":"icon-user-student","url":"StudentServlet?method=toStudentListView"},
								]
						},
						<c:if test="${userType == 1 || userType == 3}">						
						{"menuid":"3","icon":"","menuname":"Manage teacher",
							"menus":[
								{"menuid":"31","menuname":"Teacher List","icon":"icon-user-teacher","url":"TeacherServlet?method=toTeacherListView"},
							]
						},
						</c:if>
						<c:if test="${userType == 1}">
						{"menuid":"4","icon":"","menuname":"Manage clazz",
							"menus":[
									{"menuid":"42","menuname":"Clazz List","icon":"icon-house","url":"ClazzServlet?method=toClazzListView"}
								]
						},
						</c:if>
						<c:if test="${userType == 1 || userType == 3}">						
						{"menuid":"6","icon":"","menuname":"Manage course",
							"menus":[
								{"menuid":"61","menuname":"Course List","icon":"icon-book-open","url":"CourseServlet?method=toCourseListView"},
							]
						},
						</c:if>
						{"menuid":"7","icon":"","menuname":"Course selected",
							"menus":[
									{"menuid":"71","menuname":"Course List","icon":"icon-book-open","url":"SelectedCourseServlet?method=toSelectedCourseListView"},
								]
						},
						{"menuid":"8","icon":"","menuname":"Check-in",
							"menus":[
									{"menuid":"81","menuname":"Check-inList","icon":"icon-book-open","url":"AttendanceServlet?method=toAttendanceServletListView"},
								]
						},
						{"menuid":"5","icon":"","menuname":"Manage password",
							"menus":[
							        {"menuid":"51","menuname":"Change password","icon":"icon-set","url":"SystemServlet?method=toPersonalView"},
								]
						},
						<c:if test="${userType == 2}">						
						{"menuid":"6","icon":"","menuname":"Help",
							"menus":[
								{"menuid":"61","menuname":"Help","icon":"icon-book-open","url":"SystemServlet?method=toStudentIntro"},
							]
						},
						</c:if>
						<c:if test="${userType == 1}">						
						{"menuid":"6","icon":"","menuname":"Help",
							"menus":[
								{"menuid":"61","menuname":"Help","icon":"icon-book-open","url":"SystemServlet?method=toAdminIntro"},
							]
						},
						</c:if>
						<c:if test="${userType == 3}">						
						{"menuid":"6","icon":"","menuname":"Help",
							"menus":[
								{"menuid":"61","menuname":"Help","icon":"icon-book-open","url":"SystemServlet?method=toTeacherIntro"},
							]
						}
						</c:if>
						
				]};
    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no" >
	<noscript></noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background:  #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">
       		<span style="color:red; font-weight:bold;">${user.name}&nbsp;</span> 
       		&nbsp;&nbsp;&nbsp;<a href="LoginServlet?method=logout" id="loginOut">Exit</a></span>
        <span style="padding-left:10px; font-size: 16px; ">Check-in System</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">Group9</div>
    </div>
    <div region="west" hide="true" split="true" title="Menu" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="true" border="false">
		<!-- Content -->
	</div>
	
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			 <jsp:include page="welcome.jsp" /> 
		</div>
    </div>
	
	<iframe width=0 height=0 src="view/refresh.jsp"></iframe>
</body>
</html>
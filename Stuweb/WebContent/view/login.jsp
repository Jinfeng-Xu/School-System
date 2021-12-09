<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />


<link rel="shortcut icon" href="favicon.ico"/>
<link rel="bookmark" href="favicon.ico"/>
<link href="h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="h-ui/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="h-ui/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="h-ui/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />

<link href="xjf-ui/spLogin1.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome/css/font-awesome.min.css">

<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">

<script type="text/javascript" src="easyui/jquery.min.js"></script> 
<script type="text/javascript" src="h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="h-ui/lib/icheck/jquery.icheck.min.js"></script> 

<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>

<script type="text/javascript">
	$(function(){
		//Click the image to change verification code
		$("#vcodeImg").click(function(){
			this.src="CaptchaServlet?method=loginCaptcha&t="+new Date().getTime();
		});
		
		//Login
		$("#submitBtn").click(function(){
			var data = $("#form").serialize();
			$.ajax({
				type: "post",
				url: "LoginServlet?method=Login",
				data: data, 
				dataType: "text", //Return Datatype
				success: function(msg){
					if("vcodeError" == msg){
						$.messager.alert("Message", "Wrong verification code!", "warning");
						$("#vcodeImg").click();//Change verification code
						$("input[name='vcode']").val("");//Clear verification code input box
					} else if("loginError" == msg){
						$.messager.alert("Message", "Wrong username or password!", "warning");
						$("#vcodeImg").click();//Change verification code
						$("input[name='vcode']").val("");//Clear verification code input box
					} else if("loginSuccess" == msg){
						/* window.location.href = "SystemServlet?method=toAdminView"; */
						window.location.href = "SystemServlet?method=show";
					} else{
						alert(msg);
					} 
				}
				
			});
		});
		
		//Set Mult-Checkbox
		$(".skin-minimal input").iCheck({
			radioClass: 'iradio-blue',
			increaseArea: '25%'
		});
	})
</script> 
<title>Login|Check-in System</title>
<meta name="keywords" content="Check-in System">

</head>
<body>

<div class="header";>
	<h2 style="color: white; width: 400px; height: 60px; line-height: 60px; margin: 0 0 0 30px; padding: 0;">Check-in System</h2>
</div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form id="form" class="form form-horizontal" method="post">
      <div class="row cl">
        <label class="form-label col-3"><i style="color:white"; class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-8">
          <input id="" name="account" type="text" placeholder="Username" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i style="color:white"; class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-8">
          <input id="" name="password" type="password" placeholder="Password" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-8 col-offset-3">
          <input class="input-text size-L" name="vcode" type="text" placeholder="Input verification code!" style="width: 200px;">
          <img title="Change CAPTCHA" id="vcodeImg" src="CaptchaServlet?method=loginCaptcha"></div>
      </div>
      
      <div class="mt-20 skin-minimal" style="text-align: center;">
		<div class="radio-box">
			<input type="radio" id="radio-2" name="type" checked value=2 />
			<label for="radio-1" style="color:white" >Student</label>
		</div>
		<div class="radio-box">
			<input type="radio" id="radio-3" name="type" value=3 />
			<label for="radio-2" style="color:white" >Teacher</label>
		</div>
		<div class="radio-box">
			<input type="radio" id="radio-1" name="type" value=1 />
			<label for="radio-3" style="color:white" >Administrator</label>
		</div>
	</div>
      
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <input id="submitBtn" type="button" class="btn btn-success radius size-L" value="&nbsp;&nbsp;&nbsp;Login&nbsp;&nbsp;&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer"> Group9 </div>

<!-- 2D -->
<script src="https://cdn.jsdelivr.net/gh/stevenjoezhang/live2d-widget@latest/autoload.js"></script>

</div>

</body>
</html>
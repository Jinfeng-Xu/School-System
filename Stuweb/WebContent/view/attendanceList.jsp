<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>Check-in List</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'Check-in information', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"AttendanceServlet?method=AttendanceList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'studentId',title:'student',width:200,
 		        	formatter: function(value,row,index){
 						if (row.studentId){
 							var studentList = $("#studentList").combobox("getData");
 							for(var i=0;i<studentList.length;i++ ){
 								//console.log(clazzList[i]);
 								if(row.studentId == studentList[i].id)return studentList[i].name;
 							}
 							return row.studentId;
 						} else {
 							return 'not found';
 						}
 					}	
 		        },
 		       	{field:'courseId',title:'course',width:200,
 		        	formatter: function(value,row,index){
 						if (row.courseId){
 							var courseList = $("#courseList").combobox("getData");
 							for(var i=0;i<courseList.length;i++ ){
 								//console.log(clazzList[i]);
 								if(row.courseId == courseList[i].id)return courseList[i].name;
 							}
 							return row.courseId;
 						} else {
 							return 'not found';
 						}
 					}		
 		       	},
 		       {field:'type',title:'check-in type',width:200, sortable: false},
 		      {field:'date',title:'check-in date',width:200, sortable: false},
 		     {field:'cdate',title:'check-in coursedate',width:200,
 		        	formatter: function(value,row,index){
 						if (row.courseId){
 							var courseList = $("#courseList").combobox("getData");
 							for(var i=0;i<courseList.length;i++ ){
 								//console.log(clazzList[i]);
 								if(row.courseId == courseList[i].id)return courseList[i].courseDate;
 							}
 							return row.courseId;
 						} else {
 							return 'not found';
 						}
 					}		
 		       	}
	 		]], 
	        toolbar: "#toolbar",
	        onBeforeLoad : function(){
	        	try{
	        		$("#studentList").combobox("getData")
	        	}catch(err){
	        		preLoadClazz();
	        	}
	        }
	    }); 
		//提前加载学生和课程信息
	    function preLoadClazz(){
	  		$("#studentList").combobox({
		  		width: 80,
		  		height: 25,
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		url: "StudentServlet?method=StudentList&t="+new Date().getTime()+"&from=combox",
		  		
		  	});
	  		$("#courseList").combobox({
		  		width: 80,
		  		height: 25,
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		url: "CourseServlet?method=CourseList&t="+new Date().getTime()+"&from=combox",
		  		
		  	});
	  		 $("#cdateList").combobox({
		  		width: 80,
		  		height: 25,
		  		valueField: "id",
		  		textField: "courseDate",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		url: "CourseServlet?method=CourseList&t="+new Date().getTime()+"&from=combox",
		  		
		  	}); 
	  	}
		
	  //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        afterPageText: 'page    / {pages} pages', 
	        displayMsg: 'The currently displayed {from} - {to} records   Total {total} records',
	    });
	   	
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    
	  //设置编辑按钮
	    $("#edit").click(function(){
	    	table = $("#editTable");
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("Message", "Please select one data to operate on!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    
	    
	    //删除
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("Message", "Please select a piece of data to operate on!", "warning");
            } else{
            	var id = selectRow.id;
            	$.messager.confirm("Message", "All data related to the course will be deleted. Confirm to proceed?", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "AttendanceServlet?method=DeleteAttendance",
							data: {id: id},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("Message","Deleted successfully!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
								}else if(msg == "not found"){
									$.messager.alert("Message","The course selection record does not exist!","info");
								}else{
									$.messager.alert("Message","Fail to delete!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	  	
	  	//设置添加窗口
	    $("#addDialog").dialog({
	    	title: "添加考勤信息",
	    	width: 450,
	    	height: 300,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'add',
					plain: true,
					iconCls:'icon-book-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("Message","Please check your input data!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "AttendanceServlet?method=AddAttendance",
								data: $("#addForm").serialize(),
								success: function(msg){
									/* if(msg == "success"){
										$.messager.alert("Message","Course information added successfully!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_name").textbox('setValue', "");
										//刷新
										$('#dataList').datagrid("reload");
									} */
									if(msg == "Sign-in succeed!"){
										$.messager.alert("Message",msg,"info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_name").textbox('setValue', "");
										//刷新
										$('#dataList').datagrid("reload");
									}
									else{
										$.messager.alert("Message",msg,"warning");
										return;
									} 
								}
							});
						}
					}
				},
				{
					text:'Reset',
					plain: true,
					iconCls:'icon-book-reset',
					handler:function(){
						$("#add_name").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  //下拉框通用属性
/* 	  	$("#add_studentList, #add_courseList,#studentList,#courseList,#add_typeList,#add_cdateList,#cdateList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //不可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  	}); */
		$("#add_studentList, #add_courseList,#studentList,#courseList,#add_typeList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //不可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  	});

	  	//添加信息教师选择框
	    $("#add_studentList").combobox({
	  		url: "StudentServlet?method=StudentList&t="+new Date().getTime()+"&from=combox",
	  		onLoadSuccess: function(){
				//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
				getStudentSelectedCourseList(data[0].id);
	  		},
	  		onChange:function(id,o){
	  			getStudentSelectedCourseList(id);
	  		}
	  	});
	  	
	  	function getStudentSelectedCourseList(studentId){
	  	//添加信息课程选择框
		    $("#add_courseList").combobox({
		  		url: "AttendanceServlet?method=getStudentSelectedCourseList&t="+new Date().getTime()+"&student_id="+studentId,
		  		onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
	  	}
	  	/* function getCourseDate(courseId){
	  		//获取课程时间
	  		$("#add_cdateList").combobox({
		  		url: "AttendanceServlet?method=getCourseDate&t="+new Date().getTime()+"&course_id="+courseId,
		  		onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
	  	}  */
	  	var typeData = [{id:"Morning",text:"Morning"},{id:"Afternoon",text:"Afternoon"}];
	  	$("#add_typeList").combobox({
	  		data:typeData,
	  		valueField: 'id',
	  		textField: 'text',
	  		onLoadSuccess: function(){
				//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	  	$("#typeList").combobox({
	  		data:typeData,
	  		valueField: 'id',
	  		textField: 'text',
	  		width: "80",
	  		height: "25",
	  		onLoadSuccess: function(){
				//默认选择第一条数据
				//var data = $(this).combobox("getData");
				//$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	//var courseData = 
	  	
	    //搜索按钮监听事件
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			studentid: $("#studentList").combobox('getValue') == '' ? 0 : $("#studentList").combobox('getValue'),
	  			courseid: $("#courseList").combobox('getValue') == '' ? 0 : $("#courseList").combobox('getValue'),
				type: $("#typeList").combobox('getValue') == '' ? '' : $("#typeList").combobox('getValue'),
				date:$("#date").datebox('getValue'),
				cdate:$("#cdate").combobox('getValue')== '' ? '':$("#cdateList").combobox('getValue')
	  		});
	  	});
	    
	    $("#clear-btn").click(function(){
	    	$('#dataList').datagrid("reload",{});
	    	$("#studentList").combobox('clear');
	    	$("#courseList").combobox('clear');
	    	$("#cdateList").combobox('clear');
	    });
	    
	    
	});
	</script>
	<script type="text/javascript">
		function myformatter(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		}
		function myparser(s){
			if (!s) return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">add</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">delete</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="margin-top: 3px;">
			student：<input id="studentList" class="easyui-textbox" name="studentList" />
			course：<input id="courseList" class="easyui-textbox" name="courseList" />
			type：<input id="typeList" class="easyui-textbox" name="typeList" />
			date：<input id="date" data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" name="date" />
			coursedate：<input id="cdateList" class="easyui-textbox" name="cdateList" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">search</a>
			<a id="clear-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">Empty the search</a>
		</div>
	</div>
	
	<!-- 添加数据窗口 -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td style="width:40px">student:</td>
	    			<td colspan="3">
	    				<input id="add_studentList" style="width: 200px; height: 30px;" class="easyui-textbox" name="studentid" data-options="required:true, missingMessage:'Please select student'" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">course:</td>
	    			<td colspan="3">
	    				<input id="add_courseList" style="width: 200px; height: 30px;" class="easyui-textbox" name="courseid" data-options="required:true, missingMessage:'Please select course'" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">type:</td>
	    			<td colspan="3">
	    				<input id="add_typeList" style="width: 200px; height: 30px;" class="easyui-textbox" name="type" data-options="required:true, missingMessage:'Please select type'" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<!-- <tr>
	    			<td style="width:40px">courseDate:</td>
	    			<td colspan="3">
	    				<input id="add_cdateList" style="width: 200px; height: 30px;" class="easyui-textbox" name="cdate" data-options="required:true, missingMessage:'Please select courseDate'" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr> -->
	    	</table>
	    </form>
	</div>
	
</body>
</html>
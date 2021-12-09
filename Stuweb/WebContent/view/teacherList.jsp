<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>TeacherList</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		var table;
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'TeacherList', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"TeacherServlet?method=TeacherList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'sn',title:'teacherId',width:150, sortable: true},    
 		        {field:'name',title:'name',width:150},
 		       	{field:'clazz_id',title:'clazz',width:150, 
 		        	formatter: function(value,row,index){
 						if (row.clazzId){
 							var clazzList = $("#clazzList").combobox("getData");
 							for(var i=0;i<clazzList.length;i++ ){
 								//console.log(clazzList[i]);
 								if(row.clazzId == clazzList[i].id)return clazzList[i].name;
 							}
 							return row.clazzId;
 						} else {
 							return 'not found';
 						}
 					}
				}
	 		]], 
	        toolbar: "#toolbar",
	        onBeforeLoad : function(){
	        	try{
	        		$("#clazzList").combobox("getData")
	        	}catch(err){
	        		preLoadClazz();
	        	}
	        }
	    }); 
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        afterPageText: 'page    / {pages} page', 
	        displayMsg: 'The currently displayed {from} - {to} records   Total {total} records', 
	    }); 
	    //设置工具类按钮
	    $("#add").click(function(){
	    	table = $("#addTable");
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	table = $("#editTable");
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("Message", "Please select a piece of data to operate on!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("Message", "Select data to delete!", "warning");
            } else{
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("Message", "All data related to the teacher will be deleted. Confirm to proceed？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "TeacherServlet?method=DeleteTeacher",
							data: {ids: ids},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("Message","Delete successful!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
									$("#dataList").datagrid("uncheckAll");
								} else{
									$.messager.alert("Message","Fail to delete!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	    
	    function preLoadClazz(){
	  		$("#clazzList").combobox({
		  		width: "150",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		url: "ClazzServlet?method=getClazzList&t="+new Date().getTime()+"&from=combox",
		  		onChange: function(newValue, oldValue){
		  			//加载班级下的学生
		  			//$('#dataList').datagrid("options").queryParams = {clazzid: newValue};
		  			//$('#dataList').datagrid("reload");
		  		}
		  	});
	  	}
	    
	  	//设置添加窗口
	    $("#addDialog").dialog({
	    	title: "Add teacher",
	    	width: 850,
	    	height: 550,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'Add',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("Message","Check your input data!","warning");
							return;
						} else{
							var clazzid = $("#add_clazzList").combobox("getValue");
							var name = $("#add_name").textbox("getText");
							var password = $("#add_password").textbox("getText");
							var data = {clazzid:clazzid, name:name, password:password};
							
							$.ajax({
								type: "post",
								url: "TeacherServlet?method=AddTeacher",
								data: data,
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("Message","Add successful!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_number").textbox('setValue', "");
										$("#add_name").textbox('setValue', "");
										$(table).find(".chooseTr").remove();
										
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("Message","Fail to added!","warning");
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
					iconCls:'icon-reload',
					handler:function(){
						$("#add_number").textbox('setValue', "");
						$("#add_name").textbox('setValue', "");
						
						$(table).find(".chooseTr").remove();
						
					}
				},
			],
			onClose: function(){
				$("#add_number").textbox('setValue', "");
				$("#add_name").textbox('setValue', "");
				$(table).find(".chooseTr").remove();
			}
	    });
	  	
	  	
	  	
	  //下拉框通用属性
	  	$("#edit_clazzList, #add_clazzList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //不可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  	});
	  	
	  	
	  	
	  	$("#add_clazzList").combobox({
	  		url: "ClazzServlet?method=getClazzList&t="+new Date().getTime()+"&from=combox",
	  		onLoadSuccess: function(){
				//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	  	//编辑教师信息班级下拉框
	  	$("#edit_clazzList").combobox({
	  		url: "ClazzServlet?method=getClazzList&t="+new Date().getTime()+"&from=combox",
			onLoadSuccess: function(){
				//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	  	
	  	//编辑教师信息
	  	$("#editDialog").dialog({
	  		title: "EditTeacher",
	    	width: 850,
	    	height: 550,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'Submit',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("Message","Check your input data!","warning");
							return;
						} else{
							var clazzid = $("#edit_clazzList").combobox("getValue");
							var id = $("#dataList").datagrid("getSelected").id;
							var name = $("#edit_name").textbox("getText");
							var data = {id:id, clazzid:clazzid, name:name};
							
							$.ajax({
								type: "post",
								url: "TeacherServlet?method=EditTeacher",
								data: data,
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("Message","Edit Successful!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//清空原表格数据
										$("#edit_name").textbox('setValue', "");
										
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
							  			$('#dataList').datagrid("uncheckAll");
										
									} else{
										$.messager.alert("Message","Fail to edit!","warning");
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
					iconCls:'icon-reload',
					handler:function(){
						$("#edit_name").textbox('setValue', "");
						$(table).find(".chooseTr").remove();
						
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_photo").attr("src", "PhotoServlet?method=getPhoto&type=2&tid="+selectRow.id);
				$("#set-photo-id").val(selectRow.id);
				var clazzid = selectRow.clazzId;
				setTimeout(function(){
					$("#edit_clazzList").combobox('setValue', clazzid);
				}, 100);
			},
			onClose: function(){
				$("#edit_name").textbox('setValue', "");
			}
	    });
	   	
	  	 //搜索按钮监听事件
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			teacherName: $('#search_student_name').val(),
	  			clazzid: $("#clazzList").combobox('getValue') == '' ? 0 : $("#clazzList").combobox('getValue')
	  		});
	  	});
	});
	
	//上传图片按钮事件
	$("#upload-photo-btn").click(function(){
		
	});
	function uploadPhoto(){
		var action = $("#uploadForm").attr('action');
		var pos = action.indexOf('tid');
		if(pos != -1){
			action = action.substring(0,pos-1);
		}
		$("#uploadForm").attr('action',action+'&tid='+$("#set-photo-id").val());
		$("#uploadForm").submit();
		setTimeout(function(){
			var message =  $(window.frames["photo_target"].document).find("#message").text();
			$.messager.alert("Message",message,"info");
			
			$("#edit_photo").attr("src", "PhotoServlet?method=getPhoto&tid="+$("#set-photo-id").val());
		}, 1500)
	}
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<c:if test="${userType == 1}">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">Add</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		</c:if>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">Edit</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<c:if test="${userType == 1}">
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">Delete</a></div>
		</c:if>
		<div style="float: left;margin-top:4px;" class="datagrid-btn-separator" >&nbsp;&nbsp;Name：<input id="search_student_name" class="easyui-textbox" name="search_student_name" /></div>
		<div style="margin-left: 10px;margin-top:4px;" >Clazz：<input id="clazzList" class="easyui-textbox" name="clazz" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">Search</a>
		</div>		
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px;">  
   		<div style=" position: absolute; margin-left: 560px; width: 200px; border: 1px solid #EEF4FF" id="photo">
    		<img alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="PhotoServlet?method=getPhoto" />
	    </div> 
   		<form id="addForm" method="post">
	    	<table id="addTable" border=0 style="width:800px; table-layout:fixed;" cellpadding="6" >
	    		<tr>
	    			<td style="width:40px">Clazz:</td>
	    			<td colspan="3">
	    				<input id="add_clazzList" style="width: 200px; height: 30px;" class="easyui-textbox" name="clazzid" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td>Name:</td>
	    			<td colspan="4"><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'Please input teachers name'" /></td>
	    		</tr>
	    		<tr>
	    			<td>Password:</td>
	    			<td colspan="4"><input id="add_password" style="width: 200px; height: 30px;" class="easyui-textbox" type="password" name="password" data-options="required:true, missingMessage:'Please input password'" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	
	<!-- 修改窗口 -->
	<div id="editDialog" style="padding: 10px">
		<div style=" position: absolute; margin-left: 560px; width: 200px; border: 1px solid #EEF4FF">
	    	<img id="edit_photo" alt="Photo" style="max-width: 200px; max-height: 400px;" title="Photo" src="" />
	    	<form id="uploadForm" method="post" enctype="multipart/form-data" action="PhotoServlet?method=SetPhoto" target="photo_target">
	    		<!-- StudentServlet?method=SetPhoto -->
	    		<input type="hidden" name="tid" id="set-photo-id">
		    	<input class="easyui-filebox" name="photo" data-options="prompt:'Select your photo'" style="width:200px;">
		    	<input id="upload-photo-btn" onClick="uploadPhoto()" class="easyui-linkbutton" style="width: 50px; height: 24px;" type="button" value="Upload"/>
		    </form>
	    </div>   
    	<form id="editForm" method="post">
	    	<table id="editTable" border=0 style="width:800px; table-layout:fixed;" cellpadding="6" >
	    		<tr>
	    			<td style="width:40px">Clazz:</td>
	    			<td colspan="3">
	    				<input id="edit_clazzList" style="width: 200px; height: 30px;" class="easyui-textbox" name="clazzid" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td>Name:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'Please input name'" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
<iframe id="photo_target" name="photo_target"></iframe>  	
</body>
</html>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#user_dept_tree').tree({
			url : 'deptAction!getAllDepts.action',
			lines : true,
			parentField : 'pid',
			onClick : function(node) {
				
			}
		});
		
		$('#admin_yhgl_datagrid').datagrid({
			url : 'userAction!datagrid.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, {
				field : 'name',
				title : '登录名称',
				width : 150,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'pwd',
				title : '密码',
				width : 150,
				formatter : function(value, row, index) {
					return '******';
				}
			}, {
				field : 'createdatetime',
				title : '创建时间',
				width : 150,
				sortable : true
			}, {
				field : 'modifydatetime',
				title : '最后修改时间',
				width : 150,
				sortable : true
			} ] ],
			toolbar : [ {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			},{
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			},{
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					_userEdit();
				}
			},{
				text : '密码变更',
				iconCls : 'icon-pwedit',
				handler : function() {
					_userEdit();
				}
			} ],
			onRowContextMenu:function(e,rowIndex,rowData){
	    		/*console.info(e);
	    		console.info(rowIndex);
	    		console.info(rowData);*/ 
	    		e.preventDefault();
	    		$(this).datagrid('unselectAll');
	    		$(this).datagrid('selectRow',rowIndex);
	    	    $('#用户管理_menu').menu('show', {  
	    	        left: e.pageX,  
	    	        top: e.pageY  
	    	      });  
	    	} 
		});
	});

	function searchFun() {
		$('#admin_yhgl_datagrid').datagrid('load', serializeObject($('#admin_yhgl_searchForm')));
	}
	function clearFun() {
		$('#admin_yhgl_layout input[name=name]').val('');
		$('#admin_yhgl_datagrid').datagrid('load', {});
	}
	function append() {
		//$('#admin_yhgl_addForm input').val('');
		var dg = $('<div/>').dialog({
		    		modal:true,
					title : '添加用户',
					width : 800,
					height : 300,
					href : 'propage/sys/user/reg.jsp',
					buttons : [ {
						text : '添加',
						//iconCls : 'icon-add',
						handler : function() {
							$('#user_reg_regForm').form('submit', {
								url : 'userAction!add.action',
								success : function(r) {
									var obj = jQuery.parseJSON(r);
									$('#admin_yhgl_datagrid').datagrid('insertRow',{
										index:0,
										row:obj.obj
									});
									dg.dialog('close');
								}
							});
						}
					} ],
					onClose : function(){
						$(this).dialog('destroy');
					}
				});
	}
	function remove() {
		var rows = $('#admin_yhgl_datagrid').datagrid('getChecked');
		//var rows = $('#admin_yhgl_datagrid').datagrid('getSelected');
		//var rows = $('#admin_yhgl_datagrid').datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : 'userAction!remove.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(r) {
							$('#admin_yhgl_datagrid').datagrid('load');
							$('#admin_yhgl_datagrid').datagrid('unselectAll');
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			});
		} else {
			$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录！'
			});
		}
	}
	function _userEdit() {
		var l_dg_getChecked = $('#admin_yhgl_datagrid').datagrid('getChecked');
		if (l_dg_getChecked.length ==1) {
			var rowNum = $('#admin_yhgl_datagrid').datagrid('getRowIndex',l_dg_getChecked);
	    	var l_dialog_userEdit = $('<div/>').dialog({
	    		modal:true,
				title : '编辑用户',
				width : 800,
				height : 300,
				href : 'propage/sys/user/edit.jsp',
				buttons : [ {
					text : '提交',
					//iconCls : 'icon-add',
					handler : function() {
						$('#form_editUser').form('submit', {
							url : 'userAction!edit.action',
							success : function(r) {
								var obj = jQuery.parseJSON(r);
								$('#admin_role_datagrid').datagrid('updateRow',{
									index:rowNum,
									row:obj.obj
								});
								l_dialog_userEdit.dialog('close');
								$.messager.show({
									title : '提示',
									msg : obj.msg
								});
							}
						});
					}
				} ],
				onClose : function(){
					l_dialog_userEdit.dialog('destroy');
				},
				onLoad : function(){
					$('#form_editUser').form('load',l_dg_getChecked[0]);
					/* var f = d.find('roleEdit');
					var authIds = f.find('input[name=authIds]');
					var authIdsTree = authIds.combotree({
						lines : true,
						url : '${pageContext.request.contextPath}/demo/authAction!doNotNeedSession_treeRecursive.action',
						checkbox : true,
						multiple : true,
						onLoadSuccess : function() {
							parent.$.messager.progress('close');
						}
					}); */
				}
			});
		}else{
			
		}
	}
</script>
<div id="admin_yhgl_layout" class="easyui-layout" data-options="fit:true,border:false" style="margin-top:1px;margin-left:1px">
	<!-- <div data-options="region:'north',title:'查询条件',border:false" style="height: 100px;">
		<form id="admin_yhgl_searchForm">
			检索用户名称(可模糊查询)：<input name="name" /><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="clearFun();">清空</a>
		</form>
	</div> -->
	<div data-options="region:'center',title:'员工帐号'">
		<table id="admin_yhgl_datagrid"></table>
	</div>
	<!-- <div data-options="region:'west',title:'部门',split:true" style="width:150px">
		<ul id="user_dept_tree"></ul>
	</div> -->
</div>

<div id="用户管理_menu" class="easyui-menu" style="width:120px;">
	<div onclick="_userEdit()">编辑</div>
	<div onclick="_roleRemove()">删除</div>
</div>


<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#admin_role_datagrid').datagrid({
			url : 'roleAction!datagrid.action',
			fit : true,
			//fitColumns : false,
			border : false,
			pagination : true,//显示页数
			idField : 'id',
			rownumbers : true,//显示行数
			singleSelect : true,//只选 一行
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			columns : [ [ {
				field : 'id',
				title : '编号',
				hidden : true
			}, {
				field : 'name',
				title : '角色名称',
				width : 150,
				sortable : true
			},{
				field : 'remark',
				title : '角色描述',
				width : 300,
			}] ],
			toolbar : [ {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					_roleAdd();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					_roleRemove();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
				}
			}, '-' ],
			onRowContextMenu:function(e,rowIndex,rowData){
	    		/* console.info(e);
	    		console.info(rowIndex);
	    		console.info(rowData); */
	    		e.preventDefault();/*禁止浏览器右键  */
	    		$(this).datagrid('unselectAll');
	    		$(this).datagrid('selectRow',rowIndex);
	    	    $('#角色管理_menu').menu('show', {  
	    	        left: e.pageX,  
	    	        top: e.pageY  
	    	      });  
	    	}
		});
	});

	function searchFun() {
		$('#admin_role_datagrid').datagrid('load', serializeObject($('#admin_yhgl_searchForm')));
	}
	function clearFun() {
		$('#admin_yhgl_layout input[name=name]').val('');
		$('#admin_role_datagrid').datagrid('load', {});
	}
	function _roleEdit() {
		$('#dialog_role input').val('');
		var role = $('#admin_role_datagrid').datagrid('getSelected');
		var rowNum = $('#admin_role_datagrid').datagrid('getRowIndex',role);
    	var d = $('<div/>').dialog({
    		modal:true,
			title : '编辑角色',
			width : 500,
			height : 300,
			href : 'propage/sys/role/roleEdit.jsp',
			buttons : [ {
				text : '提交',
				//iconCls : 'icon-add',
				handler : function() {
					$('#roleEdit').form('submit', {
						url : 'roleAction!edit.action',
						success : function(r) {
							var obj = jQuery.parseJSON(r);
								$('#admin_role_datagrid').datagrid('updateRow',{
									index:rowNum,
									row:obj.obj
								});
								d.dialog('close');
						}
					});
				}
			} ],
			onClose : function(){
				$(this).dialog('destroy');
			},
			onLoad : function(){
				$('#roleEdit').form('load',role);
				var f = d.find('roleEdit');
				var authIds = f.find('input[name=authIds]');
				var authIdsTree = authIds.combotree({
					lines : true,
					url : '${pageContext.request.contextPath}/demo/authAction!doNotNeedSession_treeRecursive.action',
					checkbox : true,
					multiple : true,
					onLoadSuccess : function() {
						parent.$.messager.progress('close');
					}
				});
			}
		});
	}
	
	function _roleAdd() {
		$('#dialog_role input').val('');
		//$('#admin_role_addDialog').dialog('open');
	    var d = $('<div/>').dialog({
    		modal:true,
			title : '添加角色',
			width : 500,
			height : 300,
			href : 'propage/sys/role/roleAdd.jsp',
			buttons : [ {
				text : '添加',
				//iconCls : 'icon-add',
				handler : function() {
					$('#roleAdd').form('submit', {
						url : 'roleAction!add.action',
						success : function(r) {
							var obj = jQuery.parseJSON(r);
								/*$('#admin_yhgl_datagrid').datagrid('load');*/
								/*$('#admin_yhgl_datagrid').datagrid('appendRow',obj.obj);*/
								$('#admin_role_datagrid').datagrid('insertRow',{
									index:0,
									row:obj.obj
								});
								d.dialog('close');
						}
					});
				}
			} ],
			onClose : function(){
				$(this).dialog('destroy');
			}
		});
	}
	
	function _roleRemove() {
		//var rows = $('#admin_role_datagrid').datagrid('getChecked');
		var role = $('#admin_role_datagrid').datagrid('getSelected');
		//var rows = $('#admin_role_datagrid').datagrid('getSelections');
		if (role) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					$.ajax({
						url : 'roleAction!remove.action',
						data : {
							id : role.id
						},
						dataType : 'json',
						success : function(r) {
							$('#admin_role_datagrid').datagrid('load');
							$('#admin_role_datagrid').datagrid('unselectAll');
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
	
	function _roleGrant() {
		$('#dialog_role input').val('');
		var role = $('#admin_role_datagrid').datagrid('getSelected');
		var rowNum = $('#admin_role_datagrid').datagrid('getRowIndex',role);
		//$('#admin_role_addDialog').dialog('open');
    	var d = $('<div/>').dialog({
    		modal:true,
			title : '角色授权',
			width : 500,
			height : 300,
			href : 'propage/sys/role/roleGrant.jsp',
			buttons : [ {
				text : '提交',
				//iconCls : 'icon-add',
				handler : function() {
					$('#form_roleGrant').form('submit',{
						url : 'roleAction!grant.action',
						onSubmit : function() {
							var checknodes = resourceTree.tree('getChecked');
							var ids = [];
							if (checknodes && checknodes.length > 0) {
								for ( var i = 0; i < checknodes.length; i++) {
									ids.push(checknodes[i].id);
								}
							}
							$('#resourceIds').val(ids);
						},
						success : function(result) {
							result = $.parseJSON(result);
							d.dialog('close');
						}
					});
				}
			} ],
			onClose : function(){
				$(this).dialog('destroy');
			},
			onLoad : function(){
				$('#form_roleGrant').form('load',role);
				$('#role_name').html(role.name);
				$('#role_remark').html(role.remark);
				//abc.attr({style:"font-size:30px"});
			}
		});
	}
</script>
<div id="admin_yhgl_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table id="admin_role_datagrid"></table>
	</div>
	<div id="角色管理_menu" class="easyui-menu" style="width:120px;">
		<div onclick="_roleEdit()">编辑</div>
		<div onclick="_roleRemove()">删除</div>
		<div onclick="_roleGrant()">授权</div>
	</div>
</div>




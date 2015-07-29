<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$.ajax({
			//url : 'resourceAction!getAllTrees.action',
			url : 'userAction!getUserMenus.action',
			dataType : 'json',
			success : function(r) {
				console.info(r);
				for (var i = 0; i < r.length; i++) {
					var menu = r[i];
					if (menu.pid == undefined) {
						var sub_menu = [];
						for(var j=0;j<r.length;j++){
							var sm = r[j];
							if(sm.pid==menu.id)
							{
								sub_menu.push(r[j]);
							}
						}
						$('#sys_menu').accordion('add', {
							title : menu.text,
							content : '<div><ul id="'+menu.text+'"></ul></div>'
						});
						$('#'+menu.text+'').tree({
							data : sub_menu,
							onClick : function(node) {
								addTab(node);
							}
						});
					}
				}
			}
		});
	});
</script>
<div id="sys_menu" title="" class="easyui-accordion"
	data-options="fit:true,border:false,animate:false"> 
</div>
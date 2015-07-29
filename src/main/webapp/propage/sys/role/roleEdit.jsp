<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="roleEdit" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<th>角色名称<input name="id" type="hidden"></th>
					<td><input name="name" type="text" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<th>角色描述</th>
					<td><textarea name="remark" rows="5" cols="" class="span5"></textarea></td>
				</tr>
			</tr>
			</table>
		</form>
	</div>
</div>
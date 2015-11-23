<%@ page import="com.surelution.zjolap.CustomerTypeLevel2" %>
<!DOCTYPE html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
	<div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">更新机构类别</h4>
        </div>
        
        
        <g:form action="save" method="post">
		<div class="modal-body">
				<g:hiddenField name="id" value="${customerTypeLevel2Instance?.id}" />
				<g:hiddenField name="version" value="${customerTypeLevel2Instance?.version}" />
				<g:hiddenField name="max" value="${params.max}"/>
				<g:hiddenField name="offset" value="${params.offset}"/>
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
		 </div>	
		 <div class="modal-footer">		
				<fieldset class="buttons">
					<g:actionSubmit class="save btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
				</fieldset>
		</div>
			</g:form>
	</body>
</html>

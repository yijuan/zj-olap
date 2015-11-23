<%@ page import="com.surelution.zjolap.CustomerVistingType" %>
<!DOCTYPE html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'customerVistingType.label', default: 'CustomerVistingType')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
	 <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">更新回访方式</h4>
        </div>
        
        
        <g:form action="save" method="post">
		<div class="modal-body">
				<g:hiddenField name="id" value="${customerVistingTypeInstance?.id}" />
				<g:hiddenField name="version" value="${customerVistingTypeInstance?.version}" />
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

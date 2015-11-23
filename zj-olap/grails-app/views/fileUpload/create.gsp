<%@ page import="com.surelution.zjolap.FileUpload" %>
<!DOCTYPE html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'fileUpload.label', default: 'FileUpload')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
	    <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">新增附件</h4>
        </div>
			<g:uploadForm action="save">
		<div class="modal-body">  
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'license', 'error')} ">
						<label for="license">
							附件
						</label>
						<input type="file" name="file"/>
					</div>
					<g:hiddenField name="fromID" value="${params.fromID}"/>
					<g:hiddenField name="useOpter" value="${params.useOpter}"/>
				</fieldset>
				</div>
					
					<div class="modal-footer">		
				<fieldset class="buttons">
					<g:submitButton name="create" class="save btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>		
		          </div>
		          </g:uploadForm>
		
	</body>
</html>

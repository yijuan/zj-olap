
<%@ page import="com.surelution.zjolap.ExcelUpload" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'excelUpload.label', default: 'ExcelUpload')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-excelUpload" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
			<span class="menuButton"><g:link class="create" action="createSalesOrder"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="show-excelUpload" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list excelUpload">
			
				<g:if test="${excelUploadInstance?.deleted}">
				<li class="fieldcontain">
					<span id="deleted-label" class="property-label"><g:message code="excelUpload.deleted.label" default="Deleted" /></span>
					
						<span class="property-value" aria-labelledby="deleted-label"><g:formatBoolean boolean="${excelUploadInstance?.deleted}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${excelUploadInstance?.originalFileName}">
				<li class="fieldcontain">
					<span id="originalFileName-label" class="property-label"><g:message code="excelUpload.originalFileName.label" default="Original File Name" /></span>
					
						<span class="property-value" aria-labelledby="originalFileName-label"><g:fieldValue bean="${excelUploadInstance}" field="originalFileName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${excelUploadInstance?.uploadedAt}">
				<li class="fieldcontain">
					<span id="uploadedAt-label" class="property-label"><g:message code="excelUpload.uploadedAt.label" default="Uploaded At" /></span>
					
						<span class="property-value" aria-labelledby="uploadedAt-label"><g:formatDate date="${excelUploadInstance?.uploadedAt}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${excelUploadInstance?.user}">
				<li class="fieldcontain">
					<span id="user-label" class="property-label"><g:message code="excelUpload.user.label" default="User" /></span>
					
						<span class="property-value" aria-labelledby="user-label"><g:link controller="user" action="show" id="${excelUploadInstance?.user?.id}">${excelUploadInstance?.user?.username}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${excelUploadInstance?.id}" />
					<g:link class="edit" action="edit" id="${excelUploadInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

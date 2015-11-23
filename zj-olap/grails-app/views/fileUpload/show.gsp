
<%@ page import="com.surelution.zjolap.FileUpload" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fileUpload.label', default: 'FileUpload')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-fileUpload" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="['附件']" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="['附件']" /></g:link></li>
			</ul>
		</div>
		<div id="show-fileUpload" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="['文件']" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list fileUpload">
			
				<g:if test="${fileUploadInstance?.fromID}">
				<li class="fieldcontain">
					<span id="fromID-label" class="property-label"><g:message code="fileUpload.fromID.label" default="From ID" /></span>
					
						<span class="property-value" aria-labelledby="fromID-label"><g:fieldValue bean="${fileUploadInstance}" field="fromID"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fileUploadInstance?.fileName}">
				<li class="fieldcontain">
					<span id="fileName-label" class="property-label"><g:message code="fileUpload.fileName.label" default="File Name" /></span>
					
						<span class="property-value" aria-labelledby="fileName-label"><g:fieldValue bean="${fileUploadInstance}" field="fileName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fileUploadInstance?.fileUUID}">
				<li class="fieldcontain">
					<span id="fileUUID-label" class="property-label"><g:message code="fileUpload.fileUUID.label" default="File UUID" /></span>
					
						<span class="property-value" aria-labelledby="fileUUID-label"><g:fieldValue bean="${fileUploadInstance}" field="fileUUID"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fileUploadInstance?.fileUrl}">
				<li class="fieldcontain">
					<span id="fileUrl-label" class="property-label"><g:message code="fileUpload.fileUrl.label" default="File Url" /></span>
					
						<span class="property-value" aria-labelledby="fileUrl-label"><g:fieldValue bean="${fileUploadInstance}" field="fileUrl"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fileUploadInstance?.uploadDate}">
				<li class="fieldcontain">
					<span id="uploadDate-label" class="property-label"><g:message code="fileUpload.uploadDate.label" default="Upload Date" /></span>
					
						<span class="property-value" aria-labelledby="uploadDate-label"><g:formatDate date="${fileUploadInstance?.uploadDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${fileUploadInstance?.useOpter}">
				<li class="fieldcontain">
					<span id="useOpter-label" class="property-label"><g:message code="fileUpload.useOpter.label" default="Use Opter" /></span>
					
						<span class="property-value" aria-labelledby="useOpter-label"><g:fieldValue bean="${fileUploadInstance}" field="useOpter"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${fileUploadInstance?.id}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

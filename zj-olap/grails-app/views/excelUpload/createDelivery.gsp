<%@ page import="com.surelution.zjolap.ExcelUpload" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'excelUpload.label', default: 'ExcelUpload')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
	<section class="content">
		<a href="#create-excelUpload" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
		</div>
		<div id="create-excelUpload" class="content scaffold-create" role="main">
			<h1><g:message code="default.createDelivery.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${excelUploadInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${excelUploadInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:each in="${excelMessages }" var="excelMessage">
				<p style="color: red">${excelMessage.row + 1 }行${excelMessage.column }列${excelMessage.message }</p>
			</g:each>
			<g:uploadForm action="saveDelivery">
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: excelUploadInstance, field: 'uploadedAt', 'error')} required">
						<label for="uploadedAt">
							<g:message code="excelUpload.file.label" default="File name" />
							<span class="required-indicator">*</span>
						</label>
						<input type="file" name="excelFile"/>
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:uploadForm>
		</div>
		</section>
	</body>
</html>


<%@ page import="com.surelution.zjolap.BigCase" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bigCase.label', default: 'BigCase')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bigCase" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bigCase" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bigCase">
			
				<g:if test="${bigCaseInstance?.defaultOption}">
				<li class="fieldcontain">
					<span id="defaultOption-label" class="property-label"><g:message code="bigCase.defaultOption.label" default="Default Option" /></span>
					
						<span class="property-value" aria-labelledby="defaultOption-label"><g:formatBoolean boolean="${bigCaseInstance?.defaultOption}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${bigCaseInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="bigCase.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${bigCaseInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${bigCaseInstance?.id}" />
					<g:link class="edit" action="edit" id="${bigCaseInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>


<%@ page import="com.surelution.zjolap.CustomerTypeLevel2" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-customerTypeLevel2" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="show-customerTypeLevel2" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list customerTypeLevel2">
			
				<g:if test="${customerTypeLevel2Instance?.level1}">
				<li class="fieldcontain">
					<span id="level1-label" class="property-label"><g:message code="customerType.label" default="Level1" /></span>
					
						<span class="property-value" aria-labelledby="level1-label"><g:link controller="customerType" action="show" id="${customerTypeLevel2Instance?.level1?.id}">${customerTypeLevel2Instance?.level1?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${customerTypeLevel2Instance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="customerTypeLevel2.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${customerTypeLevel2Instance}" field="name"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${customerTypeLevel2Instance?.id}" />
					<g:actionSubmit class="edit" action="edit" id="${customerTypeLevel2Instance?.id}"  value="${message(code: 'default.button.edit.label', default: 'edit')}" ><g:message code="default.button.edit.label" default="Edit" /></g:actionSubmit>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

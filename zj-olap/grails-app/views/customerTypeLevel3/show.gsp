
<%@ page import="com.surelution.zjolap.CustomerTypeLevel3" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-customerTypeLevel3" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="show-customerTypeLevel3" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list customerTypeLevel3">
			
				<g:if test="${customerTypeLevel3Instance?.level2}">
				<li class="fieldcontain">
					<span id="level2-label" class="property-label"><g:message code="customerTypeLevel3.level2.label" default="Level2" /></span>
					
						<span class="property-value" aria-labelledby="level2-label"><g:link controller="customerTypeLevel2" action="show" id="${customerTypeLevel3Instance?.level2?.id}">${customerTypeLevel3Instance?.level2?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${customerTypeLevel3Instance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="customerTypeLevel3.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${customerTypeLevel3Instance}" field="name"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${customerTypeLevel3Instance?.id}" />
					<g:actionSubmit class="edit" action="edit" id="${customerTypeLevel3Instance?.id}"  value="${message(code: 'default.button.edit.label', default: 'edit')}" ><g:message code="default.button.edit.label" default="Edit" /></g:actionSubmit>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>


<%@ page import="com.surelution.zjolap.WarningRule" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'warningRule.label', default: 'WarningRule')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-warningRule" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="show-warningRule" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list warningRule">
			
				<g:if test="${warningRuleInstance?.branch}">
				<li class="fieldcontain">
					<span id="branch-label" class="property-label"><g:message code="warningRule.branch.label" default="Branch" /></span>
					
						<span class="property-value" aria-labelledby="branch-label">${warningRuleInstance?.branch?.name}</span>
					
				</li>
				</g:if>
			
				<g:if test="${warningRuleInstance?.gasType}">
				<li class="fieldcontain">
					<span id="gasType-label" class="property-label"><g:message code="warningRule.gasType.label" default="Gas Type" /></span>
					
						<span class="property-value" aria-labelledby="gasType-label">${warningRuleInstance?.gasType?.category?.name }-&gt; ${warningRuleInstance?.gasType?.name}</span>
					
				</li>
				</g:if>
			
				<g:if test="${warningRuleInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="warningRule.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${warningRuleInstance}" field="typeName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${warningRuleInstance?.value}">
				<li class="fieldcontain">
					<span id="value-label" class="property-label"><g:message code="warningRule.value.label" default="Value" /></span>
					
						<span class="property-value" aria-labelledby="value-label"><g:fieldValue bean="${warningRuleInstance}" field="value"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${warningRuleInstance?.id}" />
					<g:actionSubmit class="edit" action="edit" value="${message(code:'default.button.edit.label', default:'Edit')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

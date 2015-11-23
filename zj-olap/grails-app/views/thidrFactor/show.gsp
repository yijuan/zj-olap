
<%@ page import="com.surelution.zjolap.ThidrFactor" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'thidrFactor.label', default: 'ThidrFactor')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-thidrFactor" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-thidrFactor" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list thidrFactor">
			
				<li class="fieldcontain">
					<span id="branch-label" class="property-label"><g:message code="thidrFactor.branch.label" default="Branch" /></span>
					
						<span class="property-value" aria-labelledby="branch-label">
							<g:if test="${thidrFactorInstance?.branch}">${thidrFactorInstance?.branch.name}</g:if>
							<g:else>全省</g:else>
						</span>
					
				</li>
			
				<g:if test="${thidrFactorInstance?.influncedAt}">
				<li class="fieldcontain">
					<span id="influncedAt-label" class="property-label"><g:message code="thidrFactor.influncedAt.label" default="Influnced At" /></span>
					
						<span class="property-value" aria-labelledby="influncedAt-label"><g:formatDate date="${thidrFactorInstance?.influncedAt}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${thidrFactorInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="thidrFactor.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label">${thidrFactorInstance?.type?.name}</span>
					
				</li>
				</g:if>
			
				<g:if test="${thidrFactorInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="thidrFactor.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${thidrFactorInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${thidrFactorInstance?.id}" />
					<g:link class="edit" action="edit" id="${thidrFactorInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

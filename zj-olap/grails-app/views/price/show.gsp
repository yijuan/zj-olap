
<%@ page import="com.surelution.zjolap.Price" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'price.label', default: 'Price')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-price" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-price" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list price">
			
				<g:if test="${priceInstance?.from}">
				<li class="fieldcontain">
					<span id="from-label" class="property-label"><g:message code="price.from.label" default="From" /></span>
					
						<span class="property-value" aria-labelledby="from-label"><g:formatDate date="${priceInstance?.from}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${priceInstance?.price}">
				<li class="fieldcontain">
					<span id="price-label" class="property-label"><g:message code="price.price.label" default="Price" /></span>
					
						<span class="property-value" aria-labelledby="price-label"><g:fieldValue bean="${priceInstance}" field="price"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${priceInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="price.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label">${priceInstance?.type?.name}</span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${priceInstance?.id}" />
					<g:link class="edit" action="edit" id="${priceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>


<%@ page import="com.surelution.zjolap.CustomerVisting" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'customerVisting.label', default: 'CustomerVisting')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-customerVisting" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-customerVisting" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list customerVisting">
			
				<g:if test="${customerVistingInstance?.customer}">
				<li class="fieldcontain">
					<span id="customer-label" class="property-label"><g:message code="customerVisting.customer.label" default="Customer" /></span>
					
						<span class="property-value" aria-labelledby="customer-label">
							${customerVistingInstance?.customer?.name}</span>
					
				</li>
				</g:if>
			
				<g:if test="${customerVistingInstance?.sales}">
				<li class="fieldcontain">
					<span id="sales-label" class="property-label"><g:message code="customerVisting.sales.label" default="Sales" /></span>
					
						<span class="property-value" aria-labelledby="sales-label">
							${customerVistingInstance?.sales?.branch?.name}->${customerVistingInstance?.sales?.name}</span>
					
				</li>
				</g:if>
			
				<g:if test="${customerVistingInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="customerVisting.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label">
							${customerVistingInstance?.type?.name}
							</span>
					
				</li>
				</g:if>
			
				<g:if test="${customerVistingInstance?.vistingAt}">
				<li class="fieldcontain">
					<span id="vistingAt-label" class="property-label"><g:message code="customerVisting.vistingAt.label" default="Visting At" /></span>
					
						<span class="property-value" aria-labelledby="vistingAt-label"><g:formatDate date="${customerVistingInstance?.vistingAt}" format="yyyy-MM-dd"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${customerVistingInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="customerVisting.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${customerVistingInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${customerVistingInstance?.operator}">
				<li class="fieldcontain">
					<span id="operator-label" class="property-label">记录人</span>
					
						<span class="property-value" aria-labelledby="operator-label">
							${customerVistingInstance?.operator?.username}</span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${customerVistingInstance?.id}" />
					<g:link class="edit" action="edit" id="${customerVistingInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>


<%@ page import="com.surelution.zjolap.InitCustomerStock" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'initCustomerStock.label', default: 'InitCustomerStock')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-initCustomerStock" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-initCustomerStock" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list initCustomerStock">
			
				<g:if test="${initCustomerStockInstance?.customerBranch}">
				<li class="fieldcontain">
					<span id="customerBranch-label" class="property-label"><g:message code="initCustomerStock.customerBranch.label" default="Customer Branch" /></span>
					
						<span class="property-value" aria-labelledby="customerBranch-label"><g:link controller="customerBranch" action="show" id="${initCustomerStockInstance?.customerBranch?.id}">${initCustomerStockInstance?.customerBranch?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${initCustomerStockInstance?.date}">
				<li class="fieldcontain">
					<span id="date-label" class="property-label"><g:message code="initCustomerStock.date.label" default="Date" /></span>
					
						<span class="property-value" aria-labelledby="date-label"><g:link controller="timeByDay" action="show" id="${initCustomerStockInstance?.date?.id}">${initCustomerStockInstance?.date?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${initCustomerStockInstance?.gasType}">
				<li class="fieldcontain">
					<span id="gasType-label" class="property-label"><g:message code="initCustomerStock.gasType.label" default="Gas Type" /></span>
					
						<span class="property-value" aria-labelledby="gasType-label"><g:link controller="gasType" action="show" id="${initCustomerStockInstance?.gasType?.id}">${initCustomerStockInstance?.gasType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${initCustomerStockInstance?.stockQty}">
				<li class="fieldcontain">
					<span id="stockQty-label" class="property-label"><g:message code="initCustomerStock.stockQty.label" default="Stock Qty" /></span>
					
						<span class="property-value" aria-labelledby="stockQty-label"><g:fieldValue bean="${initCustomerStockInstance}" field="stockQty"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${initCustomerStockInstance?.id}" />
					<g:link class="edit" action="edit" id="${initCustomerStockInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>


<%@ page import="com.surelution.zjolap.InitCustomerStock" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'initCustomerStock.label', default: 'InitCustomerStock')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-initCustomerStock" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-initCustomerStock" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="initCustomerStock.customerBranch.label" default="Customer Branch" /></th>
					
						<th><g:message code="initCustomerStock.date.label" default="Date" /></th>
					
						<th><g:message code="initCustomerStock.gasType.label" default="Gas Type" /></th>
					
						<g:sortableColumn property="stockQty" title="${message(code: 'initCustomerStock.stockQty.label', default: 'Stock Qty')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${initCustomerStockInstanceList}" status="i" var="initCustomerStockInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${initCustomerStockInstance.id}">${fieldValue(bean: initCustomerStockInstance, field: "customerBranch")}</g:link></td>
					
						<td>${fieldValue(bean: initCustomerStockInstance, field: "date")}</td>
					
						<td>${fieldValue(bean: initCustomerStockInstance, field: "gasType")}</td>
					
						<td>${fieldValue(bean: initCustomerStockInstance, field: "stockQty")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${initCustomerStockInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

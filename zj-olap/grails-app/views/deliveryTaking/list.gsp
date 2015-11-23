
<%@ page import="com.surelution.zjolap.DeliveryTaking" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'deliveryTaking.label', default: 'DeliveryTaking')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-deliveryTaking" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="list-deliveryTaking" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="deliveryNo" title="${message(code: 'deliveryTaking.deliveryNo.label', default: 'Delivery No')}" />
					
						<th><g:message code="deliveryTaking.branch.label" default="Branch" /></th>
					
						<th><g:message code="deliveryTaking.customer.label" default="Customer" /></th>

						<th><g:message code="deliveryTaking.upload.label" default="Upload" /></th>
					
						<th><g:message code="deliveryTaking.gasType.label" default="Gas Type" /></th>
					
						<g:sortableColumn property="quantity" title="${message(code: 'deliveryTaking.quantity.label', default: 'Quantity')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${deliveryTakingInstanceList}" status="i" var="deliveryTakingInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${deliveryTakingInstance.id}">${deliveryTakingInstance.deliveryNo}</g:link></td>
					
						<td>${deliveryTakingInstance.branch.name}</td>
					
						<td>${fieldValue(bean: deliveryTakingInstance, field: "customer.name")}</td>
					
						<td>${deliveryTakingInstance.upload.uploadedAt}</td>
					
						<td>${fieldValue(bean: deliveryTakingInstance, field: "gasType.name")}</td>
					
						<td>${fieldValue(bean: deliveryTakingInstance, field: "quantity")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="paginateButtons">
				<g:paginate total="${deliveryTakingInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

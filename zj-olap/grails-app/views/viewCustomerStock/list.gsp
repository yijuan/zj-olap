
<%@ page import="com.surelution.zjolap.ViewCustomerStock" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-viewCustomerStock" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-viewCustomerStock" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="branchId" title="${message(code: 'viewCustomerStock.branchId.label', default: 'Branch Id')}" />
					
						<g:sortableColumn property="cuseromBranchId" title="${message(code: 'viewCustomerStock.cuseromBranchId.label', default: 'Cuserom Branch Id')}" />
					
						<g:sortableColumn property="dateId" title="${message(code: 'viewCustomerStock.dateId.label', default: 'Date Id')}" />
					
						<g:sortableColumn property="gasTypeId" title="${message(code: 'viewCustomerStock.gasTypeId.label', default: 'Gas Type Id')}" />
					
						<g:sortableColumn property="initQty" title="${message(code: 'viewCustomerStock.initQty.label', default: 'Init Qty')}" />
					
						<g:sortableColumn property="sellAllQty" title="${message(code: 'viewCustomerStock.sellAllQty.label', default: 'Sell All Qty')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${viewCustomerStockInstanceList}" status="i" var="viewCustomerStockInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${viewCustomerStockInstance.id}">${fieldValue(bean: viewCustomerStockInstance, field: "branchId")}</g:link></td>
					
						<td>${fieldValue(bean: viewCustomerStockInstance, field: "cuseromBranchId")}</td>
					
						<td>${fieldValue(bean: viewCustomerStockInstance, field: "dateId")}</td>
					
						<td>${fieldValue(bean: viewCustomerStockInstance, field: "gasTypeId")}</td>
					
						<td>${fieldValue(bean: viewCustomerStockInstance, field: "initQty")}</td>
					
						<td>${fieldValue(bean: viewCustomerStockInstance, field: "sellAllQty")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${viewCustomerStockInstanceTotal}" />
			</div>
		</div>
	</body>
</html>


<%@ page import="com.surelution.zjolap.ViewCustomerStock" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-viewCustomerStock" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-viewCustomerStock" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list viewCustomerStock">
			
				<g:if test="${viewCustomerStockInstance?.branchId}">
				<li class="fieldcontain">
					<span id="branchId-label" class="property-label"><g:message code="viewCustomerStock.branchId.label" default="Branch Id" /></span>
					
						<span class="property-value" aria-labelledby="branchId-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="branchId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${viewCustomerStockInstance?.cuseromBranchId}">
				<li class="fieldcontain">
					<span id="cuseromBranchId-label" class="property-label"><g:message code="viewCustomerStock.cuseromBranchId.label" default="Cuserom Branch Id" /></span>
					
						<span class="property-value" aria-labelledby="cuseromBranchId-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="cuseromBranchId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${viewCustomerStockInstance?.dateId}">
				<li class="fieldcontain">
					<span id="dateId-label" class="property-label"><g:message code="viewCustomerStock.dateId.label" default="Date Id" /></span>
					
						<span class="property-value" aria-labelledby="dateId-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="dateId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${viewCustomerStockInstance?.gasTypeId}">
				<li class="fieldcontain">
					<span id="gasTypeId-label" class="property-label"><g:message code="viewCustomerStock.gasTypeId.label" default="Gas Type Id" /></span>
					
						<span class="property-value" aria-labelledby="gasTypeId-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="gasTypeId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${viewCustomerStockInstance?.initQty}">
				<li class="fieldcontain">
					<span id="initQty-label" class="property-label"><g:message code="viewCustomerStock.initQty.label" default="Init Qty" /></span>
					
						<span class="property-value" aria-labelledby="initQty-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="initQty"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${viewCustomerStockInstance?.sellAllQty}">
				<li class="fieldcontain">
					<span id="sellAllQty-label" class="property-label"><g:message code="viewCustomerStock.sellAllQty.label" default="Sell All Qty" /></span>
					
						<span class="property-value" aria-labelledby="sellAllQty-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="sellAllQty"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${viewCustomerStockInstance?.sellQty}">
				<li class="fieldcontain">
					<span id="sellQty-label" class="property-label"><g:message code="viewCustomerStock.sellQty.label" default="Sell Qty" /></span>
					
						<span class="property-value" aria-labelledby="sellQty-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="sellQty"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${viewCustomerStockInstance?.soAllQty}">
				<li class="fieldcontain">
					<span id="soAllQty-label" class="property-label"><g:message code="viewCustomerStock.soAllQty.label" default="So All Qty" /></span>
					
						<span class="property-value" aria-labelledby="soAllQty-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="soAllQty"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${viewCustomerStockInstance?.soQty}">
				<li class="fieldcontain">
					<span id="soQty-label" class="property-label"><g:message code="viewCustomerStock.soQty.label" default="So Qty" /></span>
					
						<span class="property-value" aria-labelledby="soQty-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="soQty"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${viewCustomerStockInstance?.strDate}">
				<li class="fieldcontain">
					<span id="strDate-label" class="property-label"><g:message code="viewCustomerStock.strDate.label" default="Str Date" /></span>
					
						<span class="property-value" aria-labelledby="strDate-label"><g:fieldValue bean="${viewCustomerStockInstance}" field="strDate"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${viewCustomerStockInstance?.id}" />
					<g:link class="edit" action="edit" id="${viewCustomerStockInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

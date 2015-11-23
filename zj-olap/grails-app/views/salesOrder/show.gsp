
<%@ page import="com.surelution.zjolap.SalesOrder" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'salesOrder.label', default: 'SalesOrder')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-salesOrder" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="show-salesOrder" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="dialog">
				<table>
	                    <tbody>
	                    	<tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.id.label" default="Id" /></td>
	                            
	                            <td valign="top" class="value">${salesOrderInstance?.id}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.branch.label" default="Branch" /></td>
	                            
	                            <td valign="top" class="value">${salesOrderInstance?.branch?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.customer.label" default="Customer" /></td>
	                            
	                            <td valign="top" class="value">${salesOrderInstance?.customer?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.gasType.label" default="Gas Type" /></td>
	                            
	                            <td valign="top" class="value">${salesOrderInstance?.gasType?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.listUnitPrice.label" default="List Unit Price" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${salesOrderInstance}" field="listUnitPrice"/></td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.orderFormNo.label" default="Order Form No" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${salesOrderInstance}" field="orderFormNo"/></td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.purchasingPrice.label" default="Purchasing Price" /></td>
	                            
	                            <td valign="top" class="value">${salesOrderInstance.quantity * salesOrderInstance.purchasingUnitPrice }</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.purchasingPriceRate.label" default="Purchasing Price Rate" /></td>
	                            
	                            <td valign="top" class="value"><g:formatNumber number="${salesOrderInstance.purchasingUnitPrice/salesOrderInstance.listUnitPrice * 100 }" maxFractionDigits="4"/>%</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.purchasingUnitPrice.label" default="Purchasing Unit Price" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${salesOrderInstance}" field="purchasingUnitPrice"/></td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.quantity.label" default="Quantity" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${salesOrderInstance}" field="quantity"/></td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.sales.label" default="Sales" /></td>
	                            
	                            <td valign="top" class="value">${salesOrderInstance?.sales?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.salingAt.label" default="Saling At" /></td>
	                            
	                            <td valign="top" class="value"><g:formatDate date="${salesOrderInstance?.salingAt}"  format="yyyy-MM-dd"/></td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.salingtype.label" default="Salingtype" /></td>
	                            
	                            <td valign="top" class="value">${salesOrderInstance?.salingtype?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="salesOrder.timeByDay.label" default="Time By Day" /></td>
	                            
	                            <td valign="top" class="value">${salesOrderInstance?.month?.year}-${salesOrderInstance?.month?.month}</td>
	                            
	                        </tr>
	                	</tbody>
	                </table>
			</div>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${salesOrderInstance?.id}" />
					<g:actionSubmit class="edit" action="edit" value="${message(code:'default.button.edit.label', default:'Edit')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

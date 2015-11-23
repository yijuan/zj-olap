
<%@ page import="com.surelution.zjolap.DeliveryTaking" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'deliveryTaking.label', default: 'DeliveryTaking')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-deliveryTaking" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="show-deliveryTaking" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="dialog">
				<table>
	                    <tbody>
	                    	<tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.id.label" default="Id" /></td>
	                            
	                            <td valign="top" class="value">${deliveryTakingInstance?.id}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.upload.label" default="Upload" /></td>
	                            
	                            <td valign="top" class="value">${deliveryTakingInstance?.upload?.encodeAsHTML()}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.customer.label" default="Customer" /></td>
	                            
	                            <td valign="top" class="value">${deliveryTakingInstance?.customer?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.branch.label" default="Branch" /></td>
	                            
	                            <td valign="top" class="value">${deliveryTakingInstance?.branch?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.deliveryNo.label" default="Delivery No" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${deliveryTakingInstance}" field="deliveryNo"/></td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.gasType.label" default="Gas Type" /></td>
	                            
	                            <td valign="top" class="value">${deliveryTakingInstance?.gasType?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.takingAt.label" default="Taking At" /></td>
	                            
	                            <td valign="top" class="value"><g:formatDate date="${deliveryTakingInstance?.takingAt}" /></td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.quantity.label" default="Quantity" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${deliveryTakingInstance}" field="quantity"/></td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.tankFarm.label" default="Tank Farm" /></td>
	                            
	                            <td valign="top" class="value">${deliveryTakingInstance?.tankFarm?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="deliveryTaking.timeByDay.label" default="Time By Day" /></td>
	                            
	                            <td valign="top" class="value"><g:link controller="timeByDay" action="show" id="${deliveryTakingInstance?.timeByDay?.id}">${deliveryTakingInstance?.timeByDay?.encodeAsHTML()}</g:link></td>
	                            
	                        </tr>
	                	</tbody>
	                </table>
			</div>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${deliveryTakingInstance?.id}" />
					<g:actionSubmit class="edit" action="edit" value="${message(code:'default.button.edit.label', default:'Edit')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

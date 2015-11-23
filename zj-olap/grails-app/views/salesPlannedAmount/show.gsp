
<%@ page import="com.surelution.zjolap.SalesPlannedAmount" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-salesPlannedAmount" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-salesPlannedAmount" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list salesPlannedAmount">
			
				<g:if test="${salesPlannedAmountInstance?.sales}">
				<li class="fieldcontain">
					<span id="sales-label" class="property-label"><g:message code="salesPlannedAmount.sales.label" default="Sales" /></span>
					
						<span class="property-value" aria-labelledby="sales-label">${salesPlannedAmountInstance?.sales?.branch?.name}->${salesPlannedAmountInstance?.sales?.name}</span>
					
				</li>
				</g:if>
			
				<g:if test="${salesPlannedAmountInstance?.amount}">
				<li class="fieldcontain">
					<span id="amount-label" class="property-label"><g:message code="salesPlannedAmount.amount.label" default="Amount" /></span>
					
						<span class="property-value" aria-labelledby="amount-label"><g:fieldValue bean="${salesPlannedAmountInstance}" field="amount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${salesPlannedAmountInstance?.month}">
				<li class="fieldcontain">
					<span id="month-label" class="property-label"><g:message code="salesPlannedAmount.month.label" default="Month" /></span>
					
						<span class="property-value" aria-labelledby="month-label"><g:formatDate date="${salesPlannedAmountInstance?.month}" format="yyyy-MM"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${salesPlannedAmountInstance?.id}" />
					<g:link class="edit" action="edit" id="${salesPlannedAmountInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

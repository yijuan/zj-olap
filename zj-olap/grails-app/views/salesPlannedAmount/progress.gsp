
<%@ page import="com.surelution.zjolap.SalesPlannedAmount" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-salesPlannedAmount" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-salesPlannedAmount" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:form action="progress" >
				<fieldset class="form">
				<div class="fieldcontain ${hasErrors(bean: salesPlannedAmountInstance, field: 'sales', 'error')} required">
					<label for="sales">
						<g:message code="salesPlannedAmount.sales.label" default="Sales" />
						<span class="required-indicator">*</span>
					</label>
					<g:select id="sales" name="sales.id" value="${sales?.id}" from="${salesList}" noSelection="['':'']" optionKey="id" optionValue="${{it.branch.name + "->" + it.name} }" class="many-to-one"/>
				</div>
				
				<div class="fieldcontain ${hasErrors(bean: salesPlannedAmountInstance, field: 'month', 'error')} required">
					<label for="month">
						<g:message code="salesPlannedAmount.month.label" default="Month" />
						<span class="required-indicator">*</span>
					</label>
					<g:datePicker name="month" precision="month"  value="${params.month}"  />
				</div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="search" value="查询" class="search"/>
				</fieldset>
			</g:form>
			<table>
				<thead>
					<tr>
						<th>${message(code: 'salesPlannedAmount.sales.label', default: 'Sales')}</th>
						<th>${message(code: 'salesPlannedAmount.month.label', default: 'Month')}</th>
						<th>${message(code: 'salesPlannedAmount.amount.label', default: 'Amount')}</th>
						<th>实际完成量</th>
						<th>完成率(%)</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${spa}" status="i" var="sp">
				<g:if test="${sp[0] && sp[1] }">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${sp[0].sales?.branch?.name}-&gt;${sp[0].sales?.name}</td>
					
						<td><g:formatDate date="${sp[0].month}" format="yyyy-MM"/></td>
					
						<td><g:formatNumber number="${sp[0].amount}"/></td>
					
						<td><g:formatNumber number="${sp[1]}" format="###.##"/></td>
					
						<td><g:formatNumber number="${sp[1]/sp[0].amount * 100}" format="###.##"/></td>
					
					</tr>
					</g:if>
				</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>

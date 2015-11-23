
<%@ page import="com.surelution.zjolap.TimeByDay" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'timeByDay.label', default: 'TimeByDay')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-timeByDay" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="list-timeByDay" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="date" title="${message(code: 'timeByDay.date.label', default: 'Date')}" />
					
						<g:sortableColumn property="day" title="${message(code: 'timeByDay.day.label', default: 'Day')}" />
					
						<g:sortableColumn property="month" title="${message(code: 'timeByDay.month.label', default: 'Month')}" />
					
						<g:sortableColumn property="quarter" title="${message(code: 'timeByDay.quarter.label', default: 'Quarter')}" />
					
						<g:sortableColumn property="year" title="${message(code: 'timeByDay.year.label', default: 'Year')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${timeByDayInstanceList}" status="i" var="timeByDayInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${timeByDayInstance.id}">${fieldValue(bean: timeByDayInstance, field: "date")}</g:link></td>
					
						<td>${fieldValue(bean: timeByDayInstance, field: "day")}</td>
					
						<td>${fieldValue(bean: timeByDayInstance, field: "month")}</td>
					
						<td>${fieldValue(bean: timeByDayInstance, field: "quarter")}</td>
					
						<td>${fieldValue(bean: timeByDayInstance, field: "year")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="paginateButtons">
				<g:paginate total="${timeByDayInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

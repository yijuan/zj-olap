
<%@ page import="com.surelution.zjolap.Industry" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'industry.label', default: 'Industry')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-industry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="list-industry" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="id" title="${message(code: 'industry.id.label', default: 'Id')}" />

						<g:sortableColumn property="name" title="${message(code: 'industry.name.label', default: 'Name')}" />
					
						<th><g:message code="industry.type.label" default="Type" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${industryInstanceList}" status="i" var="industryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:link action="show" id="${industryInstance.id}">${fieldValue(bean: industryInstance, field: "id")}</g:link></td>
					
						<td>${fieldValue(bean: industryInstance, field: "name")}</td>
					
						<td>${industryInstance.type.name}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="paginateButtons">
				<g:paginate total="${industryInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

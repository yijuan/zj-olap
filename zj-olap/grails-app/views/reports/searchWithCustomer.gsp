<%@ page import="com.surelution.zjolap.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>分客户类型经营分析报表</title>
    	<jqui:resources theme="darkness" />
		<r:require module="jquery-ui"/>
    	<script type="text/javascript">
        $(document).ready(function()
        {
          $("#dateFrom").datepicker({dateFormat: 'yy-mm-dd'});
          $("#dateTo").datepicker({dateFormat: 'yy-mm-dd'});
        })
    </script>
	</head>
	<body>
		<a href="#create-sales" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-sales" class="content scaffold-create" role="main">
			<h1>分客户类型经营分析报表</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${salesInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${salesInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<div>
			<g:form action="searchWithCustomer" >
				<fieldset class="form">
					<div class="fieldcontain">
						<label for="dateFrom">
							请选择日期
						</label>
						<g:textField name="dateFrom" id="dateFrom"/>
						<label for="dateTo">
							到
						</label>
						<g:textField name="dateTo" id="dateTo"/>
					</div>
					<div class="fieldcontain">
						<g:each in="${Category.list() }" var="category">
							<g:checkBox name="category" value="${category.name }" id="category_${category.id }"/><label for="category_${category.id }">${category.name }</label>&nbsp;&nbsp;&nbsp;
						</g:each>
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="search" value="${message(code: 'default.button.search.label', default: '查询')}" />
				</fieldset>
			</g:form>
			</div>
		<div id="report-content" class="content scaffold-list"  role="main" style="overflow: scroll;">
		<g:if test="${result }">
			<table>
				<thead>
					<tr>
						<g:each in="${result.header }" var="h">
							<th>${h}</th>
						</g:each>
					</tr>
				</thead>
				<g:each in="${result.result}" var="row" status="i">
					<tr class="${i%2==0?'odd':'even' }">
						<g:each in="${row}" var="cell">
							<td>
								${cell }
							</td>
						</g:each>
					</tr>
				</g:each>
			</table>
		</g:if>
		</div>
		</div>
	</body>
</html>

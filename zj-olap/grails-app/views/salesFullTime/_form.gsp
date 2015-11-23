<%@ page import="com.surelution.zjolap.SalesFullTime" %>



<div class="fieldcontain ${hasErrors(bean: salesFullTimeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="salesFullTime.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${salesFullTimeInstance?.name}"/>
</div>


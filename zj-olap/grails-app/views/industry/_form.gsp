<%@ page import="com.surelution.zjolap.Industry" %>



<div class="fieldcontain ${hasErrors(bean: industryInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="industry.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${industryInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: industryInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="industry.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="type" name="type.id" from="${com.surelution.zjolap.CustomerType.list()}" optionKey="id" required="" optionValue="name" value="${industryInstance?.type?.id}" class="many-to-one"/>
</div>


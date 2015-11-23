<%@ page import="com.surelution.zjolap.BigCase" %>



<div class="fieldcontain ${hasErrors(bean: bigCaseInstance, field: 'defaultOption', 'error')} ">
	<label for="defaultOption">
		<g:message code="bigCase.defaultOption.label" default="Default Option" />
		
	</label>
	<g:checkBox name="defaultOption" value="${bigCaseInstance?.defaultOption}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bigCaseInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="bigCase.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${bigCaseInstance?.name}"/>
</div>


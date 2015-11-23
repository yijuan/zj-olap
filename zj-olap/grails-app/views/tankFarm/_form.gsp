<%@ page import="com.surelution.zjolap.TankFarm" %>



<div class="fieldcontain ${hasErrors(bean: tankFarmInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="tankFarm.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${tankFarmInstance?.name}"/>
</div>


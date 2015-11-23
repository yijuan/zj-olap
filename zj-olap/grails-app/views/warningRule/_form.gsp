<%@ page import="com.surelution.zjolap.WarningRule" %>


 <sec:ifAnyGranted roles="ROLE_ADMIN">
<div class="fieldcontain ${hasErrors(bean: warningRuleInstance, field: 'branch', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="branch">
		<g:message code="warningRule.branch.label" default="Branch" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	      <g:select id="branch" name="branch.id" from="${com.surelution.zjolap.Branch.list()}" optionValue="name" optionKey="id" required="" value="${warningRuleInstance?.branch?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>
</sec:ifAnyGranted>

<div class="fieldcontain ${hasErrors(bean: warningRuleInstance, field: 'gasType', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="gasType">
		<g:message code="warningRule.gasType.label" default="Gas Type" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	      <g:select id="gasType" name="gasType.id" from="${com.surelution.zjolap.GasType.list()}"  optionValue="${{it.category.name + '>>' + it.name}}"  optionKey="id" required="" value="${warningRuleInstance?.gasType?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>

<div class="fieldcontain ${hasErrors(bean: warningRuleInstance, field: 'type', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="type">
		<g:message code="warningRule.type.label" default="Type" />		
	</label></dt>
	   <dd>
	      <div class="col-xs-10"> 
	      <g:select id="type" name="type" from="${com.surelution.zjolap.WarningRule.getTypes()}" optionValue="typeName" optionKey="type" required="" value="${warningRuleInstance?.type}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>

<div class="fieldcontain ${hasErrors(bean: warningRuleInstance, field: 'value', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="value">
		<g:message code="warningRule.value.label" default="Value" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10"> 
	      <g:field name="value" value="${fieldValue(bean: warningRuleInstance, field: 'value')}" required="" class="form-control"/>
	      </div>
	   </dd>
	</dl>
	
	
	
</div>


<%@ page import="com.surelution.zjolap.ThidrFactorType" %>



<div class="fieldcontain ${hasErrors(bean: thidrFactorTypeInstance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="thidrFactorType.name.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textField name="name" value="${thidrFactorTypeInstance?.name}" class="form-control" required=""/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: thidrFactorTypeInstance, field: 'description', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="description">
		<g:message code="thidrFactorType.description.label" default="Description" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textField name="description" value="${thidrFactorTypeInstance?.description}" class="form-control"/>
	      </div>
	   </dd>
	</dl>	
</div>


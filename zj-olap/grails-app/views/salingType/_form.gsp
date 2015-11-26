<%@ page import="com.surelution.zjolap.SalingType" %>



<div class="fieldcontain ${hasErrors(bean: salingTypeInstance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="salingType.name.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textField name="name" value="${salingTypeInstance?.name}" class="form-control" required=""/>
	      </div>
	   </dd>
	</dl>	
</div>


<%@ page import="com.surelution.zjolap.Category" %>



<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="category.name.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textField name="name" value="${categoryInstance?.name}" class="form-control" required=""/>
	      </div>
	   </dd>
	</dl>	
</div>


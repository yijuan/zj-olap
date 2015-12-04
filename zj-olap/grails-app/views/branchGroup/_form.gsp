<%@ page import="com.surelution.zjolap.BranchGroup" %>



<div class="fieldcontain ${hasErrors(bean: branchGroupInstance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="branchGroup.name.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	     <g:textField name="name" value="${branchGroupInstance?.name}" class="form-control" required=""/>
	      </div>
	   </dd>
	</dl>	
</div>


<%@ page import="com.surelution.zjolap.Branch" %>



<div class="fieldcontain ${hasErrors(bean: branchInstance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="branch.name.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	    <g:textField name="name" value="${branchInstance?.name}" class="form-control"/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: branchInstance, field: 'branchGroup', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="branchGroup">
		<g:message code="branch.branchGroup.label" default="Branch Group" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:select id="branchGroup" name="branchGroup.id" from="${com.surelution.zjolap.BranchGroup.list()}" optionKey="id" optionValue="name" value="${branchInstance?.branchGroup?.id}" class="many-to-one form-control" />
	      </div>
	   </dd>
	</dl>	
	
	
</div>


<%@page import="com.surelution.zjolap.SalesFullTime"%>
<%@ page import="com.surelution.zjolap.Sales" %>


 <sec:ifAnyGranted roles="ROLE_ADMIN">
<div class="fieldcontain ${hasErrors(bean: salesInstance, field: 'branch', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="branch">
		<g:message code="sales.branch.label" default="Branch" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
<g:select id="branch" name="branch.id" from="${com.surelution.zjolap.Branch.list()}" optionKey="id" optionValue="name" required="" value="${salesInstance?.branch?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>
</sec:ifAnyGranted>
<div class="fieldcontain ${hasErrors(bean: salesInstance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="sales.name.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textField name="name" value="${salesInstance?.name}" class="form-control" required=""/>
	      </div>
	   </dd>
	</dl>
</div>
<div class="fieldcontain ${hasErrors(bean: salesInstance, field: 'fullTime', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="fullTime">
		<g:message code="sales.fullTime.label" default="Full Time" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:select name="fullTime.id" from="${SalesFullTime.list() }" optionKey="id" optionValue="name" value="${salesInstance.fullTime?.id }" class="form-control"/>
	      </div>
	   </dd>
	</dl>

</div>


<%@page import="com.surelution.zjolap.Branch"%>
<%@ page import="com.surelution.zjolap.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="username">
		<g:message code="user.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textField name="username" required="" value="${userInstance?.username}" class="form-control" data-error="请输入用户名"/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="password">
		<g:message code="user.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textField name="password" class="form-control" required=""/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: userBranch, field: 'branch', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="branchId">
		<g:message code="userBranch.branch.label" default="Branch" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:select id="branchId" name="branch.id" from="${Branch.list() }" optionKey="id" optionValue="name" value="${userInstance?.branch?.id }" class="form-control"/>
	      </div>
	   </dd>
	</dl>	
</div>


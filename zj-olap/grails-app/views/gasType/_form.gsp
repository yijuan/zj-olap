<%@ page import="com.surelution.zjolap.GasType" %>



<div class="fieldcontain ${hasErrors(bean: gasTypeInstance, field: 'category', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="category">
		<g:message code="gasType.category.label" default="Category" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:select id="category" name="category.id" from="${com.surelution.zjolap.Category.list()}" optionKey="id" optionValue="name" required="" value="${gasTypeInstance?.category?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>		
</div>

<div class="fieldcontain ${hasErrors(bean: gasTypeInstance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="gasType.name.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textField name="name" value="${gasTypeInstance?.name}" class="form-control" required="" oninvalid="this.setCustomValidity('Please Enter valid email')"/>
	      </div>
	   </dd>
	</dl>		
</div>


<%@ page import="com.surelution.zjolap.CustomerType" %>


<div class="fieldcontain ${hasErrors(bean: customerTypeInstance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="customerType.name.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-3">
	  <g:textField name="name" value="${customerTypeInstance?.name}" class="many-to-one form-control" style="width:150px;" required=""/>
	      </div>
	   </dd>
	</dl>
</div>
<div class="fieldcontain ${hasErrors(bean: customerTypeInstance, field: 'isHasChild', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="isHasChild">
		<g:message code="isHasChild.label" default="isHasChild" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-3">
	 <g:checkBox name="isHasChild" value="${customerTypeInstance?.isHasChild}"  class="many-to-one"/>
	      </div>
	   </dd>
	</dl>
</div>
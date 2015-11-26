<%@ page import="com.surelution.zjolap.CustomerTypeLevel2" %>



<div class="fieldcontain ${hasErrors(bean: customerTypeLevel2Instance, field: 'level1', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="level1">
		<g:message code="customerType.label" default="Level1" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-11">
	      <g:select id="level1" name="level1.id" from="${com.surelution.zjolap.CustomerType.getListForUpdate()}" optionKey="id" required="" optionValue="name" value="${customerTypeLevel2Instance?.level1?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: customerTypeLevel2Instance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="customerTypeLevel2.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-11">
	      <g:textField name="name" value="${customerTypeLevel2Instance?.name}" class="many-to-one form-control" required=""/>
	      </div>
	   </dd>
	</dl>
</div>


<div class="fieldcontain ${hasErrors(bean: customerTypeLevel2Instance, field: 'isHasChild', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="isHasChild">
		<g:message code="isHasChild.label" default="isHasChild" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-11">
	      <g:checkBox name="isHasChild" value="${customerTypeLevel2Instance?.isHasChild}" />
	      </div>
	   </dd>
	</dl>
</div>




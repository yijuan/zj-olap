<%@page import="com.surelution.zjolap.Branch"%>
<%@ page import="com.surelution.zjolap.ThidrFactor" %>

<div class="fieldcontain ${hasErrors(bean: thidrFactorInstance, field: 'branch', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="branch">
		<g:message code="thidrFactor.branch.label" default="Branch" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:select name="branch.id" noSelection="['': '全省']" default="none" from="${Branch.list() }" optionKey="id" optionValue="name" value="${thidrFactorInstance?.branch}" class="form-control"/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: thidrFactorInstance, field: 'influncedAt', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="influncedAt">
		<g:message code="thidrFactor.influncedAt.label" default="Influnced At" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textField name="influncedAt" value="${thidrFactorInstance?.influncedAt?.format("yyyy-MM-dd")}" id="datetimepicker1" class="form-control"/>
	      <script type="text/javascript">
                $('#datetimepicker1').datetimepicker({
                	isRTL: false,
                	format: "yyyy-mm-dd",
                    autoclose:true,
                    minView: 'year',
                    language: 'zh-CN'
					});
        </script>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: thidrFactorInstance, field: 'type', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="type">
		<g:message code="thidrFactor.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:select id="type" name="type.id" from="${com.surelution.zjolap.ThidrFactorType.list()}" optionKey="id" optionValue="name" required="" value="${thidrFactorInstance?.type?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: thidrFactorInstance, field: 'description', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="description">
		<g:message code="thidrFactor.description.label" default="Description" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textArea name="description" value="${thidrFactorInstance?.description}" class="form-control"/>
	      </div>
	   </dd>
	</dl>	
	
	
</div>

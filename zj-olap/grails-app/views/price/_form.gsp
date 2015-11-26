<%@ page import="com.surelution.zjolap.Price" %>



<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'from', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="from">
		<g:message code="price.from.label" default="From" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-9">
	     <%--<g:datePicker name="from" precision="day"  value="${priceInstance?.from}" class="form-control" />--%>
	       <g:textField name="from" value="${priceInstance?.from?.format("yyyy-MM-dd hh:mm")}" id='datetimepicker' class="form-control"/>
						<script type="text/javascript">
                $('#datetimepicker').datetimepicker({
                	isRTL: false,
                    format: 'yyyy-mm-dd hh:ii',
                    autoclose:true,                 
                    language: 'zh-CN'
					});
        </script>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'price', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="price">
		<g:message code="price.price.label" default="Price" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-9">
	      <g:field name="price" type="number" value="${priceInstance?.price}" required="" class="form-control"/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'type', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="type">
		<g:message code="price.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-9">
	      <g:select id="type" name="type.id" from="${com.surelution.zjolap.GasType.list()}" optionKey="id" optionValue="name" required="" value="${priceInstance?.type?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>	
</div>


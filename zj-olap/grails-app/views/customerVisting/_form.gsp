<%@page import="com.surelution.zjolap.Sales"%>
<%@page import="org.h2.command.ddl.CreateLinkedTable"%>
<%@page import="com.surelution.zjolap.CustomerVisting" %>


<script type="text/javascript">
    $(document).ready(function(){  	
    	$("#comauto1").autocomplete({		
    		  source: function(request, response){
    		   $.ajax({
    		    url: "${createLink(controller:'customerVisting', action:'coustomerlist')}", // remote datasource
    		    data: request,
    		    success: function(data){
    		     response(data); // set the response
    		    },
    		   
    		    error: function(){ // handle server errors
    		     $.jGrowl("Unable to retrieve Companies", {
    		      theme: 'ui-state-error ui-corner-all'   
    		     });
    		    }
    		   });
    		  },
    		 
    		 minLength: 2, // triggered only after minimum 2 characters have been entered.
    		  select: function(event, ui) { // event handler when user selects a company from the list.
    		   $("#customer\\.id").val(ui.item.id); // update the hidden field.
    		  }
    		 
    		 });
        })

</script>

<style type="text/css">
  .ui-autocomplete {
    z-index: 5000;
} 
</style>

<div class="fieldcontain ${hasErrors(bean: customerVistingInstance, field: 'customer', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="customer">
		<g:message code="customerVisting.customer.label" default="Customer" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">      
	         <g:textField id="comauto1" name="comauto" class="form-control" value="${customerVistingInstance?.customer?.name }" autocomplete="on"/>     
             <g:hiddenField name="customer.id"/>
	      </div>
	   </dd>
	</dl>		
</div>

<div class="fieldcontain ${hasErrors(bean: customerVistingInstance, field: 'type', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="type">
		<g:message code="customerVisting.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	     <g:select id="type" name="type.id" from="${com.surelution.zjolap.CustomerVistingType.list()}" optionKey="id" optionValue="name" required="" value="${customerVistingInstance?.type?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>

<div class="fieldcontain ${hasErrors(bean: customerVistingInstance, field: 'sales', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="sales">
		<g:message code="customerVisting.sales.label" default="Sales" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:select id="sales" name="sales.id" from="${sales?sales:Sales.list()}" optionKey="id" optionValue="${{ (it.branch?it.branch.name:"") + "->" + it.name} }" required="" value="${customerVistingInstance?.sales?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>

<div class="fieldcontain ${hasErrors(bean: customerVistingInstance, field: 'vistingAt', 'error')} required">
	<dl class="dl-horizontal">
	   <dt>
	<label for="vistingAt">
		<g:message code="customerVisting.vistingAt.label" default="Visting At" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	     <g:textField name="vistingAt" precision="day"  value="${customerVistingInstance?.vistingAt}" id="datetimepicker" class="form-control"  />
	     <script type="text/javascript">
                $('#datetimepicker').datetimepicker({
                	isRTL: false,
                    format: 'yyyy.mm.dd hh:ii',
                    autoclose:true,
                    language: 'zh-CN'
					});
        </script>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: customerVistingInstance, field: 'description', 'error')} ">
	<dl class="dl-horizontal">
	   <dt>
	<label for="description">
		<g:message code="customerVisting.description.label" default="Description"/>	
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:textArea name="description" value="${customerVistingInstance?.description}" class="form-control"/>
	      </div>
	   </dd>
	</dl>
</div>


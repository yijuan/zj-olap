<%@ page import="com.surelution.zjolap.SalesOrder" %>
	
		<script type="text/javascript">
			  $(document).ready(function()
				     {
			          $("#branch_id").change(loadData);			          
			          loadData();
			        })
			        
			        
			        function loadData() {
			      
				         var branchId =  $("#branch_id").children('option:selected').val() +"";
				         $('#sales_id').empty();
				          if(branchId) {
				          $.ajax({
				        	     url:'${createLink(uri:'/salesOrder/findSales')}',
								dataType:"json",
								data:{branchId:branchId},
								success:function(data) {
									var varHtml ="";
			                         $.each(data, function(commentIndex, comment){
											var salesId = ${salesOrderInstance?.sales?.id}+"";
											if(comment.id ==salesId) 
											{
												varHtml +="<option  selected='selected'  value='"+ comment.id +"'>"+comment.name+"</option>";
											}else 
											{
												varHtml +="<option value='"+ comment.id +"'>"+comment.name+"</option>";
											}
				                         });
									// $('#fmClosed').html(closeHtml);
									$('#sales_id').html(varHtml);
								}
					          });
				          }
			          }
				

		</script>



<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'branch', 'error')} ">
<sec:ifAnyGranted roles="ROLE_ADMIN">
	<dl class="dl-horizontal">
	   <dt><label for="branch">
		<g:message code="salesOrder.branch.label" default="Branch" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	    <g:select id="branch_id" name="branch.id" from="${com.surelution.zjolap.Branch.list()}" optionKey="id" optionValue="name" required="" value="${salesOrderInstance?.branch?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
	</sec:ifAnyGranted>	
</div>

<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'customer', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="customer">
		<g:message code="salesOrder.customer.label" default="Customer" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	    <g:select id="customer" name="customer.id" from="${com.surelution.zjolap.Customer.findAllByStatus('ABLE')}" optionKey="id" optionValue="name" required="" value="${salesOrderInstance?.customer?.id}" class=" form-control"/>
	     <%--<g:textField name="customer" value="${salesOrderInstance?.customer?.name }"/>--%>
	      </div>
	   </dd>
	</dl>
</div>


<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'gasType', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="gasType">
		<g:message code="salesOrder.gasType.label" default="Gas Type" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	   <g:select id="gasType" name="gasType.id" from="${com.surelution.zjolap.GasType.list()}" optionKey="id" optionValue="name" required="" value="${salesOrderInstance?.gasType?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>



<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'listUnitPrice', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="listUnitPrice">
		<g:message code="salesOrder.listUnitPrice.label" default="List Unit Price" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	   <g:field name="listUnitPrice" value="${fieldValue(bean: salesOrderInstance, field: 'listUnitPrice')}" required="" class="form-control"/>
	      </div>
	   </dd>
	</dl>
</div>


<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'orderFormNo', 'error')}">
	<dl class="dl-horizontal">
	   <dt><label for="orderFormNo">
		<g:message code="salesOrder.orderFormNo.label" default="Order Form No" />		
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	  <g:textField name="orderFormNo" value="${salesOrderInstance?.orderFormNo}" class="form-control"/>
	      </div>
	   </dd>
	</dl>
</div>


  <g:if test="${salesOrderInstance.quantity && salesOrderInstance.purchasingUnitPrice}">
<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'purchasingPrice', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="purchasingPrice">
		<g:message code="salesOrder.purchasingPrice.label" default="Purchasing Price" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	
    	 ${salesOrderInstance.quantity * salesOrderInstance.purchasingUnitPrice }
   
	      </div>
	   </dd>
	</dl>
</div>
 </g:if>

<g:if test="${salesOrderInstance.purchasingUnitPrice && salesOrderInstance.listUnitPrice}">
<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'purchasingPriceRate', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="purchasingPriceRate">
		<g:message code="salesOrder.purchasingPriceRate.label" default="Purchasing Price Rate" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	  
	<g:formatNumber number="${salesOrderInstance.purchasingUnitPrice/salesOrderInstance.listUnitPrice * 100 }" maxFractionDigits="4"/>%
	  
	      </div>
	   </dd>
	</dl>
</div>
</g:if>



<div class="fieldcontain ${hasErrors(bean: stationInstance, field: 'purchasingUnitPrice', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="purchasingUnitPrice">
		<g:message code="salesOrder.purchasingUnitPrice.label" default="Purchasing Unit Price" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	  <g:field name="purchasingUnitPrice" value="${fieldValue(bean: salesOrderInstance, field: 'purchasingUnitPrice')}" required="" class="form-control"/>
	      </div>
	   </dd>
	</dl>
</div>



<div class="fieldcontain ${hasErrors(bean: stationInstance, field: 'quantity', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="quantity">
		<g:message code="salesOrder.quantity.label" default="Quantity" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	 <g:field name="quantity" value="${fieldValue(bean: salesOrderInstance, field: 'quantity')}" required="" class="form-control"/>
	      </div>
	   </dd>
	</dl>
</div>


<div class="fieldcontain ${hasErrors(bean: stationInstance, field: 'sales', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="sales">
		<g:message code="salesOrder.sales.label" default="Sales" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	 <select id="sales_id" name="sales.id" class="form-control">
							
	 </select>
	      </div>
	   </dd>
	</dl>
</div>


<div class="fieldcontain ${hasErrors(bean: stationInstance, field: 'salingAt', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="salingAt">
		<g:message code="salesOrder.salingAt.label" default="Saling At" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	 <g:datePicker name="salingAt" precision="day"  value="${salesOrderInstance?.salingAt}" class="form-control" />
	      </div>
	   </dd>
	</dl>
</div>




<div class="fieldcontain ${hasErrors(bean: stationInstance, field: 'salingtype', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="salingtype">
		<g:message code="salesOrder.salingtype.label" default="Salingtype" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	<g:select id="salingtype" name="salingtype.id" from="${com.surelution.zjolap.SalingType.list()}" optionKey="id" optionValue="name" required="" value="${salesOrderInstance?.salingtype?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>


<div class="fieldcontain ${hasErrors(bean: stationInstance, field: 'timeByDay', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="timeByDay">
		<g:message code="salesOrder.month.label" default="Time By Day" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-12" style="margin-left:-20px;">
	     <div class="col-xs-1">年:</div> 
	  <div class="col-xs-4">  <g:select name="financialMonth_Year" from="${['2013','2014','2015','2016','2017']}" required="" value="${salesOrderInstance?.month?.year}" class="many-to-one form-control" style="width:90px;"/></div>
    <div class="col-xs-1">  月: </div> <div class="col-xs-5"><g:select name="financialMonth_Month" from="${['1','2','3','4','5','6','7','8','9','10','11','12']}" required="" value="${salesOrderInstance?.month?.month}" class="many-to-one form-control" style="width:80px;"/></div>
	      </div>
	   </dd>
	</dl>
</div>




<%@ page import="com.surelution.zjolap.CustomerStock" %>



<div class="fieldcontain ${hasErrors(bean: customerStockInstance, field: 'customerBranch', 'error')} required">
 <sec:ifAnyGranted roles="ROLE_ADMIN">
 <dl class="dl-horizontal">
	   <dt><label for="branch">
		<g:message code="salesOrder.branch.label" default="Branch" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	   <g:select id="branch" name="branchId" from="${com.surelution.zjolap.Branch.list()}" optionKey="id" optionValue="name" required="" value="${customerStockInstance?.customerBranch?.branch?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
	</sec:ifAnyGranted>
</div>


<div class="fieldcontain ${hasErrors(bean: customerStockInstance, field: 'customerBranch', 'error')} required">

 <dl class="dl-horizontal">
	   <dt><label for="customer">
		<g:message code="salesOrder.customer.label" default="Customer" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:select id="customer" name="customerId" from="${com.surelution.zjolap.Customer.findAllByStatus('ABLE')}" optionKey="id" optionValue="name" required="" value="${customerStockInstance?.customerBranch?.customer?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>


<div class="fieldcontain ${hasErrors(bean: customerStockInstance, field: 'date', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="date">
		<g:message code="customerStock.cdate.label" default="Date" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:datePicker name="cdate" precision="day"  value="${customerStockInstance?.cdate}" />
	      </div>
	   </dd>
	</dl>
</div>


<div class="fieldcontain ${hasErrors(bean: customerStockInstance, field: 'gasType', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="gasType">
		<g:message code="customerStock.gasType.label" default="Gas Type" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:select id="gasType" name="gasType.id" from="${com.surelution.zjolap.GasType.list()}" optionKey="id"  optionValue="${{it.category.name + '>>' + it.name}}"  required="" value="${customerStockInstance?.gasType?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>
</div>

<div class="fieldcontain ${hasErrors(bean: customerStockInstance, field: 'stockQty', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="stockQty">
		<g:message code="customerStock.stockQty.label" default="Stock Qty" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	      <g:field name="stockQty" value="${fieldValue(bean: customerStockInstance, field: 'stockQty')}" required="" class="form-control"/>
	      </div>
	   </dd>
	</dl>
</div>

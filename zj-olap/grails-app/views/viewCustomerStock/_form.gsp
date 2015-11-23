<%@ page import="com.surelution.zjolap.ViewCustomerStock" %>



<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'branchId', 'error')} required">
	<label for="branchId">
		<g:message code="viewCustomerStock.branchId.label" default="Branch Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="branchId" type="number" value="${viewCustomerStockInstance.branchId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'cuseromBranchId', 'error')} required">
	<label for="cuseromBranchId">
		<g:message code="viewCustomerStock.cuseromBranchId.label" default="Cuserom Branch Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cuseromBranchId" type="number" value="${viewCustomerStockInstance.cuseromBranchId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'dateId', 'error')} required">
	<label for="dateId">
		<g:message code="viewCustomerStock.dateId.label" default="Date Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="dateId" type="number" value="${viewCustomerStockInstance.dateId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'gasTypeId', 'error')} required">
	<label for="gasTypeId">
		<g:message code="viewCustomerStock.gasTypeId.label" default="Gas Type Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="gasTypeId" type="number" value="${viewCustomerStockInstance.gasTypeId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'initQty', 'error')} required">
	<label for="initQty">
		<g:message code="viewCustomerStock.initQty.label" default="Init Qty" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="initQty" value="${fieldValue(bean: viewCustomerStockInstance, field: 'initQty')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'sellAllQty', 'error')} required">
	<label for="sellAllQty">
		<g:message code="viewCustomerStock.sellAllQty.label" default="Sell All Qty" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="sellAllQty" value="${fieldValue(bean: viewCustomerStockInstance, field: 'sellAllQty')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'sellQty', 'error')} required">
	<label for="sellQty">
		<g:message code="viewCustomerStock.sellQty.label" default="Sell Qty" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="sellQty" value="${fieldValue(bean: viewCustomerStockInstance, field: 'sellQty')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'soAllQty', 'error')} required">
	<label for="soAllQty">
		<g:message code="viewCustomerStock.soAllQty.label" default="So All Qty" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="soAllQty" value="${fieldValue(bean: viewCustomerStockInstance, field: 'soAllQty')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'soQty', 'error')} required">
	<label for="soQty">
		<g:message code="viewCustomerStock.soQty.label" default="So Qty" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="soQty" value="${fieldValue(bean: viewCustomerStockInstance, field: 'soQty')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: viewCustomerStockInstance, field: 'strDate', 'error')} ">
	<label for="strDate">
		<g:message code="viewCustomerStock.strDate.label" default="Str Date" />
		
	</label>
	<g:textField name="strDate" value="${viewCustomerStockInstance?.strDate}"/>
</div>


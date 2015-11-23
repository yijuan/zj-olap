<%@ page import="com.surelution.zjolap.InitCustomerStock" %>



<div class="fieldcontain ${hasErrors(bean: initCustomerStockInstance, field: 'customerBranch', 'error')} required">
	<label for="customerBranch">
		<g:message code="initCustomerStock.customerBranch.label" default="Customer Branch" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="customerBranch" name="customerBranch.id" from="${com.surelution.zjolap.CustomerBranch.list()}" optionKey="id" required="" value="${initCustomerStockInstance?.customerBranch?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: initCustomerStockInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="initCustomerStock.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="date" name="date.id" from="${com.surelution.zjolap.TimeByDay.list()}" optionKey="id" required="" value="${initCustomerStockInstance?.date?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: initCustomerStockInstance, field: 'gasType', 'error')} required">
	<label for="gasType">
		<g:message code="initCustomerStock.gasType.label" default="Gas Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="gasType" name="gasType.id" from="${com.surelution.zjolap.GasType.list()}" optionKey="id" required="" value="${initCustomerStockInstance?.gasType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: initCustomerStockInstance, field: 'stockQty', 'error')} required">
	<label for="stockQty">
		<g:message code="initCustomerStock.stockQty.label" default="Stock Qty" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="stockQty" value="${fieldValue(bean: initCustomerStockInstance, field: 'stockQty')}" required=""/>
</div>


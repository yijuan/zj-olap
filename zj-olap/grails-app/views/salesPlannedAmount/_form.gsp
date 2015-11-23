<%@ page import="com.surelution.zjolap.SalesPlannedAmount" %>



<div class="fieldcontain ${hasErrors(bean: salesPlannedAmountInstance, field: 'sales', 'error')} required">
	<label for="sales">
		<g:message code="salesPlannedAmount.sales.label" default="Sales" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="sales" name="sales.id" from="${salesList}" optionKey="id" optionValue="${{it.branch.name + "->" + it.name} }" required="" value="${salesPlannedAmountInstance?.sales?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: salesPlannedAmountInstance, field: 'amount', 'error')} required">
	<label for="amount">
		<g:message code="salesPlannedAmount.amount.label" default="Amount" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="amount" value="${fieldValue(bean: salesPlannedAmountInstance, field: 'amount')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: salesPlannedAmountInstance, field: 'month', 'error')} required">
	<label for="month">
		<g:message code="salesPlannedAmount.month.label" default="Month" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="month" precision="month"  value="${salesPlannedAmountInstance?.month}"  />
</div>


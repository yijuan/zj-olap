<%@ page import="com.surelution.zjolap.DeliveryTaking" %>



<div class="fieldcontain ${hasErrors(bean: deliveryTakingInstance, field: 'upload', 'error')} ">
	<label for="upload">
		<g:message code="deliveryTaking.upload.label" default="Upload" />
		
	</label>
	<g:select id="upload" name="upload.id" from="${com.surelution.zjolap.DeliveryTakingUpload.list()}" optionKey="id" value="${deliveryTakingInstance?.upload?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deliveryTakingInstance, field: 'branch', 'error')} required">
	<label for="branch">
		<g:message code="deliveryTaking.branch.label" default="Branch" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="branch" name="branch.id" from="${com.surelution.zjolap.Branch.list()}" optionKey="id" optionValue="name" required="" value="${deliveryTakingInstance?.branch?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deliveryTakingInstance, field: 'customer', 'error')} required">
	<label for="customer">
		<g:message code="deliveryTaking.customer.label" default="Customer" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="customer" name="customer.id" from="${com.surelution.zjolap.Customer.list()}" optionKey="id" optionValue="name" required="" value="${deliveryTakingInstance?.customer?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deliveryTakingInstance, field: 'deliveryNo', 'error')} ">
	<label for="deliveryNo">
		<g:message code="deliveryTaking.deliveryNo.label" default="Delivery No" />
		
	</label>
	<g:textField name="deliveryNo" value="${deliveryTakingInstance?.deliveryNo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deliveryTakingInstance, field: 'gasType', 'error')} required">
	<label for="gasType">
		<g:message code="deliveryTaking.gasType.label" default="Gas Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="gasType" name="gasType.id" from="${com.surelution.zjolap.GasType.list()}" optionKey="id" optionValue="name" required="" value="${deliveryTakingInstance?.gasType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deliveryTakingInstance, field: 'quantity', 'error')} required">
	<label for="quantity">
		<g:message code="deliveryTaking.quantity.label" default="Quantity" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="quantity" value="${fieldValue(bean: deliveryTakingInstance, field: 'quantity')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: deliveryTakingInstance, field: 'takingAt', 'error')} required">
	<label for="takingAt">
		<g:message code="deliveryTaking.takingAt.label" default="Taking At" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="takingAt" precision="day"  value="${deliveryTakingInstance?.takingAt}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: deliveryTakingInstance, field: 'tankFarm', 'error')} required">
	<label for="tankFarm">
		<g:message code="deliveryTaking.tankFarm.label" default="Tank Farm" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tankFarm" name="tankFarm.id" from="${com.surelution.zjolap.TankFarm.list()}" optionKey="id" optionValue="name" required="" value="${deliveryTakingInstance?.tankFarm?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deliveryTakingInstance, field: 'timeByDay', 'error')} required">
	<label for="timeByDay">
		<g:message code="deliveryTaking.timeByDay.label" default="Time By Day" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="timeByDay" name="timeByDay.id" from="${com.surelution.zjolap.TimeByDay.list()}" optionKey="id"  required="" value="${deliveryTakingInstance?.timeByDay?.id}" class="many-to-one"/>
</div>


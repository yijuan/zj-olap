<%@ page import="com.surelution.zjolap.TimeByDay" %>



<div class="fieldcontain ${hasErrors(bean: timeByDayInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="timeByDay.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${timeByDayInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: timeByDayInstance, field: 'day', 'error')} required">
	<label for="day">
		<g:message code="timeByDay.day.label" default="Day" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="day" type="number" value="${timeByDayInstance.day}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: timeByDayInstance, field: 'month', 'error')} required">
	<label for="month">
		<g:message code="timeByDay.month.label" default="Month" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="month" type="number" value="${timeByDayInstance.month}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: timeByDayInstance, field: 'quarter', 'error')} required">
	<label for="quarter">
		<g:message code="timeByDay.quarter.label" default="Quarter" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="quarter" type="number" value="${timeByDayInstance.quarter}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: timeByDayInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="timeByDay.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="year" type="number" value="${timeByDayInstance.year}" required=""/>
</div>


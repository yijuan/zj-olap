<%@ page import="com.surelution.zjolap.StockExcelUpload" %>



<div class="fieldcontain ${hasErrors(bean: stockExcelUploadInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="stockExcelUpload.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="date" name="date.id" from="${com.surelution.zjolap.TimeByDay.list()}" optionKey="id" required="" value="${stockExcelUploadInstance?.date?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockExcelUploadInstance, field: 'deleted', 'error')} ">
	<label for="deleted">
		<g:message code="stockExcelUpload.deleted.label" default="Deleted" />
		
	</label>
	<g:checkBox name="deleted" value="${stockExcelUploadInstance?.deleted}" />
</div>

<div class="fieldcontain ${hasErrors(bean: stockExcelUploadInstance, field: 'filePath', 'error')} ">
	<label for="filePath">
		<g:message code="stockExcelUpload.filePath.label" default="File Path" />
		
	</label>
	<g:textField name="filePath" value="${stockExcelUploadInstance?.filePath}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockExcelUploadInstance, field: 'originalFileName', 'error')} ">
	<label for="originalFileName">
		<g:message code="stockExcelUpload.originalFileName.label" default="Original File Name" />
		
	</label>
	<g:textField name="originalFileName" value="${stockExcelUploadInstance?.originalFileName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockExcelUploadInstance, field: 'uploadedAt', 'error')} required">
	<label for="uploadedAt">
		<g:message code="stockExcelUpload.uploadedAt.label" default="Uploaded At" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="uploadedAt" precision="day"  value="${stockExcelUploadInstance?.uploadedAt}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: stockExcelUploadInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="stockExcelUpload.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${com.surelution.zjolap.User.list()}" optionKey="id" required="" value="${stockExcelUploadInstance?.user?.id}" class="many-to-one"/>
</div>


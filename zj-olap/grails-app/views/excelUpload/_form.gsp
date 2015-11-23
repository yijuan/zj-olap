<%@ page import="com.surelution.zjolap.ExcelUpload" %>



<div class="fieldcontain ${hasErrors(bean: excelUploadInstance, field: 'deleted', 'error')} ">
	<label for="deleted">
		<g:message code="excelUpload.deleted.label" default="Deleted" />
		
	</label>
	<g:checkBox name="deleted" value="${excelUploadInstance?.deleted}" />
</div>

<div class="fieldcontain ${hasErrors(bean: excelUploadInstance, field: 'filePath', 'error')} ">
	<label for="filePath">
		<g:message code="excelUpload.filePath.label" default="File Path" />
		
	</label>
	<g:textField name="filePath" value="${excelUploadInstance?.filePath}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: excelUploadInstance, field: 'originalFileName', 'error')} ">
	<label for="originalFileName">
		<g:message code="excelUpload.originalFileName.label" default="Original File Name" />
		
	</label>
	<g:textField name="originalFileName" value="${excelUploadInstance?.originalFileName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: excelUploadInstance, field: 'uploadedAt', 'error')} required">
	<label for="uploadedAt">
		<g:message code="excelUpload.uploadedAt.label" default="Uploaded At" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="uploadedAt" precision="day"  value="${excelUploadInstance?.uploadedAt}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: excelUploadInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="excelUpload.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${com.surelution.zjolap.User.list()}" optionKey="id" required="" value="${excelUploadInstance?.user?.id}" class="many-to-one"/>
</div>


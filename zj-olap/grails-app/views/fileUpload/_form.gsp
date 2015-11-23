<%@ page import="com.surelution.zjolap.FileUpload" %>



<div class="fieldcontain ${hasErrors(bean: fileUploadInstance, field: 'fromID', 'error')} ">
	<label for="fromID">
		<g:message code="fileUpload.fromID.label" default="From ID" />
		
	</label>
	<g:field name="fromID" type="number" value="${fileUploadInstance.fromID}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fileUploadInstance, field: 'fileName', 'error')} ">
	<label for="fileName">
		<g:message code="fileUpload.fileName.label" default="File Name" />
		
	</label>
	<g:textField name="fileName" value="${fileUploadInstance?.fileName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fileUploadInstance, field: 'fileUUID', 'error')} ">
	<label for="fileUUID">
		<g:message code="fileUpload.fileUUID.label" default="File UUID" />
		
	</label>
	<g:textField name="fileUUID" value="${fileUploadInstance?.fileUUID}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fileUploadInstance, field: 'fileUrl', 'error')} ">
	<label for="fileUrl">
		<g:message code="fileUpload.fileUrl.label" default="File Url" />
		
	</label>
	<g:textField name="fileUrl" value="${fileUploadInstance?.fileUrl}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fileUploadInstance, field: 'uploadDate', 'error')} required">
	<label for="uploadDate">
		<g:message code="fileUpload.uploadDate.label" default="Upload Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="uploadDate" precision="day"  value="${fileUploadInstance?.uploadDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: fileUploadInstance, field: 'useOpter', 'error')} ">
	<label for="useOpter">
		<g:message code="fileUpload.useOpter.label" default="Use Opter" />
		
	</label>
	<g:textField name="useOpter" value="${fileUploadInstance?.useOpter}"/>
</div>


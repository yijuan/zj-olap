<%@ page import="com.surelution.zjolap.Requestmap" %>



<div class="fieldcontain ${hasErrors(bean: requestmapInstance, field: 'url', 'error')} required">
	<label for="url">
		<g:message code="requestmap.url.label" default="Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="url" required="" value="${requestmapInstance?.url}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: requestmapInstance, field: 'configAttribute', 'error')} required">
	<label for="configAttribute">
		<g:message code="requestmap.configAttribute.label" default="Config Attribute" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="configAttribute" required="" value="${requestmapInstance?.configAttribute}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: requestmapInstance, field: 'httpMethod', 'error')} ">
	<label for="httpMethod">
		<g:message code="requestmap.httpMethod.label" default="Http Method" />
		
	</label>
	<g:select name="httpMethod" from="${org.springframework.http.HttpMethod?.values()}" keys="${org.springframework.http.HttpMethod.values()*.name()}" value="${requestmapInstance?.httpMethod?.name()}" noSelection="['': '']"/>
</div>


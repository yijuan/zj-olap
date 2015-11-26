<%@ page import="com.surelution.zjolap.Customer" %>
<!DOCTYPE html>
<html>
	<head>
	<%--<meta name="layout" content="jarvis">--%>
		<g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
	 <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">更新客户</h4>
        </div>
        
        <g:uploadForm method="post" >
		<div class="modal-body">
				<g:hiddenField name="id" value="${customerInstance?.id}" />
				<g:hiddenField name="version" value="${customerInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/> 				
				</fieldset>
		 </div>	
		 <div class="modal-footer">		
				<fieldset class="buttons">
				    <g:actionSubmit class="save btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="save btn btn-primary" action="updateD" value="临时保存" />
					<button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
				</fieldset>
		</div>
			</g:uploadForm>
        
	
	<%--<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					客户管理 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 客户</a></li>
					<li class="active">客户管理</li>
				</ol>
				</section>
	<section class="content">
	<div class="c1" style=" height: 30px; border-bottom: 1px solid #e5e5e5;margin-bottom:10px;">
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
			<g:link  target='_blank' class="create" controller="fileUpload" action="list" params="${[fromID:customerInstance?.id,useOpter:'CUSTOMER']}">编辑附件</g:link>				
           <span class="glyphicon glyphicon-folder-close" aria-hidden="true"></span>
           <g:link target='_blank' class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
          </div>
          
		<div id="edit-customer" class="content scaffold-edit">
			<h3><g:message code="default.edit.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${customerInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${customerInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:uploadForm method="post" >
				<g:hiddenField name="id" value="${customerInstance?.id}" />
				<g:hiddenField name="version" value="${customerInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/> 				
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="save btn btn-primary" action="updateD" value="临时保存" />
					<g:actionSubmit class="delete btn btn-primary" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:uploadForm>
		</div>
		</section>
		</div>
	--%></body>
</html>

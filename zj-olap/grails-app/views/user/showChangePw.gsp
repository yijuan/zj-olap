<%@ page import="com.surelution.zjolap.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title>修改密码</title>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					修改密码<small></small>
				</h1>
				<%--<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 修改密码</a></li>
					<li class="active"></li>
				</ol>
				--%></section>
				<g:if test="${flash.message}">
			<div class="message" role="status" style="color:red;font-weight:bold;">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${userInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${userInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
	<section class="content">
	<div class="box box-info"><%--
                <div class="box-header with-border">
                  <h4>修改密码</h4>
                </div>
                --%><div class="box-body">	
			<g:form method="post" >
				<g:hiddenField name="id" value="${userInstance?.id}" />
				<g:hiddenField name="version" value="${userInstance?.version}" />
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
						<dl class="dl-horizontal">
	   <dt><label for="password">
							原密码：
							<span class="required-indicator">*</span>
						</label></dt>
	   <dd>
	      <div class="col-xs-3">
	      <g:passwordField name="password" required="" class="form-control"/>
	      </div>
	   </dd>
	</dl>
				</div>
					<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'newPw1', 'error')} required">
						<dl class="dl-horizontal">
	   <dt><label for="pw1">
							新密码：
							<span class="required-indicator">*</span>
						</label></dt>
	   <dd>
	      <div class="col-xs-3">
	      <g:passwordField name="newPw1" required="" class="form-control"/>
	      </div>
	   </dd>
	</dl>
						
						
						
					</div>
					<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'newPw2', 'error')} required">
						<dl class="dl-horizontal">
	   <dt><label for="pw2">
							确认密码：
							<span class="required-indicator">*</span>
						</label></dt>
	   <dd>
	      <div class="col-xs-3">
	      <g:passwordField name="newPw2" required="" class="form-control"/>
	      </div>
	   </dd>
	</dl>
				</div>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save btn btn-primary" action="changePw" value="修改" />
				</fieldset>
			</g:form>
			</div>
			</div>
		</section>
		</div>
	</body>
</html>

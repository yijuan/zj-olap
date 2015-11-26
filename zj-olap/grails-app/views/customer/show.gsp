
<%@ page import="com.surelution.zjolap.Customer" %>
<%@ page import="com.surelution.zjolap.FileUpload" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	<div class="content-wrapper">
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
            <span class="glyphicon glyphicon-folder-close" aria-hidden="true"></span>
			<g:link class="list" action="list">客户列表</g:link>	
       </div>
			<h3><g:message code="default.show.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="dialog">
				<table class="table table-bordered  table-striped">
	                    <tbody>
	                    	<tr class="prop">
	                            <td valign="top" class="name"><g:message code="customer.id.label" default="Id" /></td>
	                            
	                            <td valign="top" class="value">${customerInstance?.id}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customer.name.label" default="name" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${customerInstance}" field="name"/></td>
	                            
	                        </tr>
	                         <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customer.tel.label" default="name" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${customerInstance}" field="tel"/></td>
	                            
	                        </tr>
	                        
	                         <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customer.address.label" default="name" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${customerInstance}" field="address"/></td>
	                            
	                        </tr>
	                     
	                        
	                         <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customer.license.label" default="license" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${customerInstance}" field="license"/></td>
	                        </tr>
	                         <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customer.licensePicUrl.label" default="license" /></td>
	                            
	                            <td valign="top" class="value">
	                            	<g:if test="${customerInstance?.licensePicUrl }"><img src="${resource(dir:'dynImage/file/')}${customerInstance?.licensePicUrl?.id }" width="200" height="200" class="img-thumbnail"/></g:if>
	                            	&nbsp;
	                            	</td>
	                        </tr>
	                        
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customer.customerType.label" default="customerType" /></td>
	                            
	                            <td valign="top" class="value">${customerInstance?.customerType?.name}</td>
	                        </tr>
	                	</tbody>
	        
	                </table>
	                
	                <table class="table table-bordered  table-striped">
				<thead>
					<tr>
					   <th>文件名</th>
	                   <th>时间</th>
	                   		     
					    <th>操作</th>
						
					</tr>
				</thead>
				<tbody>
				<g:each in="${files}" status="i" var="fileUploadInstance">
					<tr>
						<td>${fieldValue(bean: fileUploadInstance, field: "fileName")}</td>				
						<td><g:formatDate date="${fileUploadInstance.uploadDate}" /></td>				
						<td>
						    <g:form controller="fileUpload" >
							<g:hiddenField name="id" value="${fileUploadInstance?.id}" />
							<g:actionSubmit class="create btn btn-primary" action="downFile" value="下载"   />
					     </g:form>
						    
						 </td>
				</tr>
				</g:each>
				</tbody>
			</table>
	                
			</div>
			<g:if test="${editable}">
			<g:form>
							<fieldset class="buttons">
					<g:hiddenField name="id" value="${customerInstance?.id}" />
					<%--<g:actionSubmit value="${message(code:'default.button.edit.label', default:'Edit')}" class="btn btn-primary" action="edit"/>--%>
					
					
				   <g:if test="${customerInstance?.status =='ADD'}">
				   
                      </g:if>
                      <g:else>
                         <g:link class="edit btn btn-primary" action="edit" id="${customerInstance?.id}"  value="" data-toggle="modal" data-target="#myModal">${message(code:'default.button.edit.label', default:'Edit')}</g:link>
					<div class="modal fade" id="myModal" role="dialog">
                     <div class="modal-dialog">
                           <!-- Modal content-->
                           <div class="modal-content"> 
                           </div>
                          </div>
                      </div>	
                     
					<g:actionSubmit class="delete btn btn-primary" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				  
				 
				    <g:link  target='_blank' class="create btn btn-primary" controller="fileUpload" action="list" params="${[fromID:customerInstance?.id,useOpter:'CUSTOMER']}">编辑附件</g:link>
				 </g:else>
				</fieldset>
			</g:form>	
			</g:if>	
		</section>
		</div>
	</body>
</html>


<%@ page import="com.surelution.zjolap.FileUpload" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'fileUpload.label', default: 'FileUpload')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					编辑附件 <small></small>
				</h1>
				<ol class="breadcrumb">
					<%--<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> </a></li>
					<li class="active"></li>
				--%></ol>
				</section>
	<section class="content">
	    <div class="c1" style=" height: 30px; border-bottom: 1px solid #e5e5e5;margin-bottom:10px;">
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
			<g:link class="create" action="create" params="${[fromID:params.fromID,useOpter:params.useOpter]}" data-toggle="modal" data-target="#myModal"><g:message code="default.new.label" args="[entityName]" /></g:link>
			<!-- 新增台账的dialog -->	
			 <div class="modal fade" id="myModal" role="dialog">
                     <div class="modal-dialog">
                           <!-- Modal content-->
                           <div class="modal-content"> 
                           </div>
                          </div>
                      </div>		
          </div>
		<%--<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="create" action="create" params="${[fromID:params.fromID,useOpter:params.useOpter]}"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		--%><div id="list-fileUpload" class="content scaffold-list" role="main">
			<h4><g:message code="default.list.label" args="[entityName]" /></h4>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
					
					
						<%--<g:sortableColumn property="fileName" title="${message(code: 'fileUpload.fileName.label', default: 'File Name')}"  params="${params}"/>
					
						<g:sortableColumn property="fileUUID" title="${message(code: 'fileUpload.fileUUID.label', default: 'File UUID')}" params="${params}"/>
					
						<g:sortableColumn property="fileUrl" title="${message(code: 'fileUpload.fileUrl.label', default: 'File Url')}"  params="${params}"/>
					
						<g:sortableColumn property="uploadDate" title="${message(code: 'fileUpload.uploadDate.label', default: 'Upload Date')}" params="${params}"/>
					
						<g:sortableColumn property="useOpter" title="${message(code: 'fileUpload.useOpter.label', default: 'Use Opter')}" params="${params}"/>
					     
					    --%>
					   <th>文件名</th>
	                   <th>时间</th>
					   <th colspan="2">操作</th>
						
					</tr>
				</thead>
				<tbody>
				<g:each in="${fileUploadInstanceList}" status="i" var="fileUploadInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
					
						<td>${fieldValue(bean: fileUploadInstance, field: "fileName")}</td>
					
						<%--<td>${fieldValue(bean: fileUploadInstance, field: "fileUUID")}</td>
					
						<td>${fieldValue(bean: fileUploadInstance, field: "fileUrl")}</td>
					
						--%><td><g:formatDate date="${fileUploadInstance.uploadDate}" /></td><%--
					
						<td>${fieldValue(bean: fileUploadInstance, field: "useOpter")}</td>
						
						--%><td>
						    <g:form>
							<g:hiddenField name="id" value="${fileUploadInstance?.id}" />
							<g:actionSubmit class="create" action="downFile" value="下载"   />
					     </g:form>
						    
						 </td>
						<td>
						<g:form>
							<g:hiddenField name="id" value="${fileUploadInstance?.id}" />
							<g:actionSubmit class="create" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"  onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					     </g:form>
						</td>
						
						
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${fileUploadInstanceTotal}" params="${params}"/>
			</div>
		</div>
		</section>
		</div>
	</body>
</html>

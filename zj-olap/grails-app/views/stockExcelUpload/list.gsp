
<%@ page import="com.surelution.zjolap.StockExcelUpload" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'excelUpload.label', default: 'ExcelUpload')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					提货数据列表 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 提货数据上传</a></li>
					<li class="active">上传列表</li>
				</ol>
				</section>
	<section class="content">
		 <div class="c1" style=" height: 30px; border-bottom: 1px solid #e5e5e5;margin-bottom:10px;">
            <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
			<g:link class="create" action="initUploadExcel" controller="customerStock">新增提货数据Excel上传</g:link>
       </div>
		<div id="list-excelUpload" class="content scaffold-list" role="main">
			<h4><g:message code="default.list.label" args="[entityName]" /></h4>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>			
						<g:sortableColumn property="deleted" title="${message(code: 'excelUpload.deleted.label', default: 'Deleted')}" class="alink"/>
					
						<g:sortableColumn property="originalFileName" title="${message(code: 'excelUpload.originalFileName.label', default: 'Original File Name')}" class="alink"/>
					
						<g:sortableColumn property="uploadedAt" title="${message(code: 'excelUpload.uploadedAt.label', default: 'Uploaded At')}" class="alink"/>
						<g:sortableColumn property="user" title="${message(code: 'excelUpload.branch.label', default: 'Branch')}" class="alink" />
						<g:sortableColumn property="user" title="${message(code: 'excelUpload.user.label', default: 'User')}" class="alink"/>
						<th>下载</th>
						<sec:ifAnyGranted roles="ROLE_ADMIN">
						<th>删除（会删除账单）</th>
						</sec:ifAnyGranted>
					</tr>
				</thead>
				<tbody>
				<g:each in="${excelUploadInstanceList}" status="i" var="excelUploadInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:formatBoolean boolean="${excelUploadInstance.deleted}"/></td>
					
						<%--<td>${fieldValue(bean: excelUploadInstance, field: "filePath")}</td>
					
						--%><td>${fieldValue(bean: excelUploadInstance, field: "originalFileName")}</td>
					
						<td><g:formatDate date="${excelUploadInstance.uploadedAt}" /></td>
					
						<g:if test="${excelUploadInstance?.user?.branch}">
							<td>${excelUploadInstance?.user?.branch?.name} </td>
						</g:if>
						<g:else>
							<td>总公司</td>
						</g:else>
						<td>${excelUploadInstance?.user?.username} </td>
						<td>
						  <g:form controller="stockExcelUpload" >
							<g:hiddenField name="id" value="${excelUploadInstance?.id}" />
							<g:actionSubmit class="create" action="downFile" value="下载"   />
					     </g:form>
						</td>
						<sec:ifAnyGranted roles="ROLE_ADMIN">
					    <td>
					        <g:if test="${!excelUploadInstance.deleted}">
					          <g:form>
					         	 <g:hiddenField name="id" value="${excelUploadInstance?.id}" />
					         	 <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"    onclick="return confirm('确定删除？')"/>
					          </g:form>
					        </g:if>
					    </td>
					    </sec:ifAnyGranted>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div style="height: 50px;">
			<div class="list-page" id="paginateButtons">
				<g:paginate total="${excelUploadInstanceTotal}" class="pagination"/>
			</div>
			</div>
		</div>
		</section>
		</div>
	</body>
</html>

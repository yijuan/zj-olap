
<%@page import="com.surelution.zjolap.Branch"%>
<%@ page import="com.surelution.zjolap.ExcelUpload" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'excelUpload.label', default: 'ExcelUpload')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<script type="text/javascript">
			function deleteItem(itemId) {
				if(confirm('如果确定删除，系统可能需要执行较长时间，请耐心等候后续提示！确定删除？')) {
					$.ajax({
						  method: "POST",
						  url: "${createLink(controller:'excelUpload', action:'delete')}/" + itemId
						})
						  .done(function( data ) {
							  alert(data.msg);
							  if(data.succeed) {
								  document.location.reload(true);
							  }
						  });
				}
			}
		</script>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					销售数据列表 <small></small>
				</h1>
				<%--<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 销售数据上传</a></li>
					<li class="active">上传列表</li>
				</ol>
				--%></section>
	<section class="content">
		 <div class="c1" style=" height: 30px; border-bottom: 1px solid #e5e5e5;margin-bottom:10px;">
            <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
			<g:link class="create" action="createSalesOrder">新增销售Excel上传</g:link>
       </div>
       <div class="box box-info">
                <div class="box-header with-border">
                  <h3 class="box-title">销售数据表查询</h3>
                </div>
                
                 <g:if test="${flash.message}">
			     <div class="message" role="status">${flash.message}</div>
			   </g:if>
			
                <div class="box-body">
		<div id="create-salesOrder" class="scaffold-create" role="main">
			<g:form action="list" >
				<fieldset class="form">
				<g:if test="${branches}">
					<div class="fieldcontain ">
					    <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch">
							 分公司
						</label></dt>
	                           <dd>
	                               <div class="col-md-2" style="margin-left:-20px;">
	                               <g:select name="branch" from="${branches}" optionKey="id" optionValue="name" noSelection="${['':''] }" value="${branch?.id }" class="form-control"/>
	                               </div>   
	                            </dd>
	                        </dl>				
					</div>
					</g:if>
					<div class="fieldcontain form-group">
						<label for="financialMonth_Year" class="col-sm-1 control-label" style="margin-left:-10px; width:100px;"> 结算年/月 </label>
						<div class="col-xs-1" style="margin-left:-15px;">
					          <strong>年:</strong></div>
						<div class="col-xs-2" style="margin-left:-70px;"><g:select name="financialMonth_Year"
							from="${['','2013','2014','2015','2016','2017']}"
							value="${params.financialMonth_Year}" class="form-control many-to-one" style="width:125px;"/>
						</div>					
						<div class="col-xs-1" style="margin-left:-20px;">
						<strong>月:</strong></div>
						<div class="col-xs-2" style="margin-left:-60px;" ><g:select name="financialMonth_Month"
							from="${['','1','2','3','4','5','6','7','8','9','10','11','12']}"
							value="${params.financialMonth_Month}" class="form-control many-to-one" />
					     </div>
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="search" class="search btn btn-primary" value="${message(code: 'default.button.search.label', default: 'Search')}" />
				</fieldset>
			</g:form>
			</div>
			</div>
		</div>
		<div id="list-excelUpload" class="content scaffold-list" role="main">
			<h4><g:message code="default.list.label" args="[entityName]" /></h4>
			
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
					
						<g:sortableColumn property="deleted" title="${message(code: 'excelUpload.deleted.label', default: 'Deleted')}" params="${params}" class="alink"/>
					
						<%--
						<g:sortableColumn property="filePath" title="${message(code: 'excelUpload.filePath.label', default: 'File Path')}" />
					
						<g:sortableColumn property="originalFileName" title="${message(code: 'excelUpload.originalFileName.label', default: 'Original File Name')}" />
						--%>
						<g:sortableColumn property="user" title="${message(code: 'excelUpload.branch.label', default: 'Branch')}" params="${params}" class="alink"/>
						<g:sortableColumn property="month" title="${message(code: 'excelUpload.month.label', default: '账期')}" params="${params}" class="alink"/>
						<th>文件名</th>
						<g:sortableColumn property="user" title="${message(code: 'excelUpload.user.label', default: 'User')}" params="${params}" class="alink"/>
						<g:sortableColumn property="uploadedAt" title="${message(code: 'excelUpload.uploadedAt.label', default: 'Uploaded At')}" params="${params}" class="alink"/>
						<th>下载</th>
						<sec:ifAnyGranted roles="ROLE_ADMIN">
						<th>删除（会删除账单）</th>
						</sec:ifAnyGranted>
					</tr>
				</thead>
				<tbody>
				<g:each in="${excelUploadInstanceList}" status="i" var="excelUploadInstance">
					<tr>
					
						<td><g:formatBoolean boolean="${excelUploadInstance.deleted}"/></td>
					
						<%--<td>${fieldValue(bean: excelUploadInstance, field: "filePath")}</td>
					
						<td>${fieldValue(bean: excelUploadInstance, field: "originalFileName")}</td>
						--%>
					
					
						<g:if test="${excelUploadInstance?.user?.branch}">
							<td>${excelUploadInstance?.user?.branch?.name} </td>
						</g:if>
						<g:else>
							<td>总公司</td>
						</g:else>
						<td>${excelUploadInstance?.month?.year}-${excelUploadInstance?.month?.month} </td>
						<td>${excelUploadInstance?.originalFileName}</td>
						<td>${excelUploadInstance?.user?.username} </td>
						
						<td><g:formatDate date="${excelUploadInstance.uploadedAt}" /></td>
						<td>
						  <g:form controller="excelUpload" >
							<g:hiddenField name="id" value="${excelUploadInstance?.id}" />
							<g:actionSubmit class="create" action="downFile" value="下载"   />
					     </g:form>
						</td>
						<sec:ifAnyGranted roles="ROLE_ADMIN">
					    <td>
					        <g:if test="${!excelUploadInstance.deleted}">
					        <input type="button" onclick="deleteItem('${excelUploadInstance?.id}')" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
					        </g:if>
					    </td>
					    </sec:ifAnyGranted>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div style="height: 50px;">
			<div class="list-page" id="paginateButtons">
				<g:paginate total="${excelUploadInstanceTotal}" params="${params}" class="pagination"/>
			</div>
			</div>
		</div>
		</section>
		</div>
	</body>
</html>

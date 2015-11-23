
<%@page import="com.surelution.zjolap.SalesFullTime"%>
<%@ page import="com.surelution.zjolap.Sales" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'sales.label', default: 'Sales')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<script type="text/javascript">
		 $(document).ready(function() {
			   $("a[data-target=#customerModal]").click(function(event) {
							$(this).data('customerModal',null)
						    event.preventDefault();
						    var target = $(this).attr("href"); 
							    $("#customerModal").load(target, function() { 
							       $("#customerModal").addClass("modalstyle")      
				                   $("#customerModal").modal('show');  			    	
						    });
		    
		});
		   })

		</script>
		
		<style type="text/css">
		    .modalstyle{ width: 40%; background-color:#F5F5F5;filter:alpha(opacity=50); margin-left:30%; height:55%; margin-top:5%; border-radius:5px; border: 1px solid #C5CBD7}
		</style>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					客户经理管理 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 客户管理</a></li>
					<li class="active">客户经理管理</li>
				</ol>
				</section>
	<section class="content">
	           <div class="c1" style=" height: 30px; border-bottom: 1px solid #e5e5e5;margin-bottom:10px;">
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
			<g:link class="create" action="create" data-toggle="modal" data-target="#myModal"><g:message code="default.new.label" args="[entityName]" /></g:link>
			<!-- 新增台账的dialog -->	
			 <div class="modal fade" id="myModal" role="dialog">
                     <div class="modal-dialog">
                           <!-- Modal content-->
                           <div class="modal-content"> 
                           </div>
                          </div>
                      </div>		
          </div>
          
          <div class="box box-info">
                <div class="box-header with-border">
                  <h3 class="box-title">查询客户经理</h3>
                </div>
                <div class="box-body">
		  <sec:ifAnyGranted roles="ROLE_ADMIN">
				<g:form action="list" >
						<fieldset class="form">
							<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'branch', 'error')} ">
								<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch">
									<g:message code="salesOrder.branch.label" default="Branch" />
								</label></dt>
	                           <dd>
	                               <div class="col-xs-3">
	                               <g:select id="branch" name="branch.id" from="${com.surelution.zjolap.Branch.list()}" noSelection="['':'']" optionKey="id" optionValue="name" value="${params['branch.id']}" class="many-to-one form-control" />
	                               </div>
	                            </dd>
	                        </dl>		
							</div>
							<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'fullTime', 'error')} ">
								<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="fullTime">
									<g:message code="salesOrder.fullTime.label" default="Full Time" />
								</label></dt>
	                           <dd>
	                               <div class="col-xs-3">
	                               <g:select name="fullTime.id" from="${SalesFullTime.list() }" optionKey="id" optionValue="name" noSelection="['':'全部']" value="${params['fullTime.id']}" class="form-control"/>
	                               </div>
	                            </dd>
	                        </dl>				
							</div>
	
						</fieldset>
						<fieldset class="buttons">
							<g:submitButton name="search" class="search btn btn-primary" value="${message(code: 'default.button.search.label', default: 'Search')}" />
						   <span class="menuButton"><g:link class="excel btn btn-primary" action="downloadExcel">下载excel</g:link></span>
						</fieldset>
						</g:form>
					
				</sec:ifAnyGranted>
					 
		</div>
		</div>
		<div id="list-sales" class="content scaffold-list" role="main">
			<h3><g:message code="default.list.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-bordered  table-striped">
				<thead>
					<tr><%--
						<g:sortableColumn property="id" title="${message(code: 'sales.id.label', default: 'Id')}" />
					
						--%>
						
						<g:sortableColumn property="name" title="${message(code: 'sales.name.label', default: 'Name')}" params="${params }" class="alink"/>
						<th><g:message code="sales.branch.label" default="Branch" /></th>
						<th><g:message code="sales.fullTime.label" default="Full Time" /></th>
                       <th>操作</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${salesInstanceList}" status="i" var="salesInstance">
					<tr>
					
						<%--<td>${fieldValue(bean: salesInstance, field: "id")}</td>

						--%>
						
						<td>${fieldValue(bean: salesInstance, field: "name")}</td>
						<td>${salesInstance?.branch?.name}</td>
						<td>${salesInstance.fullTime?.name}</td>
						 <td>
					      <a href="${createLink(action:'delete',controller:'sales',id:salesInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除客户经理"></span></a>
		 
		&nbsp;&nbsp;
		
		 <g:link rel="external" action="edit" id="${salesInstance?.id}" params="${params }"  data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新客户经理"></span></g:link>
					    
					    
					    </td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="modal" id="customerModal" role="dialog">
                        <div class="modal-dialog">
                           <!-- Modal content-->
                           <div class="modal-content"> 
                                
                           </div>
                          </div>
                      </div>
                      <div style="height: 50px;">
			<div class="list-page">
				<g:paginate total="${salesInstanceTotal}" params="${params }" class="pagination"/>
			</div>
			</div>
			</div>
			</section>
		</div>
	</body>
</html>

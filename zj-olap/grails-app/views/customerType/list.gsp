
<%@ page import="com.surelution.zjolap.CustomerType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'customerType.label', default: 'CustomerType')}" />
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
		    .modalstyle{ width: 40%; background-color:#F5F5F5;filter:alpha(opacity=50); margin-left:30%; height:45%; margin-top:5%; border-radius:5px; border: 1px solid #C5CBD7}
		</style>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					销售类别 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 销售类别</a></li>
					<li class="active">销售类别管理</li>
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
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<h4><g:message code="default.list.label" args="[entityName]" /></h4>
			
			<table class="table table-bordered  table-striped">
				<thead style="background-color: #3C8DBD">
					<tr>
					
						<th style="color:white"><g:message code="customerType.name.label" default="Name" /></th>
					   <th style="color:white">操作</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${customerTypeInstanceList}" status="i" var="customerTypeInstance">
					<tr>
						<td>${fieldValue(bean: customerTypeInstance, field: "name")}</td>
					   <td>
					   
					    <a href="${createLink(action:'delete',controller:'customerType',id:customerTypeInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除销售类别"></span></a>
		 
		&nbsp;&nbsp;
		
		 <g:link rel="external" action="edit" id="${customerTypeInstance?.id}"  data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新销售类别"></span></g:link>
					    
					   
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
				<g:paginate total="${customerTypeInstanceTotal}" class="pagination"/>
			</div>
			</div>
			</section>
		</div>
	</body>
</html>

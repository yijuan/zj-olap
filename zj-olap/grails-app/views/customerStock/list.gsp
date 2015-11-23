
<%@ page import="com.surelution.zjolap.CustomerStock" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'customerStock.label', default: 'CustomerStock')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>	
		<r:require module="jquery-ui"/>
		<script type="text/javascript" src="${resource(file:'js/jquery.stickytableheaders.js') }"></script>
	     
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



			  
	          $("table").stickyTableHeaders({ scrollableArea: $(".scaffold-list")[0]});
		   })

		</script>
		
		<style type="text/css">
		    .modalstyle{ width: 40%; background-color:#F5F5F5;filter:alpha(opacity=50); margin-left:30%; height:75%; margin-top:5%; border-radius:5px; border: 1px solid #C5CBD7}
		</style>
	</head>
	<r:layoutResources/>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					提货单 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 客存</a></li>
					<li class="active">提货单</li>
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
			<div class="message" role="status" style="color:red; font-weight:bold;">${flash.message}</div>
			</g:if>
		<div class="box box-info">
                <div class="box-header with-border">
                  <h4 class="box-title">提货单查询</h4>
                </div>
                <div class="box-body">
		             <g:form action="list" >
					     <fieldset class="form">
					          <sec:ifAnyGranted roles="ROLE_ADMIN">
					    
						    <div class="fieldcontain  ">
						    <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch">
								        <g:message code="salesOrder.branch.label" default="Branch" />
							         </label></dt>
	                           <dd>
	                               <div class="col-xs-3">
	                               <g:select id="branch" name="branchId" from="${com.surelution.zjolap.Branch.list()}" noSelection="['':'']" optionKey="id" optionValue="name" value="${params['branchId']}" class="form-control many-to-one"/>
	                               </div>
	                            </dd>
	                        </dl>
						    
							        
						   </div>
						    </sec:ifAnyGranted>
						
						
						
						<div class="row">
                    <div class="col-md-8">
                    <div class="fieldcontain">
				        <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="salingAt">
								<g:message code="customerStock.cdate.label" default="Date" />
							</label></dt>
	                           <dd>
	                              
	                               <div class="col-xs-5">
	                              <g:textField name="dateBegin" value="${params.dateBegin}" id="datetimepicker" class="form-control"/>
						<script type="text/javascript">
                $('#datetimepicker').datetimepicker({
                	isRTL: false,
                    format: 'yyyy.mm.dd hh:ii',
                    autoclose:true,
                    language: 'zh-CN'
					});
        </script>
						</div>
						<div class="col-xs-1">~</div>
						<div class="col-xs-5">
						<g:textField name="dateEnd" value="${params.dateEnd}" id="datetimepicker1" class="form-control"/>
						<script type="text/javascript">
                $('#datetimepicker1').datetimepicker({
                	isRTL: false,
                    format: 'yyyy.mm.dd hh:ii',
                    autoclose:true,
                    language: 'zh-CN'
					});
        </script>
						
						</div>
	                              
	                            </dd>
	                        </dl>						
					</div>
				    </div>
				   </div>
						
						
					</fieldset>
					 
					 <fieldset class="buttons">
						<g:submitButton name="search" class="search btn btn-primary" value="${message(code: 'default.button.search.label', default: 'Search')}" />
						<g:submitButton name="downExcelFile" class="search btn btn-primary" value="excel导出"/>
					</fieldset>
					
					</g:form>
					</div>
					</div>
					
			<sec:ifAnyGranted roles="ROLE_ADMIN">	
			<div style="height: 30px; color: red; background-color: red; border-radius:3px;">
			        ${branchStr}		
			</div>	
				
			</sec:ifAnyGranted>
			<h2><g:message code="default.list.label" args="[entityName]" /></h2>
			
			
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
					
						<g:sortableColumn property="branch" title="${message(code: 'customerStock.branch.label', default: 'Branch')}"  params="${params}" class="alink"/>
						<g:sortableColumn property="customer" title="${message(code: 'customerStock.customer.label', default: 'Customer')}"  params="${params}" class="alink"/>
						<g:sortableColumn property="customerType" title="${message(code: 'salesOrder.customerType.label', default: '客户性质')}"  params="${params}" class="alink"/>
						<g:sortableColumn property="customerType" title="${message(code: 'salesOrder.customerTypelevel2.label', default: '机构用户分类')}"  params="${params}" class="alink"/>
						<g:sortableColumn property="customerType" title="${message(code: 'salesOrder.customerTypelevel3.label', default: '工业分类')}"  params="${params}" class="alink"/>
						<g:sortableColumn property="cdate" title="${message(code: 'customerStock.cdate.label', default: 'Date')}"  params="${params}" class="alink"/>
						<g:sortableColumn property="category" title="${message(code: 'customerStock.category.label', default: 'Gas Type')}"  params="${params}" class="alink"/>
						<g:sortableColumn property="gasType" title="${message(code: 'customerStock.gasType.label', default: 'Gas Type')}"  params="${params}" class="alink"/>
						<g:sortableColumn property="stockQty" title="${message(code: 'customerStock.stockQty.label', default: 'Stock Qty')}"  params="${params}" class="alink"/>
						<g:sortableColumn property="status" title="${message(code: 'customerStock.status.label', default: 'Status')}"  params="${params}" class="alink"/>
					
					    <th><g:message code="action.label" default="操作"/></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${customerStockInstanceList}" status="i" var="customerStockInstance">
					<tr>
					
						<td>${customerStockInstance?.customerBranch?.branch?.name}</td>
						
						<td>${customerStockInstance?.customerBranch?.customer?.name}</td>
						<td>${customerStockInstance?.customerBranch?.customer?.customerType?.level2?.level1?.name}</td>
						<td>${customerStockInstance?.customerBranch?.customer?.customerType?.level2?.displayName}</td>
						<td>${customerStockInstance?.customerBranch?.customer?.customerType?.displayName}</td>
						<td><g:formatDate format="yyyy-MM-dd" date="${customerStockInstance?.date?.date}"/></td>
					
						<td>${customerStockInstance.gasType?.category?.name}</td>
						<td>${customerStockInstance.gasType.name}</td>
					
						<td>${fieldValue(bean: customerStockInstance, field: "stockQty")}</td>
						
						<td>${fieldValue(bean: customerStockInstance, field: "statusName")}</td>
					    
					    <td>
					      <a href="${createLink(action:'delete',controller:'customerStock',id:customerStockInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除提油"></span></a>
		 
		&nbsp;&nbsp;
		
		 <g:link rel="external" action="edit" id="${customerStockInstance?.id}" params="${params }" data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新提油"></span></g:link>
					    
					    
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
			<div class="list-page" id="paginateButtons">
				<g:paginate total="${customerStockInstanceTotal}"  params="${params}" class="pagination"/>
			</div>
		</div>

		</section>
		</div>
	</body>
</html>

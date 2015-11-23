
<%@page import="com.surelution.zjolap.Sales"%>
<%@ page import="com.surelution.zjolap.CustomerVisting" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'customerVisting.label', default: 'CustomerVisting')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
  <link rel="stylesheet" href="${resource(file:'css/jquery-ui.css') }"><%--
  <script src="${resource(file:'js/jquery-1.11.3.min.js') }"></script>
  <script src="${resource(file:'js/jquery-ui.min.js') }"></script>
--%>

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
		   
		   $(document).ready(function(){
    	$("#comauto").autocomplete({		
    		  source: function(request, response){
    		   $.ajax({
    		    url: "${createLink(controller:'customerVisting', action:'coustomerlist')}", // remote datasource
    		    data: request,
    		    success: function(data){
    		     response(data); // set the response
    		    },
    		    error: function(){ // handle server errors
    		     $.jGrowl("Unable to retrieve Companies", {
    		      theme: 'ui-state-error ui-corner-all'   
    		     });
    		    }
    		   });
    		  },
    		  minLength: 2, // triggered only after minimum 2 characters have been entered.
    		  select: function(event, ui) { // event handler when user selects a company from the list.
    		   $("#customer\\.id").val(ui.item.id); // update the hidden field.
    		  }
    		 });
        })
		   
		    

		</script>
		
		<style type="text/css">
		    .modalstyle{ width: 40%; background-color:#F5F5F5;filter:alpha(opacity=50); margin-left:30%; height:60%; margin-top:5%; border-radius:5px; border: 1px solid #C5CBD7}
		</style>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					客户 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 客户</a></li>
					<li class="active">客户回访记录</li>
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
                  <h4><g:message code="default.list.label" args="[entityName]" /></h4>
                </div>
                <div class="box-body">
		
			
			
			
			<g:form action="list" >
				<fieldset class="form">
					
<div>
        <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="customer">
		<g:message code="customerVisting.customer.label" default="Customer" />
	</label></dt>
	                           <dd>
	                               <div class="col-md-2" style="margin-left:-10px;">
	                              
	                                 <g:textField id="comauto" name="customer"  value="${params?.customer }"  class="form-control" style="width:170px;"/>
	                               </div>
	                            </dd>
	                        </dl>
</div>

<div>
<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="sales">
		<g:message code="customerVisting.sales.label" default="Sales" />
	</label></dt>
	                           <dd>
	                               <div class="col-md-2" style="margin-left:-10px;">
	                              <g:select id="sales" name="sales.id" from="${sales?sales:Sales.list()}" noSelection="['':'']" optionKey="id" optionValue="${{ (it.branch?it.branch.name:"") + "->" + it.name} }" value="${customerVistingInstance?.sales?.id}" class="form-control" style="width:170px;"/>
	                               </div>
	                            </dd>
	                        </dl>
	
	
</div>


<div class="row">
                    <div class="col-md-8">
					<div class="form-group fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'salingAt', 'error')} required">
				        <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="vistingAt">
		<g:message code="customerVisting.vistingAt.label" default="Visting At" />
	</label></dt>
	                           <dd>
	                              
	                               <div class="col-xs-4" style="margin-left:-10px;">
	                               <g:textField name="vistingAtFrom" value="${params?.vistingAtFrom}" id="datetimepicker" class="form-control" style="width:170px;"/>
						 <script type="text/javascript">
                $('#datetimepicker').datetimepicker({
                	isRTL: false,
                    format: 'yyyy.mm.dd hh:ii',
                    autoclose:true,
                    language: 'zh-CN'
					});
        </script>
						</div>
						<div class="col-xs-1" style="margin-left:-30px;">~</div>
						<div class="col-xs-4" style="margin-left:-30px;">
						<g:textField name="vistingAtTo" value="${params?.vistingAtTo}" id="datetimepicker1"  class="form-control" style="width:170px;"/>
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
					<g:submitButton name="search" class="search btn btn-primary" value="查询" />
				</fieldset>
			</g:form>
		
			</div></div>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
					
						<th><g:message code="customerVisting.customer.label" default="Customer" /></th>
					
						<th><g:message code="customerVisting.sales.label" default="Sales" /></th>
					
						<th><g:message code="customerVisting.type.label" default="Type" /></th>
					
						<g:sortableColumn property="vistingAt" title="${message(code: 'customerVisting.vistingAt.label', default: 'Visting At')}" class="alink"/>
					
						<g:sortableColumn property="description" title="${message(code: 'customerVisting.description.label', default: 'Description')}" class="alink"/>
					
						<th><g:message code="customerVisting.operator.label" default="Operator" /></th><%--
					   <th>操作</th>
					--%></tr>
				</thead>
				<tbody>
				<g:each in="${customerVistingInstanceList}" status="i" var="customerVistingInstance">
					<tr>
					
						<td>${customerVistingInstance?.customer?.name}</td>
					
						<td>${customerVistingInstance.sales?.branch?.name}-&gt;${customerVistingInstance.sales.name}</td>
					
						<td>${customerVistingInstance.type.name}</td>
					
						<td><g:formatDate date="${customerVistingInstance.vistingAt}" /></td>
					
						<td>${fieldValue(bean: customerVistingInstance, field: "description")}</td>
					
						<td>${customerVistingInstance.operator?.username}</td>
						
						<%--<td>
						 <a href="${createLink(action:'delete',controller:'customerVisting',id:customerVistingInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除客户回访"></span></a>
		                                                                    
		&nbsp;&nbsp;
		
		 <g:link rel="external" action="edit" id="${customerVistingInstance?.id}"  data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新客户回访"></span></g:link>
					    
						
						</td>
					
					--%></tr>
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
				<g:paginate total="${customerVistingInstanceTotal}" class="pagination" />
				</div>
		</div>
		</section>
		</div>
	</body>
</html>

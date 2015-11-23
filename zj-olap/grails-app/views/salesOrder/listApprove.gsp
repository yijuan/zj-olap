
<%@ page import="com.surelution.zjolap.SalesOrder" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'salesOrder.label', default: 'SalesOrder')}" />
		<r:require module="jquery-ui"/>
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<script type="text/javascript" src="${resource(file:'js/jquery.stickytableheaders.js') }"></script>
		
		<script type="text/javascript">
	        $(document).ready(function()
	        {
	          fun();
	          clickFun();

	          $("table").stickyTableHeaders({ scrollableArea: $(".scaffold-list")[0]});
	        })
	        
	        function fun() {
  	 			 var thead = document.getElementsByTagName("thead")[0];
  	 			 var tr = thead.getElementsByTagName("tr")[0];
  	 			 var tds = tr.getElementsByTagName("th");
  	 			 
  	 			 var div = document.getElementById("divCols");
  	 			 var checkedStr = "${params.checkedStr}";
   	 			 var  strs= checkedStr.split(",");
  	  	 	     
  	 			 for(var i=0;i<tds.length;i++) {
  	 			 	  
  	 			 	  
  	 			 	  var input = document.createElement("input");
  	 			 	  var span = document.createElement("span");
  	 			 	  span.innerHTML = "&nbsp;&nbsp;&nbsp; "+tds[i].textContent+":";
  	 			 	  div.appendChild(span);
  	 			 	  
  	 			 	  input.type='checkbox';
  	 			 	  input.name = "colsCheck";
  	 			 	  input.checked = checkedFun(strs,i);
  	 			 	  input.value =i;
  	 			 	  if(input.addEventListener) {
  	 			 	  	input.addEventListener("click",clickFun);
  	 			 	  }else if(input.attachEvent){
  	 			 	  	input.attachEvent("onClick",clickFun);
  	 			 	  }
  	 			 	  
  	 			 	  div.appendChild(input);
  	 			 }
  	 			 
  	 		}
	        function checkedFun(strs,index) {
				if(strs&&strs[0].length!=0){
					for(var i=0;i<strs.length;i++) {
						if(strs[i]==index&&strs[i].length!=0) {
							return true;
						}
					}
					return false;
				}

				return true;
  	  	  	}
  	 		
  	 		function clickFun() {
  	 		  var div = document.getElementById("divCols");
  	 		  var inputs = div.getElementsByTagName("input");
  	 		  var checkedStr = "";
  	 		
  	 		  for(var i =0;i<inputs.length;i++){
  	 		  	if(inputs[i].checked) {
  	 		  	   checkedStr+=i+",";
  	 		  		visableTableHead(i,true);
  	 		  	} else {

  	 		  	  visableTableHead(i,false);
  	 		  	}
  	 		  }

  	 		  appendCheckedStrUrl(checkedStr);
  	 		}

  	 		function appendCheckedStrUrl(checkedStr) {
				var pageDiv = document.getElementById("paginateButtons");
				var  allA = pageDiv.getElementsByTagName("a");
				var  checkedStrId = document.getElementById("checkedStrId");

				checkedStrId.value = checkedStr;
				if(allA) {
					for(var i=0;i<allA.length;i++) {
						var href = allA[i].href ; 
						var offset = "";
						var pams = href.split("checkedStr");
						if(pams[1]) {
							offset =pams[1].substr(pams[1].indexOf("&"));
						}
						allA[i].href=pams[0] + "&checkedStr="+checkedStr+offset
					}
				}
  	  	  	}
  	 		
  	 		function visableTableHead(index,show) {
  	 			 var thead = document.getElementsByTagName("thead")[0];
  	 			 var tr = thead.getElementsByTagName("tr")[0];
  	 			 var th = tr.getElementsByTagName("th")[index];
  	 			 
  	 			 if(show) {
  	 			    th.style.display = ""; 
  	 			    visableTableTd(index,show);
  	 			 }else {
  	 			 	   th.style.display = "none"; 
  	 			 	   visableTableTd(index,show);
  	 			 }
  	 		}
  	 		
  	 		function visableTableTd(index,show) {
  	 			 var thead = document.getElementsByTagName("tbody")[0];
  	 			 var trs = thead.getElementsByTagName("tr");
  	 			 for(var i =0;i<trs.length;i++){
  	 			    var td = trs[i].getElementsByTagName("td")[index];
  	 		  	if(show) {
					td.style.display = ""; 
  	 		  	} else {
  	 		  	 	  td.style.display = "none"; 
  	 		  	}
  	 		  }
  	 		}
	    </script>
	    
	    <style type="text/css">
	       .alink a{ color: white;}
	    
	    </style>
	</head>
	<body >
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					台账审批 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 台账</a></li>
					<li class="active">台账审批</li>
				</ol>
		   </section>
	<section class="content">		
		
		
		<div class="box box-info">
                <div class="box-header with-border">
                  <h3 class="box-title">台账审批查询</h3>
                </div>
                <g:if test="${flash.message}">
			<div class="message" role="status" style="color:red; font-weight:bold;">${flash.message}</div>
			</g:if>
                <div class="box-body">
		<div id="create-salesOrder">
			<g:form action="listApprove" >
					<fieldset class="form">
					    <sec:ifAnyGranted roles="ROLE_ADMIN">
					    
						<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'branch', 'error')} ">
							<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch">
								<g:message code="salesOrder.branch.label" default="Branch" />
							</label></dt>
	                           <dd>
	                               <div class="col-md-3">
	                                 <g:select id="branch" name="branch.id" from="${com.surelution.zjolap.Branch.list()}" noSelection="['':'']" optionKey="id" optionValue="name" value="${params['branch.id']}" class="form-control many-to-one"/>
	                               </div>
	                            </dd>
	                        </dl>								
						</div>
						</sec:ifAnyGranted>
						
						<div class="fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'orderFormNo', 'error')} ">
							<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="orderFormNo">
								<g:message code="salesOrder.orderFormNo.label" default="Order Form No" />
							</label></dt>
	                           <dd>
	                               <div class="col-md-3">
	                                 <g:textField name="orderFormNo" value="${params.orderFormNo}" class="form-control"/>
	                               </div>
	                            </dd>
	                        </dl>		
						</div>
						
						<div class="row">
                    <div class="col-md-8">
					<div class="form-group fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'salingAt', 'error')} required">
				        <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="salingAt"> <g:message
								code="salesOrder.salingAt.label" default="Saling At" />
						</label></dt>
	                           <dd>
	                              
	                               <div class="col-xs-5">
	                               <g:textField name="salingAtFrom" value="${params.salingAtFrom}" id="datetimepicker" class="form-control"/>
						<script type="text/javascript">
                $('#datetimepicker').datetimepicker({
                	isRTL: false,
                	format: 'yyyy.mm.dd hh:ii',
                    autoclose:true,
                   
                    language: 'zh-CN'
					});
        </script>
						</div>
						<div class="col-xs-1" style="margin-left:-20px;">~</div>
						<div class="col-xs-4" style="margin-left:-30px;" >
						<g:textField name="salingAtTo" value="${params.salingAtTo}" id="datetimepicker1" class="form-control"/>
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
						<g:hiddenField id="checkedStrId" name="checkedStr" value="${params.checkedStr}"/>
					</fieldset>
					<fieldset class="buttons">
						<g:submitButton name="search" class="search btn btn-primary" value="${message(code: 'default.button.search.label', default: 'Search')}" />
					</fieldset>
				</g:form>
		</div>
		</div>
		</div>
		<div id="list-salesOrder" class="scaffold-list" role="main" style="overflow: auto;">
			<h3><g:message code="default.list.label" args="[entityName]" /></h3>
			
			<div id="divCols" style="width:1800px;"></div>
			<div class="tab" class="scaffold-list">
			<table class="table table-bordered  table-striped" style="width: 1800px;">
				<thead style=" background-color: #36A9E0;">
					<tr style="color:white;">
						<th><g:message code="salesOrder.branch.label" default="Branch" /></th>
						<th><g:message code="salesOrder.salingtype.label" default="Saling Type" /></th>
						<th><g:message code="salesOrder.month.label" default="结算月" /></th>
						<th><g:message code="salesOrder.customer.label" default="Customer" /></th>
						<g:sortableColumn property="orderFormNo" title="${message(code: 'salesOrder.orderFormNo.label', default: 'Order Form No')}"  params="${params}" class="alink"/>
						<th><g:message code="salesOrder.timeByDay.label" default="开单时间" /></th>
						<th><g:message code="salesOrder.category.label" default="Category" /></th>
						<th><g:message code="salesOrder.gasType.label" default="Gas Type" /></th>
						<th><g:message code="salesOrder.quantity.label" default="数量" /></th>
						<th><g:message code="salesOrder.purchasingUnitPrice.label" default="销售价" /></th>
						<g:sortableColumn property="purchasingPrice" title="${message(code: 'salesOrder.purchasingPrice.label', default: 'Purchasing Price')}"  params="${params}" class="alink"/>
			
						<th><g:message code="salesOrder.customerType.label" default="客户性质" /></th>
						<th><g:message code="salesOrder.customerTypelevel2.label" default="机构用户分类" /></th>
						<th><g:message code="salesOrder.customerTypelevel3.label" default="工业分类" /></th>

						<g:sortableColumn property="listUnitPrice" title="${message(code: 'salesOrder.listUnitPrice.label', default: 'List Unit Price')}"  params="${params}" class="alink"/>
						<th><g:message code="salesOrder.sales.label" default="客户经理人" /></th>
						<th>状态</th>
						 <sec:ifAnyGranted roles="ROLE_ADMIN">
						<th>审批</th>
						</sec:ifAnyGranted>
						
						<th>操作</th>
						
					</tr>
				</thead>
				<tbody>
				<g:each in="${salesOrderInstanceList}" status="i" var="salesOrderInstance">
					<tr>
						<td>${salesOrderInstance.branch.name}</td>
						<td>${salesOrderInstance?.salingtype?.name}</td>
						<td>${salesOrderInstance?.month?.year}-${salesOrderInstance?.month?.month} </td>
						<td>${salesOrderInstance?.customer?.name}</td>
						<td>${fieldValue(bean: salesOrderInstance, field: "orderFormNo")}</td>
						<td><g:formatDate format="yyyy-MM-dd" date="${salesOrderInstance?.timeByDay?.date}"/> </td>
						<td>${salesOrderInstance.gasType?.category?.name}</td>
						<td>${salesOrderInstance.gasType.name}</td>
						<td>${salesOrderInstance.quantity}</td>
						<td><g:formatNumber number="${salesOrderInstance.purchasingUnitPrice}" format=".###"/></td>
						
						<td><g:formatNumber number="${salesOrderInstance.quantity * salesOrderInstance.purchasingUnitPrice}" format=".###"/></td>
			
						<td>${salesOrderInstance?.customer?.customerType?.level2?.level1?.name}</td>
						<td>${salesOrderInstance?.customer?.customerType?.level2?.displayName}</td>
						<td>${salesOrderInstance?.customer?.customerType?.displayName}</td>
		
						<td>${salesOrderInstance.listUnitPrice}</td>
						
						<td>${salesOrderInstance?.sales?.name}</td>
						<td>
							${salesOrderInstance?.statusName}
						</td>
						 <sec:ifAnyGranted roles="ROLE_ADMIN">
						<td>
						    <g:if test="${salesOrderInstance?.status !='DISABLE'}">
							<g:link action="saveApprove" id="${salesOrderInstance.id}">
					    	同意
					    	</g:link>
					    	 &nbsp; &nbsp;
					    	 <g:link action="saveDisApprove" id="${salesOrderInstance.id}">
					    	 驳回
					    	 </g:link>
							</g:if>
							
						</td>
						</sec:ifAnyGranted>
						<g:if test="${salesOrderInstance?.status =='DISABLE'}">
						<td>
					      <a href="${createLink(action:'delete',controller:'salesOrder',id:salesOrderInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除销售台账"></span></a>
		 
		&nbsp;&nbsp;
		
		 <g:link rel="external" action="edit" id="${salesOrderInstance?.id}" params="${params }" data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新销售台账"></span></g:link>
					    
					    
					    </td>
						</g:if>
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
			</div>
			<div class="list-page" id="paginateButtons">
				<g:paginate total="${salesOrderInstanceTotal}" params="${params}" class="pagination"/>
			</div>
		</div>
		</section>
		</div>
	</body>
</html>

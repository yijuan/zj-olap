
<%@ page import="com.surelution.zjolap.SalesOrder"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="jarvis">
<g:set var="entityName"
	value="${message(code: 'salesOrder.label', default: 'SalesOrder')}" />
<r:require module="jquery-ui" />
<script type="text/javascript"
	src="${resource(file:'js/jquery.stickytableheaders.js') }"></script>
<title><g:message code="default.list.label" args="[entityName]" /></title>
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
		var strs = checkedStr.split(",");

		for (var i = 0; i < tds.length; i++) {

			var input = document.createElement("input");
			var span = document.createElement("span");
			span.innerHTML = "&nbsp;&nbsp;&nbsp; " + tds[i].textContent + ":";
			div.appendChild(span);

			input.type = 'checkbox';
			input.name = "colsCheck";
			input.checked = checkedFun(strs, i);
			input.value = i;
			if (input.addEventListener) {
				input.addEventListener("click", clickFun);
			} else if (input.attachEvent) {
				input.attachEvent("onClick", clickFun);
			}

			div.appendChild(input);
		}

	}
	function checkedFun(strs, index) {
		if (strs && strs[0].length != 0) {
			for (var i = 0; i < strs.length; i++) {
				if (strs[i] == index && strs[i].length != 0) {
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

		for (var i = 0; i < inputs.length; i++) {
			if (inputs[i].checked) {
				checkedStr += i + ",";
				visableTableHead(i, true);
			} else {

				visableTableHead(i, false);
			}
		}

		appendCheckedStrUrl(checkedStr);
	}

	function appendCheckedStrUrl(checkedStr) {
		var pageDiv = document.getElementById("paginateButtons");
		var allA = pageDiv.getElementsByTagName("a");
		var checkedStrId = document.getElementById("checkedStrId");

		checkedStrId.value = checkedStr;
		if (allA) {
			for (var i = 0; i < allA.length; i++) {
				var href = allA[i].href;
				var offset = "";
				var pams = href.split("checkedStr");
				if (pams[1]) {
					offset = pams[1].substr(pams[1].indexOf("&"));
				}
				allA[i].href = pams[0] + "&checkedStr=" + checkedStr + offset
			}
		}
	}

	function visableTableHead(index, show) {
		var thead = document.getElementsByTagName("thead")[0];
		var tr = thead.getElementsByTagName("tr")[0];
		var th = tr.getElementsByTagName("th")[index];

		if (show) {
			th.style.display = "";
			visableTableTd(index, show);
		} else {
			th.style.display = "none";
			visableTableTd(index, show);
		}
	}

	function visableTableTd(index, show) {
		var thead = document.getElementsByTagName("tbody")[0];
		var trs = thead.getElementsByTagName("tr");
		for (var i = 0; i < trs.length; i++) {
			var td = trs[i].getElementsByTagName("td")[index];
			if (show) {
				td.style.display = "";
			} else {
				td.style.display = "none";
			}
		}
	}



	
	

	
</script>
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
		    .modalstyle{ width: 40%; background-color:#F5F5F5;filter:alpha(opacity=50); margin-left:30%; height:95%; margin-top:5%; border-radius:5px; border: 1px solid #C5CBD7;margin-top:-10px;}
		</style>
</head>

<body>
<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					销售台账 <small></small>
				</h1>
				<%--<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 台账</a></li>
					<li class="active">销售台账</li>
				</ol>
				--%></section>
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
				<div class="message" role="status" style="color:red; font-weight:bold;">
					${flash.message}
				</div>
			</g:if>
		
		<div class="box box-info">
                <div class="box-header with-border">
                  <h3 class="box-title">销售查询</h3>
                </div>
                <div class="box-body">
                  <g:form action="list">
				    <fieldset class="form">	
				    
				   				    
				   							
					<sec:ifAnyGranted roles="ROLE_ADMIN">	
								 
						<div class=" form-group fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'branch', 'error')} ">
							<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch">
		                          <g:message code="salesOrder.branch.label" default="Branch" />
	                               </label></dt>
	                           <dd>
	                               <div class="col-md-8" style="margin-left:-25px;">
	                                 <g:select id="branch" name="branch.id"
								from="${com.surelution.zjolap.Branch.list()}"
								noSelection="['':'']" optionKey="id" optionValue="name"
								value="${params['branch.id']}" class="form-control many-to-one" style="width:145px;"/>
	                               </div>
	                            </dd>
	                        </dl>
						</div>	
												
					</sec:ifAnyGranted>
                     
                      
					<div class="form-group fieldcontain ${hasErrors(bean: salesOrderInstance, field: 'orderFormNo', 'error')} ">
						<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="orderFormNo"> <g:message
								code="salesOrder.orderFormNo.label" default="Order Form No" />
						</label></dt>
	                           <dd>
	                               <div class="col-xs-3" style="margin-left:-25px;">
	                               <input type="text" class="form-control" name="orderFormNo" value="${params.orderFormNo}"  placeholder="订单号" style="width:145px;"/>
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
	                              
	                               <div class="col-xs-4" style="margin-left:-25px;">
	                               <g:textField name="salingAtFrom" id="salingAtFrom" value="${params.salingAtFrom}" id='datetimepicker' class="form-control" style="width:145px;"/>
						<script type="text/javascript">
                $('#datetimepicker').datetimepicker({
                	isRTL: false,
                    format: 'yyyy.mm.dd',
                    autoclose:true,
                    minView: 'month',
                    language: 'zh-CN'
					});
        </script>
						</div>
						<div class="col-xs-1" style="margin-left:-50px;">~</div>
						<div class="col-xs-4" style="margin-left:-30px;">
						<g:textField name="salingAtTo" value="${params.salingAtTo}" id='datetimepicker4' class="form-control"/>
						 <script type="text/javascript">
                $('#datetimepicker4').datetimepicker({
                	isRTL: false,
                	format: 'yyyy.mm.dd',
                	 minView: 'month',
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
				
					<div class="fieldcontain form-group">
						<label for="financialMonth_Year" class="col-sm-1 control-label" style="margin-left:-10px; width: 100px;"> 结算年/月 </label>
						<div class="col-xs-1" style="margin-left:-25px;">
					          <strong>年:</strong></div>
						<div class="col-xs-2" style="margin-left:-70px;"><g:select name="financialMonth_Year"
							from="${['','2013','2014','2015','2016','2017']}"
							value="${params.financialMonth_Year}" class="form-control many-to-one" style="width:125px;"/>
						</div>					
						<div class="col-xs-1">
						<strong>月:</strong></div>
						<div class="col-xs-2" style="margin-left:-70px; width: 180px;"><g:select name="financialMonth_Month"
							from="${['','1','2','3','4','5','6','7','8','9','10','11','12']}"
							value="${params.financialMonth_Month}" class="form-control many-to-one" />
					     </div>
					</div>  
					<g:hiddenField id="checkedStrId" name="checkedStr"
						value="${params.checkedStr}" />
				</fieldset>
				<br>
				<fieldset class="buttons">
				
					<g:submitButton name="search" class="btn btn-primary" value="${message(code: 'default.button.search.label', default: 'Search')}" />			
					
					
					<g:submitButton name="downExcelFile" class="btn btn-primary" value="excel导出" />
				   
				</fieldset>
			</g:form>
                </div><!-- /.box-body -->
              </div>
		
		<div id="list-salesOrder"  class="scaffold-list"
			style="height: 600px; overflow: scroll;">
			<h4>
				<g:message code="default.list.label" args="[entityName]" />
			</h4>
			
			<div id="divCols" style="width: 2000px;"></div>
			
			<div class="tab" style="width: 2000px;">
				<table class="table table-bordered  table-striped" >
					<thead>
						<tr>
							<g:sortableColumn property="branch"
								title="${message(code: 'salesOrder.branch.label', default: 'Branch')}"
								params="${params}" class="alink"/>	
							<g:sortableColumn property="salingtype"
								title="${message(code: 'salesOrder.salingtype.label', default: 'Saling Type')}"
								params="${params}"  class="alink"/>
							<g:sortableColumn property="month"
								title="${message(code: 'salesOrder.month.label', default: '结算月')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="customer"
								title="${message(code: 'salesOrder.customer.label', default: 'Customer')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="orderFormNo"
								title="${message(code: 'salesOrder.orderFormNo.label', default: 'Order Form No')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="timeByDay"
								title="${message(code: 'salesOrder.timeByDay.label', default: '开单时间')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="gasType"
								title="${message(code: 'salesOrder.category.label', default: 'Category')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="gasType"
								title="${message(code: 'salesOrder.gasType.label', default: 'Gas Type')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="quantity"
								title="${message(code: 'salesOrder.quantity.label', default: '数量')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="purchasingUnitPrice"
								title="${message(code: 'salesOrder.purchasingUnitPrice.label', default: '销售价')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="purchasingPrice"
								title="${message(code: 'salesOrder.purchasingPrice.label', default: 'Purchasing Price')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="customerType"
								title="${message(code: 'salesOrder.customerType.label', default: '客户性质')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="customerType"
								title="${message(code: 'salesOrder.customerTypelevel2.label', default: '机构用户分类')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="customerType"
								title="${message(code: 'salesOrder.customerTypelevel3.label', default: '工业分类')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="listUnitPrice"
								title="${message(code: 'salesOrder.listUnitPrice.label', default: 'List Unit Price')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="sales"
								title="${message(code: 'salesOrder.sales.label', default: '客户经理人')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="bigCase"
								title="${message(code: 'salesOrder.bigCase.label', default: '是否大单')}"
								params="${params}" class="alink"/>
							<g:sortableColumn property="salingtype" title="是否小额配送"
								params="${params}" class="alink"/>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${salesOrderInstanceList}" status="i"
							var="salesOrderInstance">
							<tr>
								<td style="height: 20px;">
										${salesOrderInstance.branch.name}
									</td>
								<td>
									${salesOrderInstance?.salingtype?.name}
								</td>
								<td>
									${salesOrderInstance?.month?.year}-${salesOrderInstance?.month?.month}
								</td>
								<td>
									${salesOrderInstance?.customer?.name}
								</td>
								<td>
									${fieldValue(bean: salesOrderInstance, field: "orderFormNo")}
								</td>
								<td><g:formatDate format="yyyy-MM-dd"
										date="${salesOrderInstance?.timeByDay?.date}" /></td>
								<td>
									${salesOrderInstance.gasType?.category?.name}
								</td>
								<td>
									${salesOrderInstance.gasType.name}
								</td>
								<td>
									${salesOrderInstance.quantity}
								</td>
								<td><g:formatNumber
										number="${salesOrderInstance.purchasingUnitPrice}"
										format=".###" /></td>
								<td><g:formatNumber
										number="${salesOrderInstance.quantity * salesOrderInstance.purchasingUnitPrice}"
										format=".###" /></td>
								<td>
									${salesOrderInstance?.customer?.customerType?.level2?.level1?.name}
								</td>
								<td>
									${salesOrderInstance?.customer?.customerType?.level2?.displayName}
								</td>
								<td>
									${salesOrderInstance?.customer?.customerType?.displayName}
								</td>
								<td>
									${salesOrderInstance?.listUnitPrice}
								</td>
								<td>
									${salesOrderInstance?.sales?.name}
								</td>
								<td>
									${salesOrderInstance?.bigCase?.name}
								</td>
								<td>
									${salesOrderInstance?.salingtype?.id?.intValue() == 1 ? "是":"否"}
								</td>
								
								
								 <td>
								 <g:if test="${salesOrderInstance?.status =='ABLE' && salesOrderInstance?.isClosed==false}">
								 <g:if test="${!SalesOrder.findByUpdateFrom(salesOrderInstance.id) }">
					      <a href="${createLink(action:'delete',controller:'salesOrder',id:salesOrderInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除销售台账"></span></a>
		 
		&nbsp;&nbsp;
		
		
		 <g:link rel="external" action="edit" id="${salesOrderInstance?.id}" params="${params }" data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新销售台账"></span></g:link>
		   </g:if> 
		    </g:if>
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
			</div>
			   <div class="list-page" id="paginateButtons">
				   <g:paginate total="${salesOrderInstanceTotal}" params="${params}"  class="pagination"/>
			  </div>
		</div>
		
	</section>
  </div>
</body>
</html>

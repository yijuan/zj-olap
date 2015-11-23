<%@ page import="com.surelution.zjolap.SalesOrder" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<r:require module="jquery-ui"/>
		<title><g:message code="changeFMstatusOpen.label" /></title>
		<script type="text/javascript">
		
			  $(document).ready(function()
				     {

			          $("#fmBranch").change(loadData);
			          
			          $("#fmYear").change(loadData);
			          loadData();
			        })
			        
			        
			        function loadData() {
			        	  var year =  $(this).children('option:selected').val();
				          var branchId =  $("#fmBranch").children('option:selected').val();
				         // $('#fmClosed').empty();
				          $('#fmOpened').empty();
				          if(year && branchId) {
				          $.ajax({
								url:'${createLink(uri:'/customerStock/findFM')}',
								dataType:"json",
								data:{year:year,branchId:branchId},
								success:function(data) {
									var closeHtml = "";
									var openHtml ="";
			                         $.each(data, function(commentIndex, comment){
											  if(comment.isClosed) {
				                             	 closeHtml +="<option value='"+ comment.month +"'>"+comment.month+"</option>";
											  }else {
												  openHtml +="<option value='"+ comment.month +"'>"+comment.month+"</option>";
											  }
				                         });
									 $('#fmClosed').html(closeHtml);
									//$('#fmOpened').html(openHtml);
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
					开启账期 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 客存</a></li>
					<li class="active">开启账期</li>
				</ol>
				</section>
	<section class="content">
		<div class="box box-info">
                <div class="box-header with-border">
                  <h3 class="box-title">账期</h3>
                </div>
                
                 <g:if test="${flash.message}">
			     <div class="message" role="status" style="color:red; font-weight:bold;">${flash.message}</div>
			   </g:if>
			
                <div class="box-body">
		<div id="create-salesOrder">
			
			<g:form action="changeFMstatus">
					<fieldset class="form">
						<div class="fieldcontain">
							
							<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="fmBranch">
								<g:message code="salesOrder.branch.label" default="Branch" />
							</label></dt>
	                           <dd>
	                               <div class="col-md-3">
	                                 <g:select id="fmBranch" name="fmBranch" value="${params.fmBranch}" from="${com.surelution.zjolap.Branch.list()}" noSelection="['':'所有公司']" optionKey="id" optionValue="name"  class="many-to-one form-control"/>
	                               </div>
	                            </dd>
	                        </dl>
	                        
	                        
	                        <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="fmYear">
								年份
							</label></dt>
	                           <dd>
	                               <div class="col-md-3">
	                               <g:select id="fmYear" name="fmYear" value="${params.fmYear}" from="${com.surelution.zjolap.FinancialMonth.getDistinctYear()}" noSelection="['':'全部']" optionKey="year" optionValue="year"  class="many-to-one form-control"/>
	                               </div>
	                            </dd>
	                        </dl>
							
							
							 <dl class="dl-horizontal" style="margin-left:-70px;">
	                           <dt><label for="fmClosed">
								已经关闭的账期
							</label></dt>
	                           <dd>
	                               <div class="col-md-3" style="margin-left:-10px;">
	                               <select name="fmClosed" id="fmClosed" class="form-control">
							
							</select>
							
	                               </div>
	                            </dd>
	                        </dl>
							
						
							<g:submitButton name='openBtn' value="开启账期" class="btn btn-primary"/>
						</div>
						</fieldset>
					
					
				</g:form>
				</div>
				</div>
		</div>
		</section>
		</div>
	</body>
</html>

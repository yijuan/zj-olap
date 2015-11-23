
<%@ page import="com.surelution.zjolap.ExcelUpload"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="jarvis">
<title>新客户数量</title>
<r:require module="jquery-ui"/>

</head>
<body>
<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h3>
					客户 <small></small>
				</h3>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 客户</a></li>
					<li class="active">新客户数量</li>
				</ol>
				</section>
	<section class="content">
	<div class="box box-info">
                <div class="box-header with-border">
                 <h4>新客户数量</h4>
                </div>
                <div class="box-body">
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>

		<g:form action="listNewCustomerCount">
			<fieldset class="form">
				<sec:ifAnyGranted roles="ROLE_ADMIN">

					<div class="fieldcontain ">
					<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch"> <g:message
								code="salesOrder.branch.label" default="Branch" />
						</label></dt>
	                           <dd>
	                               <div class="col-md-2" style="margin-left:-10px;">
	                                 <g:select id="branch" name="branchId"
							from="${com.surelution.zjolap.Branch.list()}"
							noSelection="['0':'全部']" optionKey="id" optionValue="name"
							value="${params['branchId']}" class="many-to-one form-control" />
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
								<g:message code="newCustomer.date.label" default="time" />
							</label></dt>
	                           <dd>
	                              
	                               <div class="col-xs-3" style="margin-left:-10px;">
	                               <g:textField name="timeFrom" value="${params['timeFrom']}" id="datetimepicker" class="form-control" style="width:148px;"/>
						 <script type="text/javascript">
                $('#datetimepicker').datetimepicker({
                	isRTL: false,
                    format: 'yyyy-mm-dd hh-ii',
                    autoclose:true,
                    language: 'zh-CN'
					});
        </script>
						</div>
						<div class="col-xs-1">~</div>
						<div class="col-xs-4" style="margin-left:-30px;">
						<g:textField name="timeTo" value="${params['timeTo']}" id="datetimepicker1" class="form-control" style="width:148px;"/>
						 <script type="text/javascript">
                $('#datetimepicker1').datetimepicker({
                	isRTL: false,
                    format: 'yyyy-mm-dd hh-ii',
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
				
				<div class="row">		
				<div class="fieldcontain">
				<div class="col-md-3">
				<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="isBranch"> <g:message code="isBranch.label"
							default="isBranch" />
					</label></dt>
	                           <dd>
	                               <div class="col-md-3">
	                                 <g:checkBox name="isBranch" value="${params['isBranch']}" />
	                               </div>
	                            </dd>
	                        </dl>
					</div>
					
				  <div class="col-md-3">
				     <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="isMonth"> <g:message code="isMonth.label"
							default="isMonth" />
					</label></dt>
	                           <dd>
	                               <div class="col-md-5">
	                                 <g:checkBox name="isMonth" value="${params['isMonth']}"/>
	                               </div>
	                            </dd>
	                        </dl>
					
					
					</div>
					
				</div>
				</div>
				<fieldset class="buttons">
					<g:submitButton name="search" class="search btn btn-primary"
						value="${message(code: 'default.button.search.label', default: 'Search')}" />
				</fieldset>
			</fieldset>
		</g:form>
		</div>
</div>
		<table  class="table table-bordered  table-striped">
			<thead style="background-color:#3C8DBD">
				<tr>
					<th style="color: white">分公司</th>
					<th style="color: white">时间</th>
					<th style="color: white">数量</th>

				</tr>
			</thead>
			<tbody>
				<g:each in="${reesultList}" status="i" var="nc">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">


						<td>
							${nc?.branch}
						</td>
						<td>
							${nc?.year} <g:if test="${nc?.month} ">
								<g:formatNumber number="${nc?.month}" format="-00" />
							</g:if>
						</td>

						<td>
							${nc?.count}
						</td>

					</tr>
				</g:each>
			</tbody>
		</table>
		<div style="height: 50px;">
			<div class="list-page" id="paginateButtons">
			<g:paginate total="${resultCount}" params="${params}" class="pagination"/>
		</div>
		</div>
	</section>
	</div>
</body>
</html>

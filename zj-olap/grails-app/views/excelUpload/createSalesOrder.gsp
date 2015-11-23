<%@ page import="com.surelution.zjolap.ExcelUpload" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'excelUpload.label', default: 'ExcelUpload')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<r:require module="jquery-ui"/>
	</head>
	<body>
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					销售数据上传 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 数据上传</a></li>
					<li class="active">销售数据上传</li>
				</ol>
				</section>
	<section class="content">
	 <sec:ifAnyGranted roles="ROLE_ADMIN">
	 <div class="c1" style=" height: 30px; border-bottom: 1px solid #e5e5e5;margin-bottom:10px;">
	
            <span class="glyphicon glyphicon-folder-close" aria-hidden="true"></span>
			
			<g:link class="list" action="list">销售数据上传列表</g:link>
			
       </div>
	</sec:ifAnyGranted>
	<div class="box box-info">
                <div class="box-header with-border">
                  <h3 class="box-title">销售数据</h3>
                </div>
                
                 <g:if test="${flash.message}">
			     <div class="message" role="status">${flash.message}</div>
			   </g:if>
			
                <div class="box-body">
		<div id="create-excelUpload">
			<g:each in="${excelMessages }" var="excelMessage">
				<p style="color: red">${excelMessage.row + 1 }行${excelMessage.column }列${excelMessage.message }</p>
			</g:each>
			
			<g:uploadForm action="saveSalesOrder">
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: excelUploadInstance, field: 'uploadedAt', 'error')} required">
						 <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="uploadedAt">
							<g:message code="excelUpload.file.label" default="File name" />
							<span class="required-indicator">*</span>
						</label></dt>
	                           <dd>
	                               <div class="col-md-3">
	                              <input type="file" name="excelFile" class="form-control" accept="application/vnd.ms-excel"/>
	                               </div>
	                               
	                               <div class="col-md-3"><p style="color: red; font-weight: bold; line-height: 30px;">文件的类型为：.xls格式</p></div>
	                            </dd>
	                        </dl>			
					</div>
					
					<div>
					   <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="excelMonth">
							结算月
							
						</label></dt>
	                           <dd>
	                               <div class="col-md-3">
	                             <g:textField name="excelMonth" value="${params.month}" id="datetimepicker1" class="form-control"/>
	                               <script type="text/javascript">
                $('#datetimepicker1').datetimepicker({
                	isRTL: false,
                	format: "yyyy-mm",
                    autoclose:true,
                    minView: 'year',
                    language: 'zh-CN'
					});
        </script>
	                               
	                               </div>
	                            </dd>
	                        </dl>
					</div>
				</fieldset>	
				<fieldset class="buttons">
					<g:submitButton name="create" class="save btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:uploadForm>
			</div>
			</div>
		</div>	
		</section>
		</div>
	</body>
</html>

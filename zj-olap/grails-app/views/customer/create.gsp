<%@ page import="com.surelution.zjolap.Customer" %>
<!DOCTYPE html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		
		<script type="text/javascript" charset="utf-8">
			function addFileUpload() {
				 var div = document.getElementById("divFiles");
				 var file = document.createElement("input");
				 var br = document.createElement("br");
					file.type='file';
					file.name = "file"+((Math.random()*(10000-100)+100));
					div.appendChild(file);
					div.appendChild(br);
			}
		</script>
	</head>
	<body>
	<div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">新建客户</h4>
        </div>
			<g:uploadForm action="save">
		<div class="modal-body">
				<fieldset class="form">
					<g:render template="form"/>
					<div>
					<dl class="dl-horizontal">
	   <dt><label for="license">
							  附件：
						</label></dt>
	   <dd>
	      <div class="col-xs-8"> 
	   <input type="button" value='增加附件' id="btnAddFile" onclick="addFileUpload()" class="btn btn-primary"/>
	      </div>
	   </dd>
	</dl>	   
					     <div id='divFiles' style="margin-left:30%;">
					     
					     </div>
					   </div>
				</fieldset>	
		 </div>	
		 <div class="modal-footer">		
				<fieldset class="buttons">
					<g:submitButton name="create" class="save btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
					<g:actionSubmit name="create" action="saveD" class="save btn btn-primary" value="临时保存" />
				</fieldset>
		</div>
			</g:uploadForm>
	</body>
</html>

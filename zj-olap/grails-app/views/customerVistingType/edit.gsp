<%@ page import="com.surelution.zjolap.CustomerVistingType" %>
<!DOCTYPE html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'customerVistingType.label', default: 'CustomerVistingType')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		
		
		
	</head>
	
	
	<body>
	 <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">更新回访方式</h4>
        </div>
        
        
        <g:form action="update" method="post">
		<div class="modal-body">
				<g:hiddenField name="id" value="${customerVistingTypeInstance?.id}" />
				<g:hiddenField name="version" value="${customerVistingTypeInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
					<div id="message" style="margin-left:150px;"></div>
				</fieldset>
		 </div>	
		 <div class="modal-footer">		
				<fieldset class="buttons">
					<input type="submit" class="save btn btn-primary"  value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
				</fieldset>
		</div>
			</g:form>
			
			<script type="text/javascript">
        $(document).ready(function(){
        	$("#name").change(function(){
                var name = $("#name").val();
                var url ="${createLink(action:'checkName',controller:'customerVistingType')}";
                $.ajax({
		        type : "post",
		        url :url , 
		        data: {
		            name:name
		        },
		        dataType : "json",
		        //async:false,
		        success : function(data) {
		              if(data==true){
		            	$("#message").css("color","red")
                        $("#message").html("该回访方式已经创建，请更换回访方式")
                        $('input[type="submit"]').prop('disabled', true);
			              }else{
			            $("#message").css("color","green")
			            $("#message").html("该回访方式可以使用")
			            $('input[type="submit"]').prop('disabled', false);
				              }
		        }  
		    });  
                });
            
            });
            
      </script>
			
	</body>
</html>

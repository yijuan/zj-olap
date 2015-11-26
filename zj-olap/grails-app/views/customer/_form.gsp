<%@page import="com.surelution.zjolap.CustomerTypeLevel3"%>
<%@ page import="com.surelution.zjolap.Customer" %>
<%@page import="com.surelution.zjolap.CustomerType"%>

<script type="text/javascript">
	$(document).ready(function()
	    {
		loadType2();
	    });

	function loadType2() {
		var typeID =  $('#customerTypeID').children('option:selected').val();
		var type2 =  ${customerInstance?.customerType?.level2?.id} +"";
		$('#customerType2ID').empty();
		$('#customerType3ID').empty();
		
		 $.ajax({
			    url:'${createLink(uri:'/customerType/loadType2')}',
				dataType:"json",
				data:{typeID:typeID},
				
				success:function(data) {
					var html = "";
                         $.each(data, function(commentIndex, comment){
							if(type2 ==comment.id ) {
								html +="<option selected='selected' value='"+ comment.id +"'>"+comment.name+"</option>";
							}else {
								html +="<option  value='"+ comment.id +"'>"+comment.name+"</option>";
							}
                         });
					 $('#customerType2ID').html(html);
					loadType3();
				}
	          });
	}

	function loadType3() {
		var typeID =  $('#customerType2ID').children('option:selected').val();
		var type3 =  ${customerInstance?.customerType?.id} +"";
		$('#customerType3ID').empty();
		 $.ajax({
			 url:'${createLink(uri:'/customerType/loadType3')}',
				dataType:"json",
				data:{typeID:typeID},
				
				success:function(data) {
					var html = "";
                         $.each(data, function(commentIndex, comment){
							if(type3 ==comment.id ) {
								html +="<option selected='selected' value='"+ comment.id +"'>"+comment.name+"</option>";
							}else {
								html +="<option  value='"+ comment.id +"'>"+comment.name+"</option>";
							}
						                       
						  });
					 $('#customerType3ID').html(html);
				
				}
	          });
	}



	function chkimg(inp)
	{
		//alert(1);
		
		var hz = inp.substring(inp.lastIndexOf(".")+1);
		
		var patrn =/^(gif|jpg|jpeg|png|ico|bmp|GIF|JPG|JPEG|PNG|ICO|BMP){1}$/;
	    if(!patrn.test(hz)){
		    document.getElementById("licenseFile").value="";
			alert("营业执照必须是图片格式");
        }
		
	}

	
</script>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'name', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="name">
		<g:message code="customer.name.label" default="Name" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	     <g:textField name="name" value="${customerInstance?.name}" class="form-control" required=""/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'address', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="address">
		<g:message code="customer.address.label" default="address" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	    <g:textField name="address" value="${customerInstance?.address}" class="form-control" required=""/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'tel', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="tel">
		<g:message code="customer.tel.label" default="tel"  />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	    <g:textField name="tel" value="${customerInstance?.tel}" class="form-control" required=""/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'license', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="license">
		<g:message code="customer.license.label" default="license" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	   <g:textField name="license" value="${customerInstance?.license}" class="form-control"/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'license', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="license">
		<g:message code="customer.licensePicUrl.label" default="license" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-8">
	   <input type="file" id='licenseFile' name="licenseFile"  onchange="chkimg(this.value)" class="form-control"/>
	    <span style="color:red;">(只能上传图片格式的文件)</span>
	      </div>
	   </dd>
	</dl>	
	
   
   </div>

   
   
<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'customerType', 'error')} ">
	<dl class="dl-horizontal">
	   <dt><label for="customerType">
		<g:message code="customer.customerType.label" default="Customer Type" />
		
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	 <div style="display: none;">
	<%--<g:select id="customerType" name="customerType.id" from="${CustomerTypeLevel3.list() }" optionKey="id" optionValue="name" value="${customerInstance?.customerType?.id}"/>
	--%></div>
	<div class="row">
	<div class="col-md-3">
	<g:select id="customerTypeID" name="customerTypeID" from="${CustomerType.list() }" optionKey="id" optionValue="name" onchange="loadType2()"  value="${customerInstance?.customerType?.level2?.level1?.id}" class="form-control" style="width:150px;"/>
	</div>
	<div class="col-md-4">
	<select id="customerType2ID" name="customerType2ID" onchange="loadType3()" class="form-control" style="width:220px;">
	
	</select>
	</div>
	<div class="col-md-4">
	<select id="customerType3ID" name="customerType.id" class="form-control" style="width:150px;">
	
	</select>
	</div>
	</div>
	      </div>
	   </dd>
	</dl>	
</div>


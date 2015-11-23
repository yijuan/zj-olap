<%@ page import="com.surelution.zjolap.WarningRuleToBranchCustomer" %>

<head>
<script type="text/javascript">
    $(document).ready(function(){
    	$("#opener").autocomplete({		
    		  source: function(request, response){
    		   $.ajax({		  			
    		    url: "${createLink(action:'selector',controller:'customer')}", // remote datasource
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
    		   $("#customerBranch\\.id").val(ui.item.id); // update the hidden field.
    		  }
    		 });	 
        })
        
</script>


<style type="text/css">
  .ui-autocomplete {
    z-index: 5000;
} 
.ui-widget-content {
border: 1px solid #dddddd;
background: #eeeeee url("resource:/css/images/ui-bg_highlight-soft_100_eeeeee_1x100.png") 50% top repeat-x;
color: #333333;
}
.ui-widget {
font-family: Trebuchet MS,Tahoma,Verdana,Arial,sans-serif;
font-size: 1.1em;
}
.ui-menu {
list-style: none;
padding: 0;
margin: 0;
display: block;
outline: none;
}
.ui-autocomplete {
position: absolute;
top: 0;
left: 0;
cursor: default;
}
li {
display: list-item;
text-align: -webkit-match-parent;
}
.ui-menu .ui-menu-item {
position: relative;
margin: 0;
padding: 3px 1em 3px .4em;
cursor: pointer;
min-height: 0;
/* support: IE7; */
/* support: IE10, see #8844; */
list-style-image: url("data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7");
}
</style>

</head>

<div class="fieldcontain ${hasErrors(bean: warningRuleToBranchCustomerInstance, field: 'customerBranch', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="customerBranch">
		<g:message code="warningRuleToBranchCustomer.customer.label" default="Customer Branch" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	      <input id="opener" value="${warningRuleToBranchCustomerInstance?.customerBranch?.customer?.name }" class="form-control">
	      <input type="hidden" name="customerBranch.id" id="customerBranch.id" value="${warningRuleToBranchCustomerInstance?.customerBranch?.id }"/>
	      </div>
	   </dd>
	</dl>	
</div>

<div class="fieldcontain ${hasErrors(bean: warningRuleToBranchCustomerInstance, field: 'rule', 'error')} required">
	<dl class="dl-horizontal">
	   <dt><label for="rule">
		<g:message code="warningRuleToBranchCustomer.rule.label" default="Rule" />
		<span class="required-indicator">*</span>
	</label></dt>
	   <dd>
	      <div class="col-xs-10">
	      <g:select id="rule" name="rule.id" from="${com.surelution.zjolap.WarningRule.getListByBranch("${branch?.id}")}" optionKey="id" required=""  optionValue="${{it?.gasType?.category?.name+'>>'+it?.gasType?.name+'>>'+it?.typeName +'>>'+it?.value}}" value="${warningRuleToBranchCustomerInstance?.rule?.id}" class="many-to-one form-control"/>
	      </div>
	   </dd>
	</dl>			
</div>
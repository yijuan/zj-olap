<%@ page import="com.surelution.zjolap.WarningRuleToBranchCustomer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-warningRuleToBranchCustomer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="show-warningRuleToBranchCustomer" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			
			<div class="dialog">
				<table>
	                    <tbody>
	                    	<tr class="prop">
	                            <td valign="top" class="name"><g:message code="warningRuleToBranchCustomer.customer.label" default="Customer Branch" /></td>
	                            
	                            <td valign="top" class="value">${warningRuleToBranchCustomerInstance?.customerBranch?.customer?.name}</td>
	                            
	                        </tr>
	                         <tr class="prop">
	                            <td valign="top" class="name"> 油品类型</td>
	                            
	                            <td valign="top" class="value">${warningRuleToBranchCustomerInstance?.rule?.gasType?.category?.name}</td>
	                            
	                        </tr>
	                         <tr class="prop">
	                            <td valign="top" class="name">油品品号</td>
	                            
	                            <td valign="top" class="value">${warningRuleToBranchCustomerInstance?.rule?.gasType?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="warningRuleToBranchCustomer.rule.label" default="Rule" /></td>
	                            
	                            <td valign="top" class="value">${warningRuleToBranchCustomerInstance?.rule?.typeName}>>${warningRuleToBranchCustomerInstance?.rule?.value}</td>
	                            
	                        </tr>
	                        
	                        
	                	</tbody>
	                </table>
			</div>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${warningRuleToBranchCustomerInstance?.id}" />
					<g:actionSubmit class="edit" action="edit" value="${message(code:'default.button.edit.label', default:'Edit')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
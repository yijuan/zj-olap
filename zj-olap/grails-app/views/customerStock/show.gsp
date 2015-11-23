
<%@ page import="com.surelution.zjolap.CustomerStock" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'customerStock.label', default: 'CustomerStock')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-customerStock" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
			<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
		</div>
		<div id="show-customerStock" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			
			
			<div class="dialog">
				<table>
	                    <tbody>
	                    	<tr class="prop">
	                            <td valign="top" class="name"><g:message code="customerStock.branch.label" default="Branch" /></td>
	                            
	                            <td valign="top" class="value">${customerStockInstance?.customerBranch?.branch?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customerStock.customer.label" default="Customer" /></td>
	                            
	                            <td valign="top" class="value">${customerStockInstance?.customerBranch?.customer?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customerStock.cdate.label" default="Date" /></td>
	                            
	                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd" date="${customerStockInstance?.date?.date}"/></td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customerStock.gasType.label" default="Gas Type" /></td>
	                            
	                            <td valign="top" class="value">${customerStockInstance?.gasType?.category?.name }'>>' ${customerStockInstance?.gasType?.name}</td>
	                            
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="customerStock.stockQty.label" default="Stock Qty" /></td>
	                            
	                            <td valign="top" class="value"><g:fieldValue bean="${customerStockInstance}" field="stockQty"/></td>
	                            
	                        </tr>
	                        
	                	</tbody>
	                </table>
	               
  </div>
	                
			</div>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${customerStockInstance?.id}" />
					<g:actionSubmit class="edit" action="edit" value="${message(code:'default.button.edit.label', default:'Edit')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

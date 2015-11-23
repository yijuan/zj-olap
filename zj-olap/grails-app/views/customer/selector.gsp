
<%@ page import="com.surelution.zjolap.Customer" %>

<!DOCTYPE html>
			<table>
				<thead>
					<tr>
						<%--<g:sortableColumn property="id" title="${message(code: 'customer.id.label', default: 'Id')}" params="${params}"/>
					
						--%><g:sortableColumn property="name" title="${message(code: 'customer.name.label', default: 'Name')}" params="${params}"/>
						
						<g:sortableColumn property="tel" title="${message(code: 'customer.tel.label', default: 'tel')}" params="${params}"/>
					   
					    <th>
					    	${message(code: 'customer.customerType.label', default: 'customerType')}
					    </th>
					    <th>
					    	所属分公司
					    </th>
					    
					</tr>
				</thead>
				<tbody>
				<g:each in="${customerInstanceList}" status="i" var="customerInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}" ondblclick="javascript:select('${customerInstance.customer.id}','${customerInstance.customer.name}')">
					
						<td><a href="javascript:select('${customerInstance.customer.id}','${customerInstance.customer.name}')">${customerInstance.customer.name}</a></td>
						
						<td>${customerInstance.customer.tel}</td>
						
						<td>${customerInstance.customer?.customerType?.name}</td>
						
						<td>${customerInstance.branch?.name}</td>
					</tr>
				</g:each>
				</tbody>
			</table>

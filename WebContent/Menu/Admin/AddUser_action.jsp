<%@ page language="java" import="java.sql.*,apec.*, apec.users.*"%>
<jsp:useBean id="admin" class="apec.users.Admin" scope="session"/>
<jsp:useBean id="customerIdentity" class="apec.users.CustomerIdentity" scope="request"/>
<jsp:setProperty name="customerIdentity" property="*"/>

<%
	String pass = request.getParameter("retype_password");
	if(pass.equals(customerIdentity.getPassword()) && customerIdentity.noNulls()){
		admin.addCustomer(customerIdentity);
	}
	response.sendRedirect("UserManagment.jsp");
%>
<%@ page language="java" import="java.sql.*,apec.*"%>
<jsp:useBean id="emp" class="apec.Admin" scope="session"/> 
<jsp:setProperty name="emp" property="*"/>
<%       
    boolean validUser = emp.login();
	DatabaseConnection.closeDBConnection();
    if(validUser && emp.isAdmin())   
        response.sendRedirect("Menu/AdminMenu.html");
    else if(validUser)
        response.sendRedirect("Menu/CustomerMenu.html");
    else
    	response.sendRedirect("Login.html");
%> 
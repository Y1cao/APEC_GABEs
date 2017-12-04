<%@ page language="java" import="java.sql.*,apec.*, apec.users.*, java.util.*"%>
<jsp:useBean id="admin" class="apec.users.Admin" scope="session"/>

<html>
	<link rel = "stylesheet"
  		  type = "text/css"
   		  href = "../../styles.css" />
   		  
	<head>
		<meta content="text/html; charset=ISO-8859-1"
		http-equiv="content-type">
				<title >User Managment</title>
	</head>
	
	<body>
		<div style="text-align:center;">
			<h1>User Managment</h1>
			<div>
				<h2>User List</h2>
				<table>
					<tr>
						<th>User ID</th>
						<th>Username</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email</th>
						<th>Password</th>
					</tr>
					<%   
						ArrayList<CustomerIdentity> customers = admin.getCustomers();
						if(customers == null)
							response.sendRedirect("../../Login.html"); //go back to the login, the person doesnt have the proper access rights
						CustomerIdentity temp;
						for(int i=0; i<customers.size(); i++){
							temp = customers.get(i);
							%>
								<tr>
									<td><%=temp.getCustomerID()%></td>
									<td><%=temp.getUsername()%></td>
									<td><%=temp.getFirst_name()%></td>
									<td><%=temp.getLast_name()%></td>
									<td><%=temp.getEmail()%></td>
									<td><%=temp.getPassword()%></td>
								</tr>
							<%
						}
					%>
				</table>
			</div>
		</div>
	</body>
</html>
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
						int highest = customers.size();
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
			<div id="AddUserDiv">
				<h2>Add User</h2>
				<form method="post" action="AddUser_action.jsp" name="AddUser">
					<table id="AddUser">
						<tr><td>User ID</td><td><input name="customerID" value=<%=highest%> readonly> </td></tr>
						<tr><td>Username</td><td><input name="username" value=""> </td></tr>
						<tr><td>First Name</td><td><input name="first_name" value=""> </td></tr>
						<tr><td>Last Name</td><td><input name="last_name" value=""> </td></tr>
						<tr><td>Email</td><td><input name="email" value=""> </td></tr>
						<tr><td>Password</td><td><input name="password" value=""> </td></tr>
						<tr><td>Retype Password</td><td><input name="retype_password" value=""> </td></tr>
					</table>
					<br><input name="AddUser" value="Add User" type="submit">
				</form>
			</div>
		</div>
	</body>
</html>
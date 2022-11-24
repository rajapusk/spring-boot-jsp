<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%= "Hello World!" %>
<% out.print("welcome to jsp"); %> 

Current Time: <%= java.util.Calendar.getInstance().getTime() %>

<%
double principal =100000 ;      

double rate =8 ;
      
double time =1 ;

rate=rate/(12*100); 

time=time*12; 
    
double emi= (principal*rate*Math.pow(1+rate,time))/(Math.pow(1+rate,time)-1);
out.print(" Math.pow(1+rate,time)= "+Math.pow(1+rate,time)+"\n");
out.print(" Math.pow(1+rate,time)-1= "+(Math.pow(1+rate,time)-1)+"\n");
out.print(" EMI is= "+emi+"\n");
%>

</body>
</html>
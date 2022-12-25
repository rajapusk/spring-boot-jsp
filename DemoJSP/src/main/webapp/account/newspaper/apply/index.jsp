<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>NEWSPAPER ALLOWANCE</title>
		<%@include file="../../header.jsp" %>
		
	    <link rel="stylesheet" href="newspaper.css" type="text/css" />
	    <script type="text/javascript" src="newspaper.js"></script>
	    <script>	    	
		    $(document).ready(function () {
		    	let page = new Newspaper();
		    			    	
		    	page.init();
		   	});
	    </script>
	</head>
	<body>
		<div class="scCenterXY">		    
			<div id='dvRefundablePFLoan' class="scRefundablePFLoan" style="width: 100%; height: auto;"></div>
		</div>	
	</body>
</html>
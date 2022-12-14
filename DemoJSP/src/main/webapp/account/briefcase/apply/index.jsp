<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>Refundable PF Loan</title>
		<%@include file="../../header.jsp" %>
		
	    <link rel="stylesheet" href="briefCase.css" type="text/css" />
	    <script type="text/javascript" src="briefCase.js"></script>
	    <script>	    	
		    $(document).ready(function () {
		    	let page = new BriefCase();
		    			    	
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
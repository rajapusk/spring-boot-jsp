<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>VPF Contribution</title>		
	    <%@include file="../../header.jsp" %>
	    
	    <link rel="stylesheet" href="vpf-apply.css" type="text/css" />
	    <script type="text/javascript" src="vpf-apply.js"></script>
	    <script>	    	
		    $(document).ready(function () {
		    	var page = new VPFApply();
		    			    	
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
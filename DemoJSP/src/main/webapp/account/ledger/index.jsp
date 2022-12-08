<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>ER Ledger</title>
		<%@include file="../header.jsp" %>
	    <script type="text/javascript" src="ledger.js"></script>
	    <script>
		    $(document).ready(function () {	
		    	let pgLedger = new Ledger();
		    	
		    	pgLedger.init();
		   	});
	    </script>
	</head>
	<body>
		<div>			
			<div id='dvPFAccount' style="width: 100%; height: auto; margin: auto;"></div>			
		</div>
		<div>			
			<div id='dvMasterLedger' style="width: 100%; height: auto; margin: auto;"></div>			
		</div>	
	</body>
</html>
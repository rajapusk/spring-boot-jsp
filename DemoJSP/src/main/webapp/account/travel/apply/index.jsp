<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>TRAVEL EXPENSE</title>		
	    <%@include file="../../header.jsp" %>
	    
	    <link rel="stylesheet" href="travel-apply.css" type="text/css" />
	    <script type="text/javascript" src="travel-apply.js"></script>
	    <script type="text/javascript" src="travel-list.js"></script>
	    <script>	    	
		    $(document).ready(function () {
		    	let page = new TravelApply();
				
		    	page.init();
		   	});
	    </script>
	</head>
	<body>
		<div>
			<div class="scCenterXY">		    
				<div id='dvRefundablePFLoan' class="scRefundablePFLoan" style="width: 100%; height: auto;"></div>
			</div>	
			
			<div id="dvActionPnl" class="scCenterXY" style="justify-content: flex-end; background: whitesmoke; padding: 10px 0px;">
				<input style="margin-right: 25px; width: 128px;" type="button" id="btnAdd" value="Add Expense"></input>
				<input style="margin-right: 25px; width: 128px;" type="button" id="btnSubmit"" value="Submit"></input>
			</div>
			
			<div style="padding: 25px;">
				<div id='dvPFAccount' style="width: 100%; height: auto; margin: auto;"></div>
				<div id="popupWindow"></div>
			</div>
		</div>
	</body>
</html>
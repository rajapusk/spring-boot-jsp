<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>VPF Contribution</title>		
	    <%@include file="../../header.jsp" %>
	    
	    <link rel="stylesheet" href="nominee-apply.css" type="text/css" />
	    <script type="text/javascript" src="nominee-apply.js"></script>
	    <script type="text/javascript" src="nominee-list.js"></script>
	    <script>	    	
		    $(document).ready(function () {
		    	let page = new NomineeApply();
				
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
				<input style="margin-right: 25px; width: 128px;" type="button" id="btnAddNominee" value="Add Nominee"></input>
				<input style="margin-right: 25px; width: 128px;" type="button" id="btnSubmit"" value="Submit"></input>
			</div>
			
			<div style="padding: 25px;">
				<div id='dvPFAccount' style="width: 100%; height: auto; margin: auto;"></div>
				<div id="popupWindow"></div>
			</div>
		</div>
	</body>
</html>
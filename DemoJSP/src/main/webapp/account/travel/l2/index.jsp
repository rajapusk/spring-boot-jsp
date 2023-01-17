<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>TRAVEL EXPENSE</title>
		<%@include file="../../header.jsp" %>
	    <script type="text/javascript" src="l2Manager.js"></script>
	    <script>
		    $(document).ready(function () {	
		    	let page = new TravelL2Manager();
		    	
		    	page.init();
		   	});
	    </script>
	</head>
	<body>
		<div>
			<div id="dvActionPnl" class="scCenterXY" style="justify-content: flex-end; padding: 10px;">
				<input style="margin-right: 25px; width: 100px;" type="button" id="btnSave" value="Approve"></input>
			</div>
			<div id='dvPFAccount' style="width: 100%; height: auto; margin: auto;"></div>
			<div id="popupWindow"></div>
		</div>	
	</body>
</html>
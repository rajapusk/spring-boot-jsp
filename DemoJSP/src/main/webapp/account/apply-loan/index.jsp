<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>Refundable PF Loan</title>
		<meta name="keywords" content="JavaScript Form, HTML Form, jQuery Forms widget" /> 
		<meta name="description" content="The jqxForm widget helps you build interactive HTML JSON forms. It offers rich functionality and layout options."/>
		<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1 minimum-scale=1" />
	    <link rel="stylesheet" href="../jqwidgets/styles/jqx.base.css" type="text/css" />
	    <link rel="stylesheet" href="../jqwidgets/styles/theme.css" type="text/css" />
	    <link rel="stylesheet" href="../jqwidgets/styles/jqx.light.css" type="text/css" />
	    <link rel="stylesheet" href="../style/common.css" type="text/css" />	 
	    <link rel="stylesheet" href="../style/font-awesome.min.css">   
	    <link rel="stylesheet" href="pfLoan.css" type="text/css" />	    
	    <script type="text/javascript" src="../scripts/jquery-1.12.4.min.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxcore.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxbuttons.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxinput.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxlistbox.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxscrollbar.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxpanel.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxdropdownlist.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxnumberinput.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxcalendar.js"></script>
    	<script type="text/javascript" src="../jqwidgets/jqxdatetimeinput.js"></script>
    	<script type="text/javascript" src="../jqwidgets/jqxtextarea.js"></script>    	
    	<script type="text/javascript" src="../jqwidgets/globalization/globalize.js"></script>
    	<script type="text/javascript" src="../jqwidgets/jqxnotification.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxfileupload.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxform.js"></script>
	    <script type="text/javascript" src="../common.js"></script>
	    <script type="text/javascript" src="pfLoan.js"></script>
	    <script>	    	
		    $(document).ready(function () {
		    	var page = new PFLoan();
		    			    	
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
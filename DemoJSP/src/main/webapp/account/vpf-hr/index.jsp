<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>PF Account</title>
		<meta name="keywords" content="JavaScript Form, HTML Form, jQuery Forms widget" /> 
		<meta name="description" content="The jqxForm widget helps you build interactive HTML JSON forms. It offers rich functionality and layout options."/>
		<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1 minimum-scale=1" />
	    <link rel="stylesheet" href="../jqwidgets/styles/jqx.base.css" type="text/css" />
	    <link rel="stylesheet" href="../jqwidgets/styles/theme.css" type="text/css" />
	    <link rel="stylesheet" href="../jqwidgets/styles/jqx.light.css" type="text/css" />
	    <link rel="stylesheet" href="../style/common.css" type="text/css" />
	    <script type="text/javascript" src="../scripts/jquery-1.12.4.min.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxcore.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxdata.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxgrid.js"></script>
    	<script type="text/javascript" src="../jqwidgets/jqxgrid.selection.js"></script>
    	<script type="text/javascript" src="../jqwidgets/jqxbuttons.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxscrollbar.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxgrid.pager.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxpanel.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxgrid.columnsresize.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxgrid.columnsreorder.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxcheckbox.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxgrid.selection.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxgrid.sort.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxdropdownlist.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxlistbox.js"></script>	
	    <script type="text/javascript" src="../jqwidgets/jqxmenu.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxgrid.edit.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxwindow.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxgrid.filter.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxbuttons.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxinput.js"></script>
	    <script type="text/javascript" src="../jqwidgets/jqxnotification.js"></script>
	    <link rel="stylesheet" href="../style/font-awesome.min.css">
	    <script type="text/javascript" src="../common.js"></script>
	    <script type="text/javascript" src="vpfHR.js"></script>
	    <script>
		    $(document).ready(function () {	
		    	var page = new VPFHR();
		    	
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
			<div id="popupWindow">
	            <div>Approve Loan</div>
	            <div class="scPopup">
	                <table style="width: 100%; padding: 0px 15px;">
	                    <tr>
	                        <td colspan="4" align="center"><img id="userAvatar" class="scAvatar"/></td>
	                    </tr>	                    
	                    <tr>
	                    	<td align="left">EMP Code</td>
	                        <td align="left">:<input id="winEmpCode" /></td>
	                        <td align="left">Name</td>
	                        <td align="left">:<input id="winName" /></td>
	                    </tr>	
	                    <tr>
	                        <td align="left">Monthly Salary</td>
	                        <td align="left">:<input id="winMonthlySALARY" /></td>
	                        <td align="left">Prev Net Salary</td>
	                        <td align="left">:<input id="winPrevNetSalary" /></td>
	                    </tr>	
	                    <tr>
	                        <td align="left">Prev Net Salary%</td>
	                        <td align="left">:<input id="winPreNetSalaryPer"/></td>
	                        <td align="left">Present VPF</td>
	                        <td align="left">:<input id="winPresentvfp" /></td>
	                    </tr>            
	                    <tr>
	                        <td align="left">Revised VPF</td>
	                        <td align="left">:<input id="winRevisedVPF" /></td>
	                        <td align="left">New Net Salary</td>
	                        <td align="left">:<input id="winNewNetSalary"></input></td>
	                    </tr>                      
	                    <tr>
	                        <td align="left">New Net Salary%</td>
	                        <td align="left">:<input id="winNewNetSalaryPer"></input></td>
	                        <td align="left">Approved</td>
	                        <td align="left"><div id='winApproved'></div></td>
	                    </tr>	                    
	                    <tr>
	                        <td align="left">Remarks</td>
	                        <td align="left">:<input id="winREMARKS"></input></td>
	                    </tr>
	                    <tr style="height: 50px;">
	                        <td colspan="4" style="padding-top: 20px; padding-bottom: 20px;" align="center">
		                        <input style="margin-right: 5px; width: 100px;" type="button" id="winSave" value="Save" />
		                        <input style="margin-right: 5px; width: 100px;" id="Cancel" type="button" value="Cancel" />
	                        </td>
	                    </tr>
	                </table>
	            </div>
	       </div>
		</div>	
	</body>
</html>
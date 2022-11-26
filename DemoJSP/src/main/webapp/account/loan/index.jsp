<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title id='Description'>PF Account</title>
		<%@include file="../header.jsp" %>
	    <script type="text/javascript" src="loan.js"></script>
	    <script>
		    $(document).ready(function () {	
		    	var page = new Loan();
		    	
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
	                        <td align="left">PF Balance</td>
	                        <td align="left">:<input id="winPfBalance" /></td>
	                        <td align="left">Interest</td>
	                        <td align="left">:<input id="winInterest" /></td>
	                    </tr>	                    
	                    <tr>
	                        <td align="left">RequiredAmount</td>
	                        <td align="left">:<input id="winRequiredAmount" /></td>
	                        <td align="left">RateOfInterest</td>
	                        <td align="left">:<input id="winRateOfInterest" /></td>
	                    </tr>	                    
	                    <tr>
	                        <td align="left">Prev Net Salary</td>
	                        <td align="left">:<input id="winPrevNetSalary" /></td>
	                        <td align="left">Prev Net Salary%</td>
	                        <td align="left">:<input id="winPreNetSalaryPer"/></td>
	                    </tr>	                    
	                    <tr>
	                        <td align="left">EMI Amount</td>
	                        <td align="left">:<input id="winEMIAmount" /></td>
	                        <td align="left">No Of EMI</td>
	                        <td align="left">:<input id="winNoOfEMI"></input></td>
	                    </tr>	                    
	                    <tr>
	                        <td align="left">New Net Salary</td>
	                        <td align="left">:<input id="winNewNetSalary"></input></td>
	                        <td align="left">New Net Salary%</td>
	                        <td align="left">:<input id="winNewNetSalaryPer"></input></td>
	                    </tr>	                    
	                    <tr>
	                        <td align="left">Approved</td>
	                        <td align="left"><div id='winApproved'></div></td>
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
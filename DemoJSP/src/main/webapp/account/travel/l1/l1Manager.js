var TravelL1Manager = function(){
	var theme =  'light';
	var Common = new CommonMethod(theme);
	var selectedRows = [];
	var theme = 'light';
	var editRowId =  -1;
	var popupConfig = [
		{name: 'winName', type: 'input', bind: 'name', label: 'Name', disabled: true},		
		{name: 'winGlCode', type: 'input', bind: 'glCode', label: 'GL Code', disabled: true},
		{name: 'winInvoiceAmount', type: 'input', bind: 'invoiceAmount', label: 'Invoice Amount', disabled: true},
		{name: 'winEntitledAmount', type: 'input', bind: 'advanceAmount', label: 'Advance Amount', disabled: true},
		{name: 'winclaimAmount', type: 'input', bind: 'totalAmount', label: 'Claimed Amount', disabled: true},
		{name: 'winEeRemarks', type: 'input', bind: 'remarks', label: 'EE Remarks', disabled: true},
		{name: 'winL1ManagerRemarks', type: 'input', bind: 'l1ManagerRemarks', label: 'L1 Remarks'},
		{name: 'winApproved', type: 'option', bind: 'approvedText', label: 'Approved', source: ['YES', 'NO', 'REJECTED']}
	];	
	
	var source = {
		datatype: 'json',
        datafields: [
			{name: 'id', type: 'string'},
            {name: 'empCode', type: 'string'},
            {name: 'name', type: 'string'},             
            {name: 'permittedName', type: 'string'},
            {name: 'permittedDate', type: 'string'},  
            {name: 'travelPurpose', type: 'string'},  
            {name: 'advanceAmount', type: 'number'}, 
            {name: 'totalAmount', type: 'number'},
            {name: 'remarks', type: 'string'},
            {name: 'l1ManagerRemarks', type: 'string'},
            {name: 'approved', type: 'booelan'},
            {name: 'travelExpenseDetailOutputs', type: 'object'}
       	],
        localdata: []
    };

	var sourceNested = {
		datatype: 'json',
        datafields: [
			{name: 'id', type: 'string'},
			{name: 'expenseDescription', type: 'string'},
            {name: 'travelStartDate', type: 'string'},
            {name: 'travelEndDate', type: 'string'},
            {name: 'distance', type: 'string'},
            {name: 'noOfDays', type: 'string'},
            {name: 'entitledAmount', type: 'number'},
            {name: 'billAvailable', type: 'string'},
            {name: 'billAvailableText', type: 'string'},
            {name: 'amountExclGST', type: 'number'},
            {name: 'cgstAmount', type: 'number'},
            {name: 'sgstAmount', type: 'number'},
            {name: 'igstAmount', type: 'number'},
            {name: 'totalAmount', type: 'number'},
            {name: 'claimAmount', type: 'number'},              
       	],
       	id: 'WithdrawID',
        localdata: []
    };

	var columnNested =  [
		Common.snoCell(),
		{ text: 'Expense Description', datafield: 'expenseDescription', width: '250px', editable: false },	
		{ text: 'Start Date & Time', datafield: 'travelStartDate', width: '150px', editable: false },
		{ text: 'End Date & Time', datafield: 'travelEndDate', width: '150px', editable: false },
		{ text: 'Distance (in KMS)', datafield: 'distance', width: '150px', editable: false },
		{ text: 'No of days', datafield: 'noOfDays', width: '150px', editable: false },
		{ text: 'Entitled Amount', datafield: 'entitledAmount', width: '150px', editable: false, renderer: Common.numberIconHeader },
		{ text: 'Bill Available', datafield: 'billAvailableText', width: '150px', editable: false },
		{ text: 'Amount (Excl GST)', datafield: 'amountExclGST', width: '150px', editable: false, renderer: Common.numberIconHeader },
		{ text: 'CGST Amount', datafield: 'cgstAmount', width: '150px', editable: false, renderer: Common.numberIconHeader },
		{ text: 'SGST Amount', datafield: 'sgstAmount', width: '150px', editable: false, renderer: Common.numberIconHeader },
		{ text: 'IGST', datafield: 'igstAmount', width: '150px', editable: false, renderer: Common.numberIconHeader },
		{ text: 'Total Amount', datafield: 'totalAmount', width: '150px', editable: false, renderer: Common.numberIconHeader },
		{ text: 'Claim Amount', datafield: 'claimAmount', width: '150px', editable: false, renderer: Common.numberIconHeader }		
	];
                
	var columns =  [
		Common.iconCell({icon: 'fa-eye', datafield: 'View'}),
	  	Common.snoCell(),
		{ text: 'EMP Code', datafield: 'empCode', width: 90, editable: false },
		{ text: 'Name', datafield: 'name', editable: false },      
		{ text: 'Travel purpose', datafield: 'travelPurpose', width: 120, editable: false },
		{ text: 'Permitted Name', datafield: 'permittedName', width: 120, editable: false },
		{ text: 'Permitted Date', datafield: 'permittedDate', width: 120, editable: false },
		{ text: 'Advance Amt', datafield: 'advanceAmount', editable: false, width: 120, cellsalign: 'right', renderer: Common.numberIconHeader },
		{ text: 'Claimed Amt', datafield: 'totalAmount', editable: false, width: 120, cellsalign: 'right', renderer: Common.numberIconHeader  },
		{ text: 'EE Remarks', datafield: 'remarks', width: 120, editable: false, cellsalign: 'left' },      
		{ text: 'L1 Remarks', datafield: 'l1ManagerRemarks', width: 120, editable: false, cellsalign: 'left'},
		{ text: 'Approved', datafield: 'approved', width: 70, threestatecheckbox: true, columntype: 'checkbox', editable: true},
		Common.iconCell({icon: 'fa-pencil',text: 'Action', datafield: 'Edit', width: 60})
    ];    
    
    var renderNestedGrid = function (index, parentElement, gridElement, record) {		
        var grid = $($(parentElement).children()[0]);
        
        if(record != null){
			sourceNested.localdata = record.travelExpenseDetailOutputs;
	        
	        if (grid != null) {
	            grid.jqxGrid({
	                source: new $.jqx.dataAdapter(sourceNested), 
	                width: '100%', 
	                height: 240,
	                sortable: true,
	                pageable: true,
	                columns: columnNested
	            });
	        }
		}
    }
    
	var renderGrid = function(){
		$("#dvPFAccount").jqxGrid({
            columns: columns,
            theme: theme,
            selectionmode: 'multiplerowsextended',
            sortable: true,
            pageable: true,
            autoheight: true,
            columnsresize: false,
            altrows: true,
            editable: true,
            filterable: true,
			rowdetails: true,
            source: new $.jqx.dataAdapter(source),
			initrowdetails: renderNestedGrid,
            rowdetailstemplate: { 
				rowdetails: "<div></div>", 
				rowdetailsheight: 220, 
				rowdetailshidden: true 
			},
            width: '100%'
        }).on('cellvaluechanged', function (event) 
		{
			var foundRow = false;
			var rowindex = event.args.rowindex;
		    
		    if(selectedRows != null){
				for(key in selectedRows){
					var row = selectedRows[key];
					
					if(row.rowId == rowindex){
						foundRow = true;
												
						row.rowData[event.args.datafield] = (event.args.newvalue == null ? 0 : 1);
												
						break;
					}
				}
			}
		    
		    if(!foundRow){	
				var rowData = $("#dvPFAccount").jqxGrid('getrowdata', rowindex);
							
				selectedRows.push({rowId: rowindex, rowData: rowData});
			}
		}).on('cellclick', function (event) 
		{
			if(event.args.datafield == 'Edit'){
	             editRowId = event.args.rowindex;
	             var xPos = (($("body").width() / 2) - ($("#popupWindow").width() / 2));
	             var yPos = 100;
	             var dataRecord = event.args.row.bounddata;	             
	             dataRecord.approvedText = (dataRecord.approved == 1 ? 'YES' : (dataRecord.approved == 2 ? 'REJECTED' : 'NO'));
	             
	             Common.renderInputs(dataRecord, popupConfig);
	             $("#popupWindow").jqxWindow({ position: { x: xPos, y: yPos } });
	             $("#popupWindow").jqxWindow('open');
			}
		});
		
		Common.renderPopup({
			title: "TRAVEL EXPENSE",
			colSpan: 2,
			column: popupConfig,
			buttons: [
				{ name: 'winSave', label: 'Add' },
				{ name: 'winCancel', label: 'Cancel' }
			]
		}, 'popupWindow');
		
        $("#popupWindow").jqxWindow({
            width: 750, height: 290, resizable: false,  theme: theme, isModal: true, autoOpen: false, cancelButton: $("#winCancel"), modalOpacity: 0.01           
        });
        
        renderModel();
        $("#btnSave").jqxButton({ theme: theme });     	
        $("#winCancel").jqxButton({ theme: theme, width: 100 });
        
        $("#btnSave").click(function () {
            if(selectedRows != null ) {				
                var postData = [];     
                
                for(var sKey in selectedRows){
					var row = selectedRows[sKey];
					
					postData.push({id: row.rowData.id, l1Approved: row.rowData.approved, remarks: row.rowData.l1ManagerRemarks})
				}
                
                if(postData.length > 0){
					approveLoan(postData);
				}
            }
        });
        
        loadData();
	}
	
	var renderModel = function(){
		Common.renderInputs(null, popupConfig);
        $("#winSave").jqxButton({ theme: theme, width: 100 });
        $("#winSave").click(function () {
            if (editRowId >= 0) {
				var sApproved = $("#winApproved").val();
				var sManagerRemarks = $("#winL1ManagerRemarks").val();
				var dataRecord = $("#dvPFAccount").jqxGrid('getrowdata', editRowId);
				var bApproved = (sApproved == 'YES' ? 1 : (sApproved == 'NO' ? 0 : 2));
				var rowID = $('#dvPFAccount').jqxGrid('getrowid', editRowId); 
				                          
                dataRecord.approved = bApproved;
                dataRecord.l1ManagerRemarks = sManagerRemarks;
                
                $('#dvPFAccount').jqxGrid('updaterow', rowID, dataRecord);
                $("#popupWindow").jqxWindow('hide'); 
                approveLoan([{ id: dataRecord.id, l1Approved: bApproved, remarks: sManagerRemarks}]);
            }
        });        
	}
	
	var formSuccessHandler = function(){
		selectedRows = [];
		Common.showToast({message: "The record has been submitted successfully."});
		loadData();
	}
	
	var approveLoan = function(postData){
		$.ajax({
			type: 'PUT',
			url: Common.HOST + '/travel/l1/update', 
			data: JSON.stringify(postData),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				formSuccessHandler();
			}
		});
	};
	
	var loadData = function(){
		$.ajax({
			type: 'GET',
			url: Common.HOST + '/travel/l1',
			contentType: "application/json; charset=utf-8",
			success: function(result){
				source.localdata = result;
				
				if(result != null){
					for(var sKey in result){
						var row = result[sKey];
						
						for(var sIdx in row.travelExpenseDetailOutputs){
							var expense = row.travelExpenseDetailOutputs[sIdx];
							
							expense.billAvailableText = (expense.billAvailable == 1 ? 'YES' : 'NO');
						}
					}
				}
				
				$("#dvPFAccount").jqxGrid('updatebounddata', 'cells');
			}
		});
	}
	
	this.init = function(){
		renderGrid();
	}
}

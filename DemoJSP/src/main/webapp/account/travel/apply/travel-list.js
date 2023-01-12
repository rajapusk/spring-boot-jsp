var TravelList = function(){
	var HOST = 'http://localhost:' + 9092;
	//var HOST = window.location.host;
	var selectedRows = [];
	var parent = null;
	var lodgeConfig = {};
	var haltingConfig = {};
	var formData = {};
	var listData = {};
	var theme = 'light';
	var editRowId = -1;
	var Common = new CommonMethod(theme);
	var applyDistance = {'Air Ticket': true, 'Train Ticket': true,'Bus Ticket': true, 'Auto/Taxi/Metro': true,'Self Owned Car': true, 'Bank Owned Car': true};
	var dateConfig = {formatString: "F", showTimeButton: true, formatString: 'd-MMM-yy & h:ss tt'};
	var expense = ['Air Ticket','Train Ticket','Bus Ticket','Ticket Cancellation','Halting Allow','Lodging Expense','Own Stay arrangement','Auto/Taxi/Metro','Self Owned Car','Bank Owned Car','Parking','Toll tax','Halting allow','Incidental expense'];
	var calcField = {type: 'number', name: 'winTotalAmount', field: 'totalAmount', values: ['amountExclGST', 'cgstAmount', 'sgstAmount', 'igst']};
	var popupConfig = [
		{name: 'winExpenseDescription', type: 'option', bind: 'expenseDescription', label: 'Expense Description', source: expense, required: true, change: function(event){
			calcEntitledAmount();
			
			var distanceDisabled = (applyDistance[event.args.item.label] == null);
			var distance = $("#winDistance").val();
			
			$("#winDistance").val(distanceDisabled ? distance : '');
			$("#winDistance").jqxInput({ disabled: distanceDisabled});
		}},
		{name: 'winOrigin', type: 'input', bind: 'origin', label: 'Origin', required: true, disabled: false},
		{name: 'winDestination', type: 'input', bind: 'destination', label: 'Destination', required: true, disabled: false},
		{name: 'winPNR', type: 'input', bind: 'pnrNo', label: 'PNR No', required: false, disabled: false},
		{name: 'wintravelStartDate', type: 'date', bind: 'travelStartDate', label: 'Travel Start Date & Time', format:'yyyy-MM-ddTHH:mm:ssZ', required: true, disabled: false, dateConfig: dateConfig, change: function(event){
			if(event.args.date != null){
				listData.travelStartDate = event.args.date;
				
				if(listData.travelEndDate != null){
					var diff = Common.dateDiff(listData.travelEndDate, event.args.date);
				
					$("#winNoofdays").val(diff.totalDays);
				}
				
				$("#wintravelEndDate").jqxDateTimeInput({min: event.args.date});
			}
		}},
		{name: 'wintravelEndDate', type: 'date', bind: 'travelEndDate', label: 'Travel End Date & Time', format:'yyyy-MM-ddTHH:mm:ssZ', required: true, disabled: false, dateConfig: dateConfig, change: function(event){
			if(event.args.date != null){
				listData.travelEndDate = event.args.date;
				
				if(listData.travelStartDate != null){
					var diff = Common.dateDiff(event.args.date, listData.travelStartDate);
				
					$("#winNoofdays").val(diff.totalDays);
				}
			}
		}},
		{name: 'winDistance', type: 'input', bind: 'distance', label: 'Distance (in KMS)', required: false, disabled: false, info:'', change: function(event){
			var distance = event.args.value;
			var expenseDescription = $("#winExpenseDescription").val();
			var config = {
				msg: 'Air Ticket should be enabled only for distance in KMs 400 and above.',
				date: event.args.date,
				prob: 'winDistance',
				event: event
			}
			
			if(expenseDescription == 'Air Ticket' && distance < 400){				
				Common.setInfo(config);
			} else{
				calcEntitledAmount();
				Common.closeInfo(config);
			}
		}},
		{name: 'winNoofdays', type: 'input', bind: 'noOfDays', label: 'No of days', required: false, disabled: true},
		{name: 'winEntitledAmount', type: 'number', bind: 'entitledAmount', label: 'Entitled Amount', disabled: true, icon: ''},
		{name: 'winBillAvailable', type: 'ratio', bind: 'billAvailable', label: 'Bill Available', required: false, disabled: false, source: [{name: 'optYES', label: 'YES'}, {name: 'optNO', label: 'NO'}]},
		{name: 'winAmountExclGST', type: 'number', bind: 'amountExclGST', label: 'Amount (Excl GST)', required: true, disabled: false, ref: calcField},
		{name: 'winCGSTAmount', type: 'number', bind: 'cgstAmount', label: 'CGST Amount', required: false, disabled: false, ref: calcField},
		{name: 'winSGSTAmount', type: 'number', bind: 'sgstAmount', label: 'SGST Amount', required: false, disabled: false, ref: calcField},
		{name: 'winIGST', type: 'number', bind: 'igst', label: 'IGST', required: false, disabled: false, ref: calcField},
		{name: 'winTotalAmount', type: 'number', bind: 'totalAmount', label: 'Total Amount', disabled: true, change: function(event){
			calcEntitledAmount();
		}},
		{name: 'winClaimAmount', type: 'number', bind: 'claimAmount', label: 'Claim Amount', disabled: true},
		{name: 'winFileUpload', type: 'upload', bind: 'file', label: 'Document', disabled: true}
	];
	
	var source = {
		datatype: 'json',
        datafields: [
            {name: 'expenseDescription', type: 'string'},             
            {name: 'origin', type: 'string'}, 
            {name: 'destination', type: 'string'},
            {name: 'pnrNo', type: 'string'},
            {name: 'travelStartDate', type: 'string'},
            {name: 'travelEndDate', type: 'string'},
            {name: 'distance', type: 'number'},
            {name: 'noOfDays', type: 'string'},
            {name: 'entitledAmount', type: 'number'},
            {name: 'billAvailable', type: 'string'},
            {name: 'amountExclGST', type: 'number'},
            {name: 'cgstAmount', type: 'number'},
            {name: 'sgstAmount', type: 'number'},
            {name: 'igst', type: 'number'},
            {name: 'totalAmount', type: 'number'},
            {name: 'claimAmount', type: 'number'}
            
       	],
        localdata: []
    };
                
	var columns =  [
	  Common.snoCell(),      
      { text: 'Expense Description', datafield: 'expenseDescription', editable: false },
      { text: 'Origin', datafield: 'origin', width: 140, editable: false, cellsalign: 'right' },
      { text: 'Destination', datafield: 'destination', editable: false, width: 180, cellsalign: 'left'},
      { text: 'PNR No', datafield: 'pnrNo', editable: false, width: 100, cellsalign: 'left' },
      { text: 'Travel Start Date & Time', datafield: 'travelStartDate', width: 150, editable: false, cellsalign: 'left' },
      { text: 'Travel End Date & Time', datafield: 'travelEndDate', editable: false, width: 150, cellsalign: 'left' },
      { text: 'Distance (in KMS)', datafield: 'distance', editable: false, width: 100, cellsalign: 'left'},
      { text: 'No of days', datafield: 'noOfDays', editable: false, width: 100, cellsalign: 'right' },
      { text: 'Entitled Amount', datafield: 'entitledAmount', editable: false, width: 130, cellsalign: 'right', renderer: Common.numberIconHeader },
      { text: 'Bill Available', datafield: 'billAvailable', editable: false, width: 100, cellsalign: 'right' },
      { text: 'Amount(Excl GST)', datafield: 'amountExclGST', editable: false, width: 130, cellsalign: 'right', renderer: Common.numberIconHeader },
      { text: 'CGST Amount', datafield: 'cgstAmount', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader },
      { text: 'SGST Amount', datafield: 'sgstAmount', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader },
      { text: 'IGST', datafield: 'igst', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader },
      { text: 'Total Amount', datafield: 'totalAmount', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader },
      { text: 'Claim Amount', datafield: 'claimAmount', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader },
      Common.iconCell({icon: 'fa-pencil',text: 'Action', datafield: 'Edit', width: 60})
    ];
    
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
            source: new $.jqx.dataAdapter(source),
            width: '100%'
        }).on('cellclick', function (event) 
		{
			if(event.args.datafield == 'Edit'){
				editRowId = event.args.rowindex;
				var bounddata = event.args.row.bounddata;
				
	            openPopup(bounddata);
			}
		});
		
		Common.renderPopup({
			title: "Add Expense",
			colSpan: 2,
			column: popupConfig,
			buttons: [
				{ name: 'winSave', label: 'Add' },
				{ name: 'winCancel', label: 'Cancel' }
			]
		}, 'popupWindow');
		
        $("#popupWindow").jqxWindow({
            width: 840, 
            height: 500, 
            resizable: false,  
            theme: theme, 
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#winCancel"), 
            modalOpacity: 0.25           
        });
        
        renderModel();
        
        $("#btnAdd").jqxButton({ theme: theme });
        $("#winCancel").jqxButton({ theme: theme, width: 100 });
        $("#btnAdd").click(function () {
			editRowId = null;
            openPopup({travelStartDate: new Date(), travelEndDate: new Date()});
        });
	};
	
	var getEntitledAmount = function(configData){
		var entitledAmount = 0;
		
		if(formData.grade != null && configData != null){
			for(var sKey in configData){
				var config = configData[sKey];
				
				if(config[formData.category] != null && config.designation == formData.grade){
					entitledAmount = config[formData.category];
					
					break;
				}
			}
		}
		
		return entitledAmount;
	}
	
	var calcEntitledAmount = function(){
		var entitleAmount = 0;
		var expenseDes = $("#winExpenseDescription").val();
		var totalAmount = $("#winTotalAmount").val()
		
		if(expenseDes == 'Halting Allow'){
			entitleAmount = getEntitledAmount(haltingConfig);
		}else if(expenseDes == 'Lodging Expense'){
			entitleAmount = getEntitledAmount(lodgeConfig);
		}else if(expenseDes == 'Own Stay arrangement'){
			entitleAmount = Math.round(getEntitledAmount(lodgeConfig) * 0.025);
		}else if(expenseDes == 'Self Owned Car'){
			var distance = $("#winDistance").val();
			
			entitleAmount = (distance != null && distance != '' ? distance : 0) * 8;
			entitleAmount = (entitleAmount != 0 ? entitleAmount : '');
		}
		
		$("#winEntitledAmount").val(entitleAmount);
		
		var claimAmount = totalAmount;
		
		if(entitleAmount != 0){
			claimAmount = (entitleAmount < totalAmount ? entitleAmount : totalAmount);
		}
		
		$("#winClaimAmount").val(claimAmount);
		buttonAddAction(claimAmount);
	}
	
	var openPopup = function(dataRecord){
        var xPos = (($("body").width() / 2) - ($("#popupWindow").width() / 2));
        var yPos = 100;
       	listData = {};
       	
       	if(formData != null && (formData.travelPurpose == 'Joining' || formData.travelPurpose == 'Transfer')){
			var source = Common.clone(expense);
			
			source.push('Transfer Compensation');
			source.push('Transfer damage');
			source.push('Transportation Expense');
			
			$("#winExpenseDescription").jqxDropDownList({source: source});
		}else{
			$("#winExpenseDescription").jqxDropDownList({source: expense});
		}
       	       	       
        Common.setEditModel(dataRecord);	
		Common.renderInputs(dataRecord, popupConfig);
        $("#popupWindow").jqxWindow({ position: { x: xPos, y: yPos } });
        $("#popupWindow").jqxWindow('open');
        
        $("#wintravelStartDate").jqxDateTimeInput({min: new Date()});
        $("#wintravelEndDate").jqxDateTimeInput({min: dataRecord.travelStartDate});
        buttonAddAction(0);	
	};
	
	var renderModel = function(){
		Common.renderInputs(null, popupConfig);
        $("#winSave").jqxButton({ theme: theme, width: 100 });
        $("#btnSubmit").jqxButton({ theme: theme, width: 128, disabled: true });
        $("#winFileUpload").jqxFileUpload({ 
			width: '100%%', 
			fileInputName: 'emp_doc',
			multipleFilesUpload: true, 
			autoUpload: false, 
			theme: theme, 
			uploadTemplate: 'primary' ,
			uploadUrl:  Common.HOST + '/travel/uploadFile' 
		}).on('select', function (event) {
			listData.files = [];
			
			for(var sKey in event.owner._fileRows){
				var input = $(event.owner._fileRows[sKey].fileInput);
				
				if(input != null && input.length > 0){
					Common.addAll(input[0].files, listData.files);			
				}
			}
		});	
        
        Common.bindOnChange(popupConfig);
        		
		$("#winCancel").click(function () {
			if(editRowId != null && editRowId > -1){
				
			}
		});  
		
        $("#winSave").click(function () {
            let config = Common.getModel(popupConfig);
            
            if(config.valid){
				let dataRecord = config.dataModel;
	            	      
	           	if(dataRecord != null){
					dataRecord.files = listData.files;
					dataRecord.document = 'doc_' + new Date().getTime();
					
					if(editRowId != null){
						Common.updateModel(dataRecord, selectedRows[editRowId]);
						$('#dvPFAccount').jqxGrid('updaterow', editRowId, dataRecord);
					}
					else{
						selectedRows.push(dataRecord);
						$('#dvPFAccount').jqxGrid('addrow', null, dataRecord);
					}
				}
	            
	            $("#winFileUpload").jqxFileUpload('cancelAll');
	            $("#btnSubmit").jqxButton({ disabled: (selectedRows.length > 0 ? false : true) });
	            $("#popupWindow").jqxWindow('hide');
			}
            else{
				Common.showToast({message: "Please fill the required fields."});
			}
        });   
        
        $("#btnSubmit").click(function () {
            submit(selectedRows);
        });               
	};
	
	var buttonAddAction = function(value){
	    if(value > 0){
			$("#winSave").jqxButton({disabled: false});
		}
	    else{
			$("#winSave").jqxButton({disabled: true});
		}
	} 
		
	var submit = function(listData){
		if(listData != null && listData.length > 0){
			var sURL = HOST + '/travel/create'
			var postData = Common.clone(formData);
						
			postData.submitted = 1;
			postData.approved = 0;
			postData.getL2Approved = 0;
			postData.getHRApproved = 0;
			postData.expenses = listData;	
			postData.totalAmount = 0;
						
			for(var sKey in listData){
				var rowData = listData[sKey];
				
				rowData.travelStartDate = rowData.travelStartDate_format;
				rowData.travelEndDate = rowData.travelEndDate_format;
				postData.totalAmount += parseInt(rowData.claimAmount);
			}
			
			Common.callPOST(sURL, postData, function(result){
				uploadFile(result, postData);
			});
		}
	};
	
	var uploadFile = function(result, postData){
		if(postData != null && result != null && postData.expenses != null && result.travelExpenseDetailEntity != null){
			var files = [];
			
			var getExpenseId = function(documentCode){
				let pageId = null;
				
				for(var sKey in result.travelExpenseDetailEntity){
					let expense = result.travelExpenseDetailEntity[sKey];
					
					if(expense.document == documentCode){
						pageId = expense.id;
					}
				}
				
				return pageId;
			}
			
			for(var sKey in postData.expenses){
				let expense = postData.expenses[sKey];
				let pageId = getExpenseId(expense.document);
				
				if(pageId != null){
					Common.addAll(expense.files, files, pageId);
				}
			}
			
			if(files != null && files.length > 0){
				Common.upload({empCode: result.empcode, fileInput: files, fileIndex: 0, url: Common.HOST + '/travel/uploadFile', successHandler: formSuccessHandler});				
			}
		}
	}
		
	var formSuccessHandler = function(){
		Common.showToast({message: "The record has been submitted successfully."});
		
		if(parent != null){
			parent.reset();
		}
	}
	
	this.setData = function(data, parentPanel, lodge, halting){
		formData = data;
		parent = parentPanel;
		lodgeConfig = lodge;
		haltingConfig = halting;	
	};
	
	this.resetForm = function(){
		formData = {};
		source.localdata = [];
		$("#dvPFAccount").jqxGrid('updatebounddata', 'cells');
		buttonAddAction(0);
	}
	
	this.init = function(){
		renderGrid();
	}
}
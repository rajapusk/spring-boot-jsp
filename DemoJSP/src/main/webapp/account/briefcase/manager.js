var BCManager = function(){
	var theme =  'light';
	var Common = new CommonMethod(theme);
	var selectedRows = [];
	var theme = 'light';
	var editRowId =  -1;
	var popupConfig = [
		{name: 'winName', type: 'input', bind: 'name', label: 'Name', disabled: true},		
		{name: 'winGlCode', type: 'input', bind: 'glCode', label: 'GL Code', disabled: true},
		{name: 'winInvoiceAmount', type: 'input', bind: 'invoiceAmount', label: 'Invoice Amount', disabled: true},
		{name: 'winEntitledAmount', type: 'input', bind: 'entitledAmount', label: 'Entitled Amount', disabled: true},
		{name: 'winclaimAmount', type: 'input', bind: 'claimAmount', label: 'Claimed Amount', disabled: true},
		{name: 'winEeRemarks', type: 'input', bind: 'remarks', label: 'EE Remarks', disabled: true},
		{name: 'winL1Remarks', type: 'input', bind: 'l1Remarks', label: 'L1 Remarks'},
		{name: 'winApproved', type: 'option', bind: 'approvedText', label: 'Approved', source: ['YES', 'NO', 'REJECTED']}
	];
	
	var source = {
		datatype: 'json',
        datafields: [
			{name: 'id', type: 'string'},
            {name: 'empcode', type: 'string'},
            {name: 'name', type: 'string'},             
            {name: 'glCode', type: 'number'}, 
            {name: 'invoiceAmount', type: 'number'},  
            {name: 'entitledAmount', type: 'number'}, 
            {name: 'claimAmount', type: 'number'},
            {name: 'remarks', type: 'string'},
            {name: 'l1Remarks', type: 'string'},
            {name: 'approved', type: 'booelan'}
            
       	],
        localdata: []
    };
                
	var columns =  [
	  Common.snoCell(),
      { text: 'EMP Code', datafield: 'empcode', width: 90, editable: false },
      { text: 'Name', datafield: 'name', editable: false },
      { text: 'GL Code', datafield: 'glCode', width: 80, editable: false, cellsalign: 'right' },
      { text: 'Invoice Amt', datafield: 'invoiceAmount', width: 120, editable: false, cellsalign: 'right', renderer: Common.numberIconHeader },
      { text: 'Entitled Amt', datafield: 'entitledAmount', editable: false, width: 120, cellsalign: 'right', renderer: Common.numberIconHeader },
      { text: 'Claimed Amt', datafield: 'claimAmount', editable: false, width: 120, cellsalign: 'right', renderer: Common.numberIconHeader  },
      { text: 'EE Remarks', datafield: 'remarks', width: 120, editable: false, cellsalign: 'left' },      
      { text: 'L1 Remarks', datafield: 'l1Remarks', width: 120, editable: false, cellsalign: 'left'},
      { text: 'Approved', datafield: 'approved', width: 70, threestatecheckbox: true, columntype: 'checkbox', editable: true},
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
			title: "Nominee update",
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
					
					postData.push({id: row.rowData.id, approved: row.rowData.approved, l1remards: row.l1Remarks})
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
				var sL1Remarks = $("#winL1Remarks").val();
				var dataRecord = $("#dvPFAccount").jqxGrid('getrowdata', editRowId);
				var bApproved = (sApproved == 'YES' ? 1 : (sApproved == 'NO' ? 0 : 2));
				var rowID = $('#dvPFAccount').jqxGrid('getrowid', editRowId); 
				                          
                dataRecord.approved = bApproved;
                dataRecord.l1Remarks = sL1Remarks;
                
                $('#dvPFAccount').jqxGrid('updaterow', rowID, dataRecord);
                $("#popupWindow").jqxWindow('hide'); 
                approveLoan([{ id: dataRecord.id, approved: bApproved, l1Remarks: sL1Remarks}]);
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
			url: Common.HOST + '/ba/manager/update', 
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
			url: Common.HOST + '/ba/manager',
			contentType: "application/json; charset=utf-8",
			success: function(result){
				source.localdata = result;
				$("#dvPFAccount").jqxGrid('updatebounddata', 'cells');
			}
		});
	}
	
	this.init = function(){
		renderGrid();
	}
}
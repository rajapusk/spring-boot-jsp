var Nominee = function(){
	var HOST = 'http://localhost:' + 9092;
	//var HOST = window.location.host;
	var selectedRows = [];
	var theme = 'light';
	var editRowId =  -1;
	var Common = new CommonMethod(theme);
	var popupConfig = [
		{name: 'winName', type: 'input', bind: 'name', label: 'Name'},		
		{name: 'winRelation', type: 'input', bind: 'monthlySalary', label: 'Relation'},
		{name: 'winNomineeAadhaarNo', type: 'input', bind: 'prevNetSalary', label: 'Nominee Aadhaar No'},
		{name: 'winDOB', type: 'date', bind: 'netSalaryPercentage', label: 'DOB'},
		{name: 'winGender', type: 'option', bind: 'presentVPF', label: 'Gender', source: ['Male', 'Female', 'Others']},
		{name: 'winAddress', type: 'input', bind: 'revisedVPF', label: 'Address'},
		{name: 'winProportion', type: 'input', bind: 'newNetSalary', label: 'Proportion / Share %'},
		{name: 'winTotalShare', type: 'input', bind: 'newNetSalaryPercentage', label: 'Total Share %'}
	];
	
	var source = {
		datatype: 'json',
        datafields: [
			{name: 'id', type: 'string'},
            {name: 'empcode', type: 'string'},
            {name: 'name', type: 'string'},             
            {name: 'monthlySalary', type: 'number'}, 
            {name: 'prevNetSalary', type: 'number'},
            {name: 'netSalaryPercentage', type: 'number'},
            {name: 'presentVPF', type: 'number'},
            {name: 'revisedVPF', type: 'number'},
            {name: 'newNetSalary', type: 'number'},
            {name: 'newNetSalaryPercentage', type: 'number'},            
            {name: 'approved', type: 'booelan'},
            {name: 'REMARKS', type: 'string'}
            
       	],
        localdata: []
    };
                
	var columns =  [
	  {
          text: '#', sortable: false, filterable: false, editable: false,
          groupable: false, draggable: false, resizable: false, cellsalign: 'center',
          datafield: '', columntype: 'number', width: 50,
          cellsrenderer: function (row, column, value) {
              return "<div style='margin:4px;'>" + (value + 1) + "</div>";
          }
      },       
      { text: 'EMP Code', datafield: 'empcode', width: 90, editable: false },
      { text: 'Name', datafield: 'name', editable: false },
      { text: 'Monthly Salary', datafield: 'monthlySalary', width: 100, editable: false, cellsalign: 'right' },
      { text: 'Prev Net Salary', datafield: 'prevNetSalary', editable: false, width: 100, cellsalign: 'left'},
      { text: 'Prev Net Salary Percetage', datafield: 'netSalaryPercentage', editable: false, width: 100, cellsalign: 'left'},
      { text: 'Present VPF', datafield: 'presentVPF', width: 100, editable: false, cellsalign: 'right' },
      { text: 'Revised VPF', datafield: 'revisedVPF', editable: false, width: 100, cellsalign: 'right' },
      { text: 'New Net Salary', datafield: 'newNetSalary', editable: false, width: 100, cellsalign: 'left'},
      { text: 'New Net Salary Percetage', datafield: 'newNetSalaryPercentage', editable: false, width: 100, cellsalign: 'left'},
      { text: 'Approved', datafield: 'approved', width: 70, threestatecheckbox: true, columntype: 'checkbox', editable: true},
      { text: 'Remarks', datafield: 'REMARKS', width: 150, editable: false, cellsalign: 'left'},
      
      { text: 'Action', datafield: 'Edit',  editable: false, width: 60, cellsrenderer: function () {
             return "<div class='scCenterXY scEditIcon'><span class='fa fa-pencil'></span></div>";
          }
      }
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
	             
	             if(dataRecord.docPath != null){
					$("#userAvatar").attr("src", dataRecord.docPath);   
				 }
	             
	             renderInputs(dataRecord);
	             $("#popupWindow").jqxWindow({ position: { x: xPos, y: yPos } });
	             $("#popupWindow").jqxWindow('open');
			}
			console.log(event.args)
		});
		
		Common.renderPopup({
			title: "Nominee update",
			colSpan: 2,
			column: popupConfig,
			buttons: [
				{ name: 'winSave', label: 'Save' },
				{ name: 'winCancel', label: 'Cancel' }
			]
		}, 'popupWindow');
		
        $("#popupWindow").jqxWindow({
            width: 740, 
            height: 360, 
            resizable: false,  
            theme: theme, 
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#winCancel"), 
            modalOpacity: 0.25           
        });
        
        renderModel();
        $("#btnSave").jqxButton({ theme: theme });     	
        $("#winCancel").jqxButton({ theme: theme, width: 100 });
        
        $("#btnSave").click(function () {
            if(selectedRows != null ) {				
                var postData = [];     
                
                for(var sKey in selectedRows){
					var row = selectedRows[sKey];
					
					postData.push({id: row.rowData.id, approved: row.rowData.approved})
				}
                
                if(postData.length > 0){
					approveLoan(postData);
				}
            }
        });
        
        loadData();
	}
	
	var renderInputs = function(dataModel){
		for(var sKey in popupConfig){
			var row = popupConfig[sKey];
			
			if(dataModel == null){
				if(row.type == 'input'){
					$("#" + row.name).jqxInput({ theme: theme, disabled: true });
				}else if(row.type == 'date'){
					$("#" + row.name).jqxDateTimeInput({theme: theme});
				}else {
					$("#" + row.name).jqxDropDownList({theme: theme, source: row.source});
				}
			}
			else{
				$("#" + row.name).val(dataModel[row.bind]);
			}
		}
	}
	
	var renderModel = function(){
		renderInputs(null);
        $("#winSave").jqxButton({ theme: theme, width: 100 });
        $("#winSave").click(function () {
            if (editRowId >= 0) {
				var sApproved = $("#winApproved").val();
				var dataRecord = $("#dvPFAccount").jqxGrid('getrowdata', editRowId);
				var bApproved = (sApproved == 'YES' ? 1 : (sApproved == 'NO' ? 0 : 2));
				var rowID = $('#dvPFAccount').jqxGrid('getrowid', editRowId);                           
                dataRecord.approved = bApproved;
                $('#dvPFAccount').jqxGrid('updaterow', rowID, dataRecord);
                $("#popupWindow").jqxWindow('hide'); 
                approveLoan([{ id: dataRecord.id, approved: bApproved}]);
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
			type: 'put',
			url: HOST + '/vpf/manager/update', 
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
			url: HOST + '/vpf/manager',
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
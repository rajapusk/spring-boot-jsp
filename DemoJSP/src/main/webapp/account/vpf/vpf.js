var VPFManagerApproval = function(){
	var HOST = 'http://localhost:' + 9092;
	//var HOST = window.location.host;
	var selectedRows = [];
	var theme = 'light';
	var editRowId =  -1;
	var Common = new CommonMethod(theme);
	var popupConfig = [
		{name: 'winEmpCode', type: 'input', bind: 'empcode'},
		{name: 'winName', type: 'input', bind: 'name'},		
		{name: 'winMonthlySALARY', type: 'input', bind: 'monthlySalary'},
		{name: 'winPrevNetSalary', type: 'input', bind: 'prevNetSalary'},
		{name: 'winPreNetSalaryPer', type: 'input', bind: 'netSalaryPercentage'},
		{name: 'winPresentvfp', type: 'input', bind: 'presentVPF'},
		{name: 'winRevisedVPF', type: 'input', bind: 'revisedVPF'},
		{name: 'winNewNetSalary', type: 'input', bind: 'newNetSalary'},
		{name: 'winNewNetSalaryPer', type: 'input', bind: 'newNetSalaryPercentage'},		
		{name: 'winApproved', type: 'option', bind: 'approvedText', source: ['YES', 'NO', 'REJECTED']},
		{name: 'winREMARKS', type: 'input', bind: 'REMARKS'}
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
	  Common.snoCell(),     
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
      Common.iconCell({icon: 'fa-pencil', text: 'Action', datafield: 'Edit', width: 60}),
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
		
        $("#popupWindow").jqxWindow({
            width: 650, height: 462, resizable: false,  theme: theme, isModal: true, autoOpen: false, cancelButton: $("#Cancel"), modalOpacity: 0.01           
        });
        
        renderModel();
        $("#btnSave").jqxButton({ theme: theme });     	
        $("#Cancel").jqxButton({ theme: theme, width: 100 });
        
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
				}else{
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
var NomineeList = function(){
	var HOST = 'http://localhost:' + 9092;
	//var HOST = window.location.host;
	var selectedRows = [];
	var parent = null;
	var formData = {};
	var theme = 'light';
	var editRowId = -1;
	var totalShare =  0;
	var Common = new CommonMethod(theme);
	var popupConfig = [
		{name: 'winName', type: 'input', bind: 'name', label: 'Name', required: true},		
		{name: 'winRelation', type: 'option', bind: 'relation', label: 'Relation', source: ['Father', 'Mother', 'Spouse', 'Child1', 'Child2', 'Child3', 'Brother1', 'Brother2', 'Sister1', 'Sister2'], required: true},
		{name: 'winNomineeAadhaarNo', type: 'input', bind: 'nomineeAadhaarNo', label: 'Nominee Aadhaar No', required: true},
		{name: 'winDOB', type: 'date', bind: 'dob', label: 'DOB', required: true},
		{name: 'winGender', type: 'ratio', bind: 'gender', label: 'Gender', source: [{label: 'Male', name: 'optMale'}, {label: 'Female', name: 'optFemale'}]},
		{name: 'winAddress', type: 'input', bind: 'address', label: 'Address', required: true},
		{name: 'winProportion', type: 'input', bind: 'proportion', label: 'Proportion / Share %', required: true},
		{name: 'winTotalShare', type: 'input', bind: 'totalShare', label: 'Total Share %', disabled: true}
	];
	
	var source = {
		datatype: 'json',
        datafields: [
            {name: 'name', type: 'string'},             
            {name: 'relation', type: 'string'}, 
            {name: 'nomineeAadhaarNo', type: 'string'},
            {name: 'dob', type: 'string'},
            {name: 'gender', type: 'string'},
            {name: 'address', type: 'string'},
            {name: 'proportion', type: 'number'},
            {name: 'totalShare', type: 'number'}
            
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
      { text: 'Name', datafield: 'name', editable: false },
      { text: 'Relation', datafield: 'relation', width: 140, editable: false, cellsalign: 'right' },
      { text: 'Nominee Aadhaar No', datafield: 'nomineeAadhaarNo', editable: false, width: 180, cellsalign: 'left'},
      { text: 'DOB', datafield: 'dob', editable: false, width: 100, cellsalign: 'left'},
      { text: 'Gender', datafield: 'gender', width: 100, editable: false, cellsalign: 'right' },
      { text: 'Address', datafield: 'address', editable: false, width: 130, cellsalign: 'right' },
      { text: 'Proportion / Share %', datafield: 'proportion', editable: false, width: 150, cellsalign: 'left'},
      
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
        }).on('cellclick', function (event) 
		{
			if(event.args.datafield == 'Edit'){
				editRowId = event.args.rowindex;
				
				totalShare = (totalShare - event.args.row.bounddata.proportion);
				
				event.args.row.bounddata.totalShare = totalShare;
	            openPopup(event.args.row.bounddata);
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
        
        $("#btnAddNominee").jqxButton({ theme: theme });
        $("#winCancel").jqxButton({ theme: theme, width: 100 });
        
        $("#btnAddNominee").click(function () {
			editRowId = null;
            openPopup({totalShare: totalShare});
        });
	};
	
	var openPopup = function(dataRecord){
        var xPos = (($("body").width() / 2) - ($("#popupWindow").width() / 2));
        var yPos = 100;
        		
		Common.renderInputs(dataRecord, popupConfig);
        $("#popupWindow").jqxWindow({ position: { x: xPos, y: yPos } });
        $("#popupWindow").jqxWindow('open');
        buttonAddAction(0);	
	};
	
	var renderModel = function(){
		Common.renderInputs(null, popupConfig);
        $("#winSave").jqxButton({ theme: theme, width: 100 });
        $("#btnSubmit").jqxButton({ theme: theme, width: 128, disabled: true });
        $("#optMale").jqxRadioButton({ theme: theme, width: 90, checked : true });
        $("#optFemale").jqxRadioButton({ theme: theme, width: 90 });           
        $("#winProportion").on('change', function (event) 
		{
		    var value = event.args.value;
		    
		    if(!isNaN(value) && value != null && value != ''){
				$("#winTotalShare").val(totalShare + parseInt(value));
				buttonAddAction(totalShare + parseInt(value));
			}
		    else{
				$("#winTotalShare").val(totalShare);
			}
		});
		
        $("#winSave").click(function () {
            let config = Common.getModel(popupConfig);
            
            if(config.valid){
				let dataRecord = config.dataModel;
	            
	            if(!isNaN(dataRecord.totalShare)){
					totalShare = parseInt(dataRecord.totalShare);
				}
	            
	            var male = $("#optMale").jqxRadioButton('check');
	            dataRecord.gender = (male ? 'Male' : 'Female');
	            
	            if(editRowId != null){
					selectedRows[editRowId] = dataRecord;
					$('#dvPFAccount').jqxGrid('updaterow', editRowId, dataRecord);
				}
				else{
					selectedRows.push(dataRecord);
					$('#dvPFAccount').jqxGrid('addrow', null, dataRecord);
				}
	            
	            $("#btnSubmit").jqxButton({ disabled: (totalShare == 100 ? false : true) });
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
	    if(value > 0 && value <= 100){
			$("#winSave").jqxButton({disabled: false});
		}
	    else{
			$("#winSave").jqxButton({disabled: true});
		}
	} 
		
	var submit = function(nominees){
		if(nominees != null && nominees.length > 0){
			var sURL = HOST + '/pfnominee/create'
			var postData = formData;
			
			for(var sKey in nominees){
				let nominee = nominees[sKey];
				
				nominee.dob = $('#winDOB').jqxDateTimeInput('getDate');
				nominee.dob = Common.dateFormat(nominee.dob, 'yyyy-MM-dd');
			}
			
			postData.submitted = 1;
			postData.approved = 0;
			postData.hrApproved = 0;
			postData.nominees = nominees;		
					
			$.ajax({
				type: 'post',
				url: sURL, 
				data: JSON.stringify(postData),
				contentType: "application/json; charset=utf-8",
				success: function(result){
					formSuccessHandler();
				}
			});
		}
	};
		
	var formSuccessHandler = function(){
		Common.showToast({message: "The record has been submitted successfully."});
		
		resetForm();
	}
	
	var loadData = function(){
		if(formData != null && formData.nomineeId > 0){
			$.ajax({
				type: 'GET',
				url: HOST + '/pfnominee/get/' + formData.nomineeId,
				contentType: "application/json; charset=utf-8",
				success: function(result){
					if(result != null && result != ''){
						source.localdata = result;
						$("#dvPFAccount").jqxGrid('updatebounddata', 'cells');
					}
				}
			});
		}
	}
	
	this.formData = function(data, parentPanel){
		formData = data;
		parent = parentPanel;
		
		loadData();		
	};
	
	var resetForm = function(){
		formData = {};
		source.localdata = result;
		$("#dvPFAccount").jqxGrid('updatebounddata', 'cells');
		buttonAddAction();
		parent.resetForm();
	}
	
	this.init = function(){
		renderGrid();
	}
}
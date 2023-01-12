var TravelApply = function(){
	var HOST = 'http://localhost:' + 9092;
	//var HOST = window.location.host;
	var theme =  'light';
	var Common = new CommonMethod(theme);
	var formData = {travelPurpose: 'Joining', permissionMode: 'Email', permittedDate: new Date()};
	var formWidth = 850;	        	
	var labelWidth = 180;
	var controlWidth = ((formWidth / 2) - labelWidth);
	var pgList = new TravelList();
	var lodgeConfig = {};
	var haltingConfig = {};
	var $this = this;
	
	var jqxFormTmp = [
		{
	        type: 'blank',
	        rowHeight: '5px'
	    },{
			columns: [
				{
			        type: 'blank',
			        name: 'searchPanel',
			        rowHeight: '5px'
			    },{
					bind: 'name',
	                type: 'text',
	                name: 'name',
	                label: 'Name',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'originBranchCode',
	                type: 'text',
	                name: 'originBranchCode',
	                label: 'Origin Branch Code',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'destinationBranchCode',
	                type: 'text',
	                name: 'destinationBranchCode',
	                label: 'Destination Branch Code',
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
			        type: 'blank',
			        name: 'travelPurpose',
			        rowHeight: '5px',
			        columnWidth: '20%'
			    }
			]
		},{
	        type: 'blank',
	        rowHeight: '30px',
	        name: 'pnlBorder'
	    },{
			columns: [
				{
			        type: 'blank',
			        name: 'searchPanel2',
			        rowHeight: '5px'
			    },{
					bind: 'permittedName',
	                type: 'text',
	                name: 'permittedName',
	                label: 'Name',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'permittedDateVal',
					type: 'date',
					label: 'Date',
					info: '',
					name: 'permittedDateVal',
					labelPosition: 'top',
					labelWidth: labelWidth + 'px',
					width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					type: 'blank',
					name: 'permittedTime',
					columnWidth: '20%',					
				},{
			        type: 'blank',
			        name: 'permissionMode',
			        rowHeight: '5px',
			        columnWidth: '20%'
			    }
			]
		},{
			columns: [
				{
					bind: 'advanceAmount',
	                type: 'number',
	                name: 'advanceAmount',
	                label: 'Advance Amount',
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				}
			]
		},{
	        type: 'blank',
	        rowHeight: '10px'
	    }
	];
	
	var bindValue = function(value, bind){
		formData[bind] = value;
	}
	
	var bindComponent = function(){
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'pnlBorder').addClass('scBorder');
		
		$("#dvRefundablePFLoan")
		.jqxForm('getComponentByName', 'searchPanel').append(
			 '<div class="scTitlePanel">'
			 	+ '<div class="scTitle">'
			 		+ 'Employee Code'
			 	+ '</div>'
				+ '<div class="scSearchBox">'
					+ '<div class="searchInput">'
						+ '<i class="searchIcon"></i>'
						+ '<input type="search" class="searchInputField" id="searchData"></input>'
					+ '</div>'
				+ '</div>'
			+ '</div>'
		);
		
		$("#dvRefundablePFLoan")
		.jqxForm('getComponentByName', 'searchPanel2').append(
			 '<div class="scTitlePanel">'
			 	+ '<div class="scTitle">'
			 		+ 'Employee Code'
			 	+ '</div>'
				+ '<div class="scSearchBox">'
					+ '<div class="searchInput">'
						+ '<i class="searchIcon"></i>'
						+ '<input type="search" class="searchInputField" id="searchData2"></input>'
					+ '</div>'
				+ '</div>'
			+ '</div>'
		);
		
		$("#dvRefundablePFLoan")
		.jqxForm('getComponentByName', 'travelPurpose').append(
			 '<div class="scTitlePanel">'
			 	+ '<div class="scTitle">'
			 		+ 'Travel Purpose'
			 	+ '</div>'
				+ '<div class="scOptionBox">'
					+ '<div class="scGroupOption">'
						+ '<div id="optQ1">Joining</div>'
						+ '<div id="optQ2">Transfer</div>'
						+ '<div id="optQ3">Business trip</div>'
					+ '</div>'
				+ '</div>'
			+ '</div>'
		);
		
		$("#dvRefundablePFLoan")
		.jqxForm('getComponentByName', 'permissionMode').append(
			 '<div class="scTitlePanel">'
			 	+ '<div class="scTitle">'
			 		+ 'Permission mode'
			 	+ '</div>'
				+ '<div class="scOptionBox">'
					+ '<div class="scGroupOption">'
						+ '<div id="optQ4">Email</div>'
						+ '<div id="optQ5">Letter</div>'
					+ '</div>'
				+ '</div>'
			+ '</div>'
		);
		
		$("#dvRefundablePFLoan")
		.jqxForm('getComponentByName', 'permittedTime').append(
			 '<div class="scTitlePanel">'
			 	+ '<div class="scTitle">'
			 		+ 'Travel Time'
			 	+ '</div>'
				+ '<div class="scOptionBox">'
					+ '<div id="permittedTimeDOM"></div>'
				+ '</div>'
			+ '</div>'
		);
		
		$("#permittedTimeDOM").jqxDateTimeInput({ 
			width: '100%', height: '30px', formatString: "hh:mm tt", showTimeButton: true, showCalendarButton: false, 
		});
		
		$("#permittedTimeDOM").on('change', function(event){
			var time = Common.dateFormat(event.args.date, 'HH:mm:ss');
			
			bindValue(time, 'permittedTime');
		});
		
		$("#permittedTimeDOM").val(new Date());
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'permittedDateVal').on('change', function(event){
			var time = Common.dateFormat(event.args.date, 'yyyy-MM-dd');
			
			bindValue(time, 'permittedDate');
		});
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'advanceAmount').on('change', function(event){
			var val = (event.args.value != null && event.args.value != '' ? event.args.value : 0) * 1;
			
			bindValue(val, 'advanceAmount');
		});
            
		var initOption = function(){
			for(var idx = 1; idx <= 3; idx++){
				$('#optQ' + idx).jqxRadioButton({theme: theme, groupName: 'travelPurpose'});
			}
			
			for(var idx = 4; idx <= 5; idx++){
				$('#optQ' + idx).jqxRadioButton({theme: theme, groupName: 'permissionMode'});
			}
		}
		
		var setComboSource = function(event){
			var checked = event.args.checked; 
			
			if(checked){    
				formData.travelPurpose = event.currentTarget.innerText;
			}
		}
		
		var setPermissionSource = function(event){
			var checked = event.args.checked; 
			
			if(checked){    
				formData.permissionMode = event.currentTarget.innerText;
			}
		}
		
		
        /*
        entity.setPermittedTime(input.permittedTime);
        entity.setTotalAmount(input.totalAmount);
        */
        
		initOption();
		$('#optQ1').on('change', setComboSource); 
		$('#optQ2').on('change', setComboSource);
		$('#optQ3').on('change', setComboSource);
		$('#optQ4').on('change', setPermissionSource);	
		$('#optQ5').on('change', setPermissionSource);		
		$('#optQ1').jqxRadioButton('checked', true);
		$('#optQ4').jqxRadioButton('checked', true);  					
		$("#searchData").jqxInput({ width: '250px', height: '30px', placeHolder: 'Enter the employee code'});
		$("#searchData2").jqxInput({ width: '250px', height: '30px', placeHolder: 'Enter the employee code'});
		$('#searchData').on('change', function(){
			var empCode = $("#searchData").jqxInput('val');
			
			if(empCode != null && empCode != ''){
				var sURL = HOST + '/pfaccount/get/' + empCode;
				var fields = [
					{col: 'name', bind: 'name'},
					{col: 'empcode', bind: 'empCode'},  
					{col: 'originBranchCode', bind: 'originBranchCode'}, 
					{col: 'grade', bind: 'grade'}, 
					{col: 'branch', bind: 'originBranchCode'}
				];
				
				loadData(sURL, fields);
			}
		});
		
		$('#searchData2').on('change', function(){
			var empCode = $("#searchData2").jqxInput('val');
			
			if(empCode != null && empCode != ''){
				var sURL = HOST + '/pfaccount/get/' + empCode;
				var fields = [{col: 'name', bind: 'permittedName'}, {col: 'empcode', bind: 'permittedBy'}];
				
				
				loadData(sURL, fields);
			}
		});	
		
		var prevDestinationBranchCode = null;
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'destinationBranchCode').on('change', function(event){
			if(event.args.value != null && event.args.value != '' && prevDestinationBranchCode != event.args.value){
				prevDestinationBranchCode = event.args.value;
				
				var sURL = HOST + '/travel/branch/' + event.args.value;
				var fields = [
					{col: 'branchCode', bind: 'destinationBranchCode'}, 
					{col: 'category', bind: 'category'}, 
					{col: 'city', bind: 'city'}, 
					{col: 'population', bind: 'population'}, 
					{col: 'type', bind: 'type'}
				];
				
				loadData(sURL, fields);
			}
		});			
	};
	
	var loadData = function(sURL, fields){		
		if(sURL != null && sURL != ''){
			Common.callGET(sURL, function(result, event){
				if(result != null && result != ''){
					
					for(var sKey in fields){
						field = fields[sKey];
						
						formData[field.bind] = (result[field.col] != null ? result[field.col]: null);
					}
					
					setListData();
					Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, formData);
				}
				else{
					Common.showToast({message: "Please enter valid details."});
				}
			});
		}
	};
		
	var resetForm = function(){
		formData = {};
		calculateAmount();		
		$("#searchData").jqxInput('val', '');
		
		var config = {REMARKS: ''};		
		Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, config);
		pgList.resetForm(formData, $this);
	}
	
	var calculateAmount = function(){
		Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan',  Common.updateValue, formData);
	};
	
	this.reset = function(){
		resetForm();
	}

	var loadConfig = function(){
		$.ajax({url: HOST + '/travel/lodging', success: function(result, event){
			if(result != null && result != ''){
				lodgeConfig = result;
				
				setListData();
			}
		}});
		
		$.ajax({url: HOST + '/travel/halting', success: function(result, event){
			if(result != null && result != ''){
				haltingConfig = result;
				
				setListData();
			}
		}});
	}
	
	var setListData = function(){
		if(pgList != null){
			pgList.setData(formData, $this, lodgeConfig, haltingConfig);
		}
	}
	
	this.init = function(){	
	    $('#dvRefundablePFLoan').jqxForm({
	        template: jqxFormTmp,
	        theme: theme,
	        value: {},
	        padding: { left: 10, top: 10, right: 10, bottom: 10 }
	    });
	    
	    loadConfig();       
	    bindComponent();
	    Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan',  Common.updateDisable, {});
	    calculateAmount();	    
	    pgList.init();
	};
}
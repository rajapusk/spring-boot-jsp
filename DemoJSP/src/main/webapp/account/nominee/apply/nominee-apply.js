var NomineeApply = function(){
	var HOST = 'http://localhost:' + 9092;
	//var HOST = window.location.host;
	var theme =  'light';
	var Common = new CommonMethod(theme);
	var formData = {};
	var formWidth = 850;	        	
	var labelWidth = 180;
	var controlWidth = ((formWidth / 2) - labelWidth);
	var pgList = new NomineeList();
		
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
					bind: 'nameInAadhaar',
	                type: 'text',
	                name: 'nameInAadhaar',
	                label: 'Name As Per Aadhaar',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'dob',
	                type: 'date',
	                name: 'dob',
	                label: 'DOB',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'gender',
	                type: 'text',
	                name: 'gender',
	                label: 'Gender',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				}
			]
		},{
			columns: [
				{
					bind: 'doj',
					type: 'date',
					label: 'DOJ',
					name: 'doj',
					disabled: true,
					labelPosition: 'top',
					labelWidth: labelWidth + 'px',
					width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'dojpfac',
	                type: 'date',
	                name: 'dojpfac',
	                label: 'DOJ PF A/C',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'dor',
					type: 'date',
					name: 'dor',
					label: 'DT Of Retirement',
					disabled: true,
					labelPosition: 'top',
					labelWidth: labelWidth + 'px',
					width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'pf_nps',
	                type: 'text',
	                name: 'pf_nps',
	                label: 'PF-NSP A/C No',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'panNo',
	                type: 'text',
	                name: 'panNo',
	                label: 'Pan No',
	                format: {
						number: 4,
						length: 12,
						formatStr: ' '
					},
	                disabled: true,
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
	
	var bindComponent = function(){
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
				
		$('#searchData').on('change', loadEmpDetails);				
		$("#searchData").jqxInput({ width: '250px', height: '30px', placeHolder: 'Enter the employee code'});
		
		/*	
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'saveDraft').on('click', function(event){
			submitLoan('DRAFT');
		});
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'saveSubmit').on('click', function(event){
			submitLoan('SUBMIT');
		});*/
	};
	
	var loadEmpDetails = function(){
		var empCode = $("#searchData").jqxInput('val');
		var $this = this;
		
		if(empCode != null && empCode != ''){
			var sURL = HOST + '/pfaccount/get/' + empCode
		
			$.ajax({url: sURL, success: function(result, event){
				if(result != null && result != ''){
					formData = result;
					formData.dob = new Date(formData.dob);
					formData.doj = new Date(formData.doj);
					formData.dor = new Date(formData.dor);
					formData.nomineeId = 7;
					
					formData.age = Common.dateDiff(formData.dob, new Date()).year;
					formData.present_EXPERIENCE = Common.dateDiff(formData.doj, new Date()).year;
					formData.service_LEFT = Common.dateDiff(new Date(), formData.dor).year;
					pgList.formData(formData, $this);
					Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, formData);
				}
				else{	
					resetForm();
					Common.showToast({message: "Please enter valid Employee Code."});
				}
			}});
		}
	};
		
	var resetForm = function(){
		formData = {};
		calculateAmount();		
		$("#searchData").jqxInput('val', '');
		
		var config = {REMARKS: ''};		
		Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, config);
	}
	
	var calculateAmount = function(){
		Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan',  Common.updateValue, formData);
	};

	this.init = function(){	
	    $('#dvRefundablePFLoan').jqxForm({
	        template: jqxFormTmp,
	        theme: theme,
	        value: {},
	        padding: { left: 10, top: 10, right: 10, bottom: 10 }
	    });
	    	       
	    bindComponent();
	    Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan',  Common.updateDisable, {});
	    calculateAmount();
	    
	    $('#searchData').val('31925');	
	    loadEmpDetails();
	    
	    pgList.init();
	};
}
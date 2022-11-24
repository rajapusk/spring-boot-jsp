var PFLoan = function(){	
	var HOST = 'http://localhost:' + 9092;
	//var HOST = window.location.host;
	var theme =  'light';
	var Common = new CommonMethod(theme);
	var formData = {};
	var formWidth = 850;	        	
	var labelWidth = 180;
	var controlWidth = ((formWidth / 2) - labelWidth);
	var defaultAdvanceType = 'Purchase of House/Site';
	var defaultEMI = 24;
	var typeOptions = [
        { value: 'Purchase of House/Site', percentage: 100 },
        { value: 'Construction of House', percentage: 100},
        { value: 'Repayment of outside borrowing towards house/site', percentage: 100},
        { value: 'Children Marriage', percentage: 50},
        { value: 'Higher education of child', percentage: 50}
    ];
	    		
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
					bind: 'grade',
	                type: 'text',
	                name: 'grade',
	                label: 'Grade',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'designation',
	                type: 'text',
	                name: 'designation',
	                label: 'Designation',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'worksitecode',
	                type: 'text',
	                name: 'worksitecode',
	                label: 'Worksite Code',
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
					bind: 'location',
	                type: 'text',
	                name: 'location',
	                label: 'Location',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
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
					bind: 'present_EXPERIENCE',
					type: 'number',
					name: 'PresentExperience',
					label: 'Present Experience',
					disabled: true,
					labelPosition: 'top',
					autoopen: true,
					labelWidth: labelWidth + 'px',
					width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'dor',
	                type: 'date',
	                name: 'DORetirement',
	                label: 'DO Retirement',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'service_LEFT',
					name: 'serviceLeft',
	                type: 'number',
	                label: 'Service Left',
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
					bind: 'monthly_SALARY',
	                type: 'number',
	                name: 'monthlySalary',
	                label: 'Monthly Salary',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'prevNetSalary',
	                type: 'number',
	                name: 'prevNetSalary',
	                label: 'Prev Net Salary',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'netSalPer',
	                type: 'number',
	                name: 'netSalPer',
	                label: 'Net Salary Percetage',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'pf_BALANCE',
	                type: 'number',
	                name: 'pfBalance',
	                label: 'PF Balance',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'interest',
	                type: 'number',
	                name: 'interest',
	                label: 'Interest',
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
					bind: 'advanceType',
	                type: 'option',
	                name: 'advanceType',
	                label: 'Advance Type',
	                value: defaultAdvanceType,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
	                component: 'jqxDropDownList',
	                options: typeOptions,
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'eligibleAmount',
	                type: 'number',
	                name: 'eligibleAmount',
	                label: 'Eligible Amount',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'requiredAmount',
	                type: 'number',
	                name: 'requiredAmount',
	                label: 'Required Amount',
	                disabled: false,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
			        type: 'blank',
			        rowHeight: '75px',
			        columnWidth: '20%'
			    },{
	                type: 'blank',
			        rowHeight: '75px',
			        columnWidth: '20%'
	            }
			]
		},{
			columns: [
				{
			        type: 'blank',
			        name: 'fileUpload',
			        rowHeight: '75px',
			        columnWidth: '40%'
			    },{
	                bind: 'REMARKS',
	                type: 'textArea',
	                name: 'REMARKS',
	                label: 'Remarks',
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: '100%',
	                align: 'left',
					columnWidth: '20%'
	            }
			]
		},{
	        type: 'blank',
	        rowHeight: '10px'
	    },{
	        columns: [
				{
			        type: 'blank',
			        rowHeight: '75px',
			        columnWidth: '35%'
			    },{
	                type: 'button',
	                text: 'Save As Draft',
	                name: 'saveDraft',
	                width: '140px',
	                height: '30px',
	                rowHeight: '40px',
	                columnWidth: '10%',
	                align: 'right'
	            },{
	                type: 'button',
	                text: 'Save And Submit',
	                name: 'saveSubmit',
	                width: '140px',
	                height: '30px',
	                rowHeight: '40px',
	                columnWidth: '10%',
	                align: 'center'
	            },{
	                type: 'button',
	                text: 'Cancel',
	                name: 'cancel',
	                width: '140px',
	                height: '30px',
	                rowHeight: '40px',
	                columnWidth: '10%',
	               	align: 'left'
	            },{
			        type: 'blank',
			        rowHeight: '75px',
			        columnWidth: '35%'
			    }             
	        ]
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
		$("#dvRefundablePFLoan")
		.jqxForm('getComponentByName', 'fileUpload')
		.jqxFileUpload({ 
			width: '100%%', 
			fileInputName: 'pf_loan_doc',
			multipleFilesUpload: false, 
			autoUpload: false, 
			theme: theme, 
			uploadTemplate: 'primary' ,
			uploadUrl:  HOST + '/pfloan/uploadFile' 
		}).on('select', function (event) {
		   	formData.hasFile = true;
		}).on('remove', function (event) {
		   	formData.hasFile = false;
		});		
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'requiredAmount').on('change', function(event){
			var requiredAmount = 0;
			
			if(event.args.value != null && event.args.value.length > 0){
				requiredAmount = parseInt(event.args.value);
			}
		
			calculateAmount(requiredAmount);
		});
				
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'saveDraft').on('click', function(event){
			submitLoan('DRAFT');
		});
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'saveSubmit').on('click', function(event){
			submitLoan('SUBMIT');
		});
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'advanceType').on('change', function(event){
			formData.advanceType = event.args.item.value;	
			
			formData.eligibleAmount = getEligibleAmount();
			calculateAmount(formData.requiredAmount);
		});		
				
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'REMARKS').jqxTextArea({
			height: 75, 
			theme: theme, 
			width: controlWidth
		}).on('change', function(event){
			formData.REMARKS = $("#dvRefundablePFLoan").jqxForm('getComponentByName', 'REMARKS').val();
		});
	};
	
	var getEligibleAmount = function(){
		var eligibleAmount = 0;
		
		if(formData.present_EXPERIENCE >= 10){
			var eligibleAmt = (formData.pf_BALANCE + formData.interest);		
			var eligibleMonth = (formData.monthly_SALARY * 6)
						
			for(var sKey in typeOptions){
				var row = typeOptions[sKey];
				
				if(formData.advanceType == row.value){
					if(row.percentage == 100){
						eligibleAmount = eligibleAmt;
					} else {
						eligibleAmount = ((eligibleAmt * (row.percentage / 100)) < eligibleMonth ? (eligibleAmt * (row.percentage / 100)) : eligibleMonth);
					}
					
					break;
				}
			}
		}
		
		return eligibleAmount;
	}
	
	var loadEmpDetails = function(){
		var empCode = $("#searchData").jqxInput('val');
		
		if(empCode != null && empCode != ''){
			var sURL = HOST + '/pfaccount/get/' + empCode
		
			$.ajax({url: sURL, success: function(result, event){
				if(result != null && result != ''){
					formData = result;
					formData.dob = new Date(formData.dob);
					formData.doj = new Date(formData.doj);
					formData.dor = new Date(formData.dor);
					
					formData.age = Common.dateDiff(formData.dob, new Date()).year;
					formData.present_EXPERIENCE = Common.dateDiff(formData.doj, new Date()).year;
					formData.service_LEFT = Common.dateDiff(new Date(), formData.dor).year;
					
					formData.advanceType = defaultAdvanceType;					
					formData.eligibleAmount = getEligibleAmount();
					
					Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, formData);
				}
				else{		
					resetForm();			
					Common.showToast({message: "Please enter valid Employee Code."});
				}
			}});
		}
	};
	
	var submitLoan = function(sType){
		var sURL = HOST + '/pfloan/create'
		var postData = getLoanData();
		postData.submitted = 0;
		postData.type = 2;
		
		if(sType == 'SUBMIT'){
			postData.submitted = 1;
		}
		
		$.ajax({
			type: 'post',
			url: sURL, 
			data: JSON.stringify(postData),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				uploadFile(result);
			}
		});
	};
	
	var uploadFile = function(result){
		if(formData.hasFile){
			var fileInput = $('input[type=file]');
			
			if(fileInput != null && fileInput.length > 0){
				if(fileInput[0].files != null && fileInput[0].files.length > 0){
					var files = fileInput[0].files;
					var fileData = new FormData();
     				fileData.append("pf_loan_doc", files[0]);
     				fileData.append("id", result.id);
      			
      				$.ajax({
						type: 'post',
						url: HOST + '/pfloan/uploadFile', 
						data: fileData,
						contentType: false,
						cache: false,
	   					processData:false,
						success: function(result){
							formSuccessHandler();
						}
					});
				}
			}
		}
		else{
			formSuccessHandler();
		}
	}
	
	var resetForm = function(){
		formData = {};
		calculateAmount(0);	
		$("#searchData").jqxInput('val', '');
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'fileUpload').jqxFileUpload('cancelAll');
		Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, {NOOFEMI: defaultEMI, REMARKS: '', advanceType: defaultAdvanceType});
	}
	
	var formSuccessHandler = function(){
		resetForm();
		Common.showToast({message: "The record has been submitted successfully."});
	}
	
	var getLoanData = function(){
		var postData = {};
		var fields = [
			{field: 'empcode', name: 'empcode'},
			{field: 'advanceType', name: 'advanceType'}, 
			{field: 'requiredAmount', name: 'requiredAmount'}, 
			{field: 'emiAmount', name: 'EMIAMOUNT'}, 
			{field: 'noOfEMI', name: 'NOOFEMI'}, 
			{field: 'newNetSalary', name: 'NEWNETSALARY'}, 
			{field: 'newNetSalaryPer', name: 'NEWNETSALARYPER'},
			{field: 'remarks', name: 'REMARKS'}
		];
		
		for(var sKey in fields){
			var row = fields[sKey];
			
			if(formData[row.name] != null){
				postData[row.field] = formData[row.name];
			}
		}
		
		return postData;
	}	
	
	var calculateAmount = function(requiredAmount){
		var form = $('#dvRefundablePFLoan');	
		formData.requiredAmount = requiredAmount;
		
		if(formData.requiredAmount < 1 || formData.requiredAmount > formData.eligibleAmount || formData.age > 50 || formData.present_EXPERIENCE < 10){
			form.jqxForm('getComponentByName', 'saveDraft').jqxButton({disabled: true});
			form.jqxForm('getComponentByName', 'saveSubmit').jqxButton({disabled: true});
		}
		else{
			form.jqxForm('getComponentByName', 'saveDraft').jqxButton({disabled: false});
			form.jqxForm('getComponentByName', 'saveSubmit').jqxButton({disabled: false});
		}
		
		Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, formData);
	};
	
	this.getTemplate = function(){
		return jqxFormTmp;
	}
	
	this.init = function(){		
	    $('#dvRefundablePFLoan').jqxForm({
	        template: jqxFormTmp,
	        theme: theme,
	        value: {},
	        padding: { left: 10, top: 10, right: 10, bottom: 10 }
	    });
	    	       
	    bindComponent();
	    Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateDisable, {});
	    calculateAmount(0);
	};
}
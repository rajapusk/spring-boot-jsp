var VPFApply = function(){
	var HOST = 'http://localhost:' + 9092;
	//var HOST = window.location.host;
	var theme =  'light';
	var Common = new CommonMethod(theme);
	var formData = {};
	var formWidth = 850;	        	
	var labelWidth = 180;
	var controlWidth = ((formWidth / 2) - labelWidth);
	    		
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
					bind: 'worksiteCode',
	                type: 'text',
	                name: 'worksiteCode',
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
	                label: 'Net Salary',
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
					bind: 'netSalPer',
	                type: 'number',
	                name: 'netSalPer',
	                label: 'Net Salary Percetage',
	                icon: 'fa-percent',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'presentVPF',
	                type: 'number',
	                name: 'presentVPF',
	                label: 'Present VPF Contribution',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'revisedVPF',
	                type: 'number',
	                name: 'revisedVPF',
	                label: 'Revised VPF Contribution',
	                disabled: false,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'NEWNETSALARY',
	                type: 'number',
	                name: 'NEWNETSALARY',
	                disabled: true,
	                labelPosition: 'top',
	                label: 'New Net Salary',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'NEWNETSALARYPER',
	                type: 'number',
	                name: 'NEWNETSALARYPER',
	                disabled: true,
	                labelPosition: 'top',
	                label: 'New Net Salary Percetage',
	                icon: 'fa-percent',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				}
			]
		},{
			columns: [
				{
	                bind: 'REMARKS',
	                type: 'textArea',
	                name: 'REMARKS',
	                label: 'Remarks',
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: '100%',
	                align: 'left',
					columnWidth: '60%'
	            }
			]
		},{
	        type: 'blank',
	        rowHeight: '10px',
	        name: 'acceptChk',
	        columnWidth: '40%'
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
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'acceptChk')
		.append(
			'<div id="chkAceeptVFP">'
				+ 'Note : VPF Contribution change will be effective from 1st calender day of the month'
			+'</div>');
			
		$('#chkAceeptVFP').jqxCheckBox({ width: 120, height: 25, theme:theme});
		
		$('#searchData').on('change', loadEmpDetails);				
		$("#searchData").jqxInput({ width: '250px', height: '30px', placeHolder: 'Enter the employee code'});
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'revisedVPF').on('change', function(event){
			var revisedVPF = 0;
			
			if(event.args.value != null && event.args.value.length > 0){
				revisedVPF = parseInt(event.args.value);
			}
		
			calculateAmount(revisedVPF);
		});
				
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'saveDraft').on('click', function(event){
			submitLoan('DRAFT');
		});
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'saveSubmit').on('click', function(event){
			submitLoan('SUBMIT');
		});
						
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'REMARKS').jqxTextArea({
			height: 75, 
			theme: theme, 
			width: controlWidth
		}).on('change', function(event){
			formData.REMARKS = $("#dvRefundablePFLoan").jqxForm('getComponentByName', 'REMARKS').val();
		});
	};
	
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
		var sURL = HOST + '/vpf/create'
		var postData = getLoanData();
		postData.submitted = 0;
		postData.type = 1;
		
		if(sType == 'SUBMIT'){
			postData.submitted = 1;
		}
		
		$.ajax({
			type: 'post',
			url: sURL, 
			data: JSON.stringify(postData),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				formSuccessHandler();
			}
		});
	};
	
	var resetForm = function(){
		formData = {};
		calculateAmount(0);		
		$("#searchData").jqxInput('val', '');
		
		var config = {REMARKS: ''};		
		Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, config);
	}
	
	var formSuccessHandler = function(){
		resetForm();
		Common.showToast({message: "The record has been submitted successfully."});
	}
		
	var getLoanData = function(){
		var postData = {};
		var fields = [
			{field: 'empcode', name: 'empcode'},
			{field: 'presentVPF', name: 'presentVPF'}, 
			{field: 'revisedVPF', name: 'revisedVPF'}, 
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
	
	var calculateAmount = function(revisedVPF){
		var form = $('#dvRefundablePFLoan');

		formData.revisedVPF = revisedVPF;			
		formData.NEWNETSALARY = (formData.revisedVPF > 0 ? formData.prevNetSalary + (formData.presentVPF - formData.revisedVPF) : 0);		
		formData.NEWNETSALARYPER = (formData.NEWNETSALARY > 0 ? ((formData.NEWNETSALARY / formData.monthly_SALARY) * 100) : 0);		
		
		if(isNaN(formData.NEWNETSALARYPER) || formData.NEWNETSALARYPER < 50){
			form.jqxForm('getComponentByName', 'saveDraft').jqxButton({disabled: true});
			form.jqxForm('getComponentByName', 'saveSubmit').jqxButton({disabled: true});
		}
		else{
			form.jqxForm('getComponentByName', 'saveDraft').jqxButton({disabled: false});
			form.jqxForm('getComponentByName', 'saveSubmit').jqxButton({disabled: false});
		}
		
		Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan',  Common.updateValue, formData);
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
	    Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan',  Common.updateDisable, {});
	    calculateAmount(0);
	};
}
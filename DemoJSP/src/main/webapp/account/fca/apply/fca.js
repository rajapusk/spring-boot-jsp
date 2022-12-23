var FCA = function(){
	var theme =  'light';
	var Common = new CommonMethod(theme);
	var formData = {};
	var formWidth = 850;	        	
	var labelWidth = 180;
	var controlWidth = ((formWidth / 2) - labelWidth);
	var entitled = {};
	var quarter = {Q1: ['Apr', 'May', 'Jun'], Q2: ['Jul', 'Aug', 'Sep'], Q3: ['Oct', 'Nov', 'Dec'], Q4: ['Jan', 'Feb', 'Mar']};
		
	var jqxFormTmp = [
		{
	        type: 'blank',
	        rowHeight: '5px'
	    },{
			columns: [
				{
			        type: 'blank',
			        name: 'searchPanel',
			        rowHeight: '5px',
			        columnWidth: '20%'
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
					bind: 'glcode',
	                type: 'text',
	                name: 'glcode',
	                label: 'GL Code',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
			        type: 'blank',
			        name: 'quarterType',
			        rowHeight: '5px',
			        columnWidth: '20%'
			    }
			]
		},{
			columns: [
				{
					type: 'blank',
					name: 'forMonth',
					columnWidth: '20%',					
				},{
					bind: 'entitledAmount',
					name: 'entitledAmount',
	                type: 'number',
	                label: 'Entitled Amount',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{  
					bind: 'claimAmount',
	                type: 'number',
	                name: 'claimAmount',
	                label: 'Claim Amount',
	                disabled: true,
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
	                bind: 'REMARKS',
	                type: 'textArea',
	                name: 'REMARKS',
	                label: 'Remarks',
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: '100%',
	                align: 'left',
					columnWidth: '40%'
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
		
		$("#dvRefundablePFLoan")
		.jqxForm('getComponentByName', 'quarterType').append(
			 '<div class="scTitlePanel">'
			 	+ '<div class="scTitle">'
			 		+ 'Quarter Type'
			 	+ '</div>'
				+ '<div class="scOptionBox">'
					+ '<div class="scGroupOption">'
						+ '<div id="optQ1">Q1</div>'
						+ '<div id="optQ2">Q2</div>'
						+ '<div id="optQ3">Q3</div>'
						+ '<div id="optQ4">Q4</div>'
					+ '</div>'
				+ '</div>'
			+ '</div>'
		);
		
		$('#optQ1').jqxRadioButton({theme: theme });
		$('#optQ2').jqxRadioButton({theme: theme});
		$('#optQ3').jqxRadioButton({theme: theme});
		$('#optQ4').jqxRadioButton({theme: theme});
				
		$("#dvRefundablePFLoan")
		.jqxForm('getComponentByName', 'forMonth').append(
			 '<div class="scTitlePanel">'
			 	+ '<div class="scTitle">'
			 		+ 'For the Month'
			 	+ '</div>'
				+ '<div class="scDropdownBox">'
					+ '<div class="scDropdownOption" style="height: 46px;">'
						+ '<div id="forMonth" class="scFullWidth"></div>'
					+ '</div>'
				+ '</div>'
			+ '</div>'
		);
		
		$("#forMonth").jqxComboBox({ theme: theme, multiSelect: true, height: 46, autoOpen: true, showArrow: true});
		$('#forMonth').on('change', function(event) {
			let claimAmount = 0;
		    let items = $("#forMonth").jqxComboBox('getSelectedItems');
		    formData.months = '';
		    
		    items.forEach(element => {
				if(formData.months.length > 0){
					formData.months += ',';
				}
				
				formData.months += element.value;
				claimAmount += 500;
			});
			
			if(entitled != null && entitled[formData.grade] != null){
				formData.entitledAmount = entitled[formData.grade];
			}
			
			formData.claimAmount = (formData.entitledAmount < claimAmount ? formData.entitledAmount : claimAmount);
			calculateAmount();
		});

		var setComboSource = function(event){
			var checked = event.args.checked; 
			
			if(checked){    
				formData.quarterType = event.currentTarget.innerText;
				formData.claimAmount = 0;
		    	formData.months = '';
				$("#forMonth").jqxComboBox({source:  quarter[formData.quarterType]});
				disableComboBox(false);
				calculateAmount();
			}
		}
		
		$('#optQ1').on('change', setComboSource); 
		$('#optQ2').on('change', setComboSource);
		$('#optQ3').on('change', setComboSource);
		$('#optQ4').on('change', setComboSource);
		$('#searchData').on('change', loadEmpDetails);				
		$("#searchData").jqxInput({ width: '250px', height: '30px', placeHolder: 'Enter the employee code'});		
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'saveDraft').on('click', function(event){
			submitLoan('DRAFT');
		});
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'saveSubmit').on('click', function(event){
			submitLoan('SUBMIT');
		});
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'acceptChk')
		.append(
			'<div id="chkAceeptVFP">'
				+ 'Declaration: I hereby declare that, I do not own a vehicle on my name in the current location'
			+'</div>');
			
		$('#chkAceeptVFP').jqxCheckBox({ width: 120, height: 25, theme:theme});
		$('#chkAceeptVFP').on('change', function(event){
			formData.acceptDeclaration = event.args.checked;
			
			calculateAmount();
		}); 
				
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'REMARKS').jqxTextArea({
			height: 75, 
			theme: theme, 
			width: controlWidth
		}).on('change', function(event){
			formData.REMARKS = $(event.currentTarget).val();
		});
	};
	
	var loadEmpDetails = function(){
		var empCode = $("#searchData").jqxInput('val');
		
		if(empCode != null && empCode != ''){
			var sURL = Common.HOST + '/pfaccount/get/' + empCode
		
			Common.callGET(sURL, function(result){
				if(result != null && result != ''){
					formData = result;
					formData.doj = new Date(formData.doj);
					
					//Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, formData);
					calculateAmount();
					resetOption(false);
				}
				else{	
					resetForm();
					Common.showToast({message: "Please enter valid Employee Code."});
				}
			});
		}
	};
		
	var submitLoan = function(sType){
		var sURL = Common.HOST + '/fca/create'
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
				uploadFile(result);
			}
		});
	};	
	
	var uploadFile = function(result){
		if(formData.hasFile){
			var fileInput = $('input[type=file]');
			
			if(fileInput != null && fileInput.length > 0){
				Common.uploadFiles({id: result.id, empCode: result.empcode, fileInput: fileInput, fileIndex: 0, url: Common.HOST + '/fca/uploadFile', successHandler: formSuccessHandler});				
			}
		}else{
			formSuccessHandler();
		}
	}
	
	var disableComboBox = function(disabled){
		var source = $('#forMonth').jqxComboBox('source');
		
		if(!disabled && (source == null || source.length == 0)){
			disabled = true;
		}
		
		$("#forMonth").jqxComboBox({ disabled: disabled, autoOpen: !disabled});
	}
	
	var resetOption = function(disabled){
		for(var key = 1; key<=4; key++){
			$('#optQ' + key).jqxRadioButton({checked: false, disabled: disabled });
		}
				
		disableComboBox(disabled);
	}
	
	var resetForm = function(){
		formData = {REMARKS: ''};
		calculateAmount();				
		//var config = {REMARKS: ''};		
		//Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, config);
		resetOption(true);
		$("#searchData").jqxInput('val', '');				
		$("#forMonth").jqxComboBox({source: []});
	}
	
	var formSuccessHandler = function(){
		resetForm();
		Common.showToast({message: "The record has been submitted successfully."});
	}
	
	var getLoanData = function(){
		var postData = {};
		var fields = [
			{field: 'empCode', name: 'empcode'},
			{field: 'months', name: 'months'},
			{field: 'quarterType', name: 'quarterType'},
			{field: 'entitledAmount', name: 'entitledAmount'},
			{field: 'claimAmount', name: 'claimAmount'},
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
	
	var calculateAmount = function(){
		var form = $('#dvRefundablePFLoan');
		
		if(entitled != null && entitled[formData.grade] != null){
			formData.entitledAmount = entitled[formData.grade];
		}

		if(formData.claimAmount == null || formData.claimAmount == 0 || formData.acceptDeclaration == null || formData.acceptDeclaration == false){
			form.jqxForm('getComponentByName', 'saveDraft').jqxButton({disabled: true});
			form.jqxForm('getComponentByName', 'saveSubmit').jqxButton({disabled: true});
		}
		else{
			form.jqxForm('getComponentByName', 'saveDraft').jqxButton({disabled: false});
			form.jqxForm('getComponentByName', 'saveSubmit').jqxButton({disabled: false});
		}
		
		Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan',  Common.updateValue, formData);
	};
	
	var loadEntitled = function(){		
		var sURL = Common.HOST + '/fca/getEntitle'
		
		Common.callGET(sURL, function(result){			
			if(result != null && result != ''){
				entitled = result;
			}
		});
	}
	
	this.init = function(){		
	    $('#dvRefundablePFLoan').jqxForm({
	        template: jqxFormTmp,
	        theme: theme,
	        value: {},
	        padding: { left: 10, top: 10, right: 10, bottom: 10 }
	    });
	   	
	   	loadEntitled(); 
	    bindComponent();
	    resetOption(true);
	    Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan',  Common.updateDisable, {});
	    calculateAmount();
	};
}
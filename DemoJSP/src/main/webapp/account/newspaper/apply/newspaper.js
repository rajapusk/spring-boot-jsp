var Newspaper = function(){
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
			    },{
					type: 'blank',
					name: 'forMonth',
					columnWidth: '20%',					
				}
			]
		},{
			columns: [
				{
					bind: 'vendorName',
	                type: 'text',
	                name: 'vendorName',
	                label: 'Vendor Name',
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'invoiceDate',
					type: 'date',
					label: 'Invoice Date',
					info: '',
					name: 'invoiceDate',
					labelPosition: 'top',
					labelWidth: labelWidth + 'px',
					width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'invoiceNo',
					type: 'text',
					name: 'invoiceNo',
					label: 'Invoice No',
					labelPosition: 'top',
					autoopen: true,
					labelWidth: labelWidth + 'px',
					width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
					bind: 'invoiceAmount',
	                type: 'number',
	                name: 'invoiceAmount',
	                label: 'Invoice Amount',
	                labelPosition: 'top',
	                labelWidth: labelWidth + 'px',
	                width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
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
				}
			]
		},{
			columns: [
				{  
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
	                align: 'left',
					columnWidth: '40%'
	            },{
					type: 'blank',
			        name: 'fileUpload',
			        rowHeight: '75px',
			        columnWidth: '40%'
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
		
		$("#dvRefundablePFLoan")
		.jqxForm('getComponentByName', 'fileUpload')
		.jqxFileUpload({ 
			width: '100%%', 
			fileInputName: 'emp_doc',
			multipleFilesUpload: true, 
			autoUpload: false, 
			theme: theme, 
			uploadTemplate: 'primary' ,
			uploadUrl:  Common.HOST + '/newspaper/uploadFile' 
		}).on('select', function (event) {
		   	formData.hasFile = true;
		}).on('remove', function (event) {
		   	formData.hasFile = false;
		});	
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'invoiceDate').on('valueChanged', function(event){
			var config = {
				msg: 'Invoice date is exceeding 30days from the expense date, hence it is not allowed',
				date: event.args.date,
				prob: 'invoiceDate',
				dayProb: 'invoiceDateDays',
				event: event
			}
			
			dateValidation(config);
		});
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'invoiceNo').on('change', function(event){
			formData.invoiceNo = event.args.value;
		});
		
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'vendorName').on('change', function(event){
			formData.vendorName = event.args.value;
		});
		
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'invoiceAmount').on('change', function(event){
			formData.invoiceAmount = !isNaN(event.args.value) ? parseInt(event.args.value) : 0;
						
			calculateAmount();
		});
		
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
		    let items = $("#forMonth").jqxComboBox('getSelectedItems');
		    formData.months = '';
		    formData.entitledAmount = 0;
		    
		    items.forEach(element => {
				if(formData.months.length > 0){
					formData.months += ',';
				}
				
				formData.months += element.value;
				
				if(entitled != null && entitled[formData.grade] != null){
					formData.entitledAmount += entitled[formData.grade];
				}
			});
			
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
		
		/*$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'acceptChk')
		.append(
			'<div id="chkAceeptVFP">'
				+ 'Declaration: I hereby declare that, I do not own a vehicle on my name in the current location'
			+'</div>');
			
		$('#chkAceeptVFP').jqxCheckBox({ width: 120, height: 25, theme:theme});
		$('#chkAceeptVFP').on('change', function(event){
			formData.acceptDeclaration = event.args.checked;
			
			calculateAmount();
		}); */
				
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'REMARKS').jqxTextArea({
			height: 75, 
			theme: theme, 
			width: controlWidth
		}).on('change', function(event){
			formData.REMARKS = $(event.currentTarget).val();
		});
	};
	
	var dateValidation = function(config){
		var diff = Common.dateDiff(config.date, new Date());
		formData[config.prob] = config.date;
		formData[config.dayProb] = diff.totalDays;
		
		if(diff.totalDays > 30){
			Common.setInfo(config);
		}else{
			Common.closeInfo(config);
		}
		
		calculateAmount();
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
		var sURL = Common.HOST + '/newspaper/create'
		var postData = getLoanData();
		postData.submitted = 0;
		postData.type = 1;
		postData.invoiceDate = Common.dateFormat(postData.invoiceDate, 'yyyy-MM-dd');
		
		if(sType == 'SUBMIT'){
			postData.submitted = 1;
		}
		
		
		if(formData.hasFile){
			$.ajax({
				type: 'post',
				url: sURL, 
				data: JSON.stringify(postData),
				contentType: "application/json; charset=utf-8",
				success: function(result){
					uploadFile(result);
				}
			});
		} else {
			Common.showToast({message: "Please select the invoice document"});
		}
	};	
	
	var uploadFile = function(result){
		if(formData.hasFile){
			var fileInput = $('input[type=file]');
			
			if(fileInput != null && fileInput.length > 0){
				Common.uploadFiles({id: result.id, empCode: result.empcode, fileInput: fileInput, fileIndex: 0, url: Common.HOST + '/newspaper/uploadFile', successHandler: formSuccessHandler});				
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
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'fileUpload').jqxFileUpload('cancelAll');			
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
			{field: 'vendorName', name: 'vendorName'}, 
			{field: 'invoiceDate', name: 'invoiceDate'}, 
			{field: 'invoiceNo', name: 'invoiceNo'}, 
			{field: 'invoiceAmount', name: 'invoiceAmount'}, 
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
		
		if((formData.entitledAmount == null || formData.entitledAmount == 0) &&(entitled != null && entitled[formData.grade] != null)){
			formData.entitledAmount = entitled[formData.grade];
		}

		formData.claimAmount = (formData.entitledAmount < formData.invoiceAmount ? formData.entitledAmount : formData.invoiceAmount);
		
		if(formData.claimAmount == null || formData.claimAmount == 0){
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
		var sURL = Common.HOST + '/newspaper/getEntitle'
		
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
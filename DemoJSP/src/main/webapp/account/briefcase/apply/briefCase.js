var BriefCase = function(){
	var theme =  'light';
	var Common = new CommonMethod(theme);
	var formData = {};
	var formWidth = 850;	        	
	var labelWidth = 180;
	var controlWidth = ((formWidth / 2) - labelWidth);
	var entitled = {};
		
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
				}
			]
		},{
			columns: [
				{
					bind: 'claimDate',
					type: 'date',
					label: 'Prev Claim Date',
					name: 'claimDate',
					info: '',
					labelPosition: 'top',
					labelWidth: labelWidth + 'px',
					width: controlWidth + 'px',
					align: 'left',
					columnWidth: '20%'
				},{
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
				}
			]
		},{
			columns: [
				{
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
	                align: 'left',
					columnWidth: '40%'
	            },{
					type: 'blank',
			        name: 'fileUpload',
			        rowHeight: '75px',
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
			fileInputName: 'emp_doc',
			multipleFilesUpload: true, 
			autoUpload: false, 
			theme: theme, 
			uploadTemplate: 'primary' ,
			uploadUrl:  Common.HOST + '/ba/uploadFile' 
		}).on('select', function (event) {
		   	formData.hasFile = true;
		}).on('remove', function (event) {
		   	formData.hasFile = false;
		});		
			
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'claimDate').on('valueChanged', function(event){
			var config = {
				msg: 'Claim date is exceeding 30days from the expense date, hence it is not allowed',
				date: event.args.date,
				prob: 'claimDate',
				dayProb: 'claimDateDays',
				event: event
			}
			
			dateValidation(config);
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

					Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan', Common.updateValue, formData);
				}
				else{	
					resetForm();
					Common.showToast({message: "Please enter valid Employee Code."});
				}
			});
		}
	};
		
	var submitLoan = function(sType){
		var sURL = Common.HOST + '/ba/create'
		var postData = getLoanData();
		postData.submitted = 0;
		postData.type = 1;
		
		postData.claimDate = Common.dateFormat(postData.claimDate, 'yyyy-MM-dd');
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
				Common.uploadFiles({id: result.id, empCode: result.empcode, fileInput: fileInput, fileIndex: 0, url: Common.HOST + '/ba/uploadFile', successHandler: formSuccessHandler});				
			}
		}
	}
	
	var resetForm = function(){
		formData = {};
		calculateAmount();		
		$("#searchData").jqxInput('val', '');
		$("#dvRefundablePFLoan").jqxForm('getComponentByName', 'fileUpload').jqxFileUpload('cancelAll');
		
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
			{field: 'empCode', name: 'empcode'},
			{field: 'claimDate', name: 'claimDate'}, 
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
		
		if(entitled != null && entitled[formData.grade] != null){
			formData.entitledAmount = entitled[formData.grade];
		}
		
		if(formData.entitledAmount <= formData.invoiceAmount){
			formData.claimAmount = formData.entitledAmount;
		} else {
			formData.claimAmount = formData.invoiceAmount;
		}
		
		if(formData.claimAmount == null || formData.claimAmount == 0 || formData.claimDate == null || formData.claimDateDays > 30 
			|| formData.invoiceDate == null || formData.invoiceDateDays > 30){
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
		var sURL = Common.HOST + '/ba/getEntitle'
		
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
	    Common.loopInput(jqxFormTmp, 'dvRefundablePFLoan',  Common.updateDisable, {});
	    calculateAmount();
	};
}
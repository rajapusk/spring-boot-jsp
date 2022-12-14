var Ledger = function(){
	var HOST = 'http://localhost:' + 9092;
	//var HOST = window.location.host;
	var theme = 'light';
	var Common = new CommonMethod(theme);
	var srcEmployee = {
		datatype: 'json',
        datafields: [
			{name: 'id', type: 'string'},
            {name: 'empCode', type: 'string'},
            {name: 'name', type: 'string'},             
            {name: 'doj', type: 'string'}, 
            {name: 'experience', type: 'number'}, 
            {name: 'ob_ee_contri', type: 'number'},  
            {name: 'ob_vpf', type: 'number'}, 
            {name: 'ob_er_contri', type: 'number'},
            {name: 'pr_exp', type: 'number'},
            {name: 'roi', type: 'number'},
            {name: 'withdrawals', type: 'object'},
            {name: 'lstContributions', type: 'object'}
       	],
       	id: 'employeeID',
        localdata: []
    };
    
	var colEmployee =  [
		Common.iconCell({icon: 'fa-eye', datafield: 'View'}),
		Common.snoCell(),
		{ text: 'EMP Code', datafield: 'empCode', width: 90, editable: false },
		{ text: 'Name', datafield: 'name', editable: false },
		{ text: 'DOJ', datafield: 'doj', width: 100, editable: false, cellsalign: 'right' },
		{ text: 'Experience', datafield: 'pr_exp', width: 100, editable: false, cellsalign: 'right' },
		{ text: 'OB-EE Contri', datafield: 'ob_ee_contri', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader },
		{ text: 'OB-VPF', datafield: 'ob_vpf', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader },
		{ text: 'OB-ER Contri', datafield: 'ob_er_contri', width: 100, editable: false, cellsalign: 'right', renderer: Common.numberIconHeader }, 
		{ text: 'ROI', datafield: 'roi', width: 100, editable: false, cellsalign: 'right', renderer: Common.percentageIconHeader}
	];
	
	var srcWithdraw = {
		datatype: 'json',
        datafields: [
			{name: 'id', type: 'string'},
			{name: 'empCode', type: 'string'},
            {name: 'withdrawn_date', type: 'string'},
            {name: 'withdrawnAmount', type: 'string'},
            {name: 'withdrawalPurpose', type: 'string'}
            
       	],
       	id: 'WithdrawID',
        localdata: []
    };
    
    var colWithdraw =  [
		Common.snoCell(),
		{ text: 'EMP Code', datafield: 'empCode', width: '25%', editable: false },	
		{ text: 'Withdrawn Date', datafield: 'withdrawn_date', width: '25%', editable: false },
		{ text: 'Withdrawn Amount', datafield: 'withdrawnAmount', width: '25%', editable: false, renderer: Common.numberIconHeader },
		{ text: 'Withdrawal Purpose', datafield: 'withdrawalPurpose', editable: false }
	];
	
	var srcContribution = {
		datatype: 'json',
        datafields: [
			{name: 'id', type: 'string'},
			{name: 'empCode', type: 'string'},
            {name: 'wage_month', type: 'string'},             
            {name: 'date_of_credit', type: 'string'}, 
            {name: 'basic_total', type: 'number'},
			{name: 'ee_contri', type: 'number'},  
			{name: 'vpf', type: 'number'},
			{name: 'ee_interest', type: 'number'}, 
            {name: 'ee_total', type: 'number'},            
            {name: 'er_contri', type: 'number'},               
            {name: 'er_interest', type: 'number'},
            {name: 'er_total', type: 'number'}
       	],
        localdata: []
    };
    
	var colContribution =  [
		Common.snoCell(),		
		{ text: 'EMP Code', datafield: 'empCode', width: 90, editable: false },
		{ text: 'Wage Month', datafield: 'wage_month', editable: false },
		{ text: 'Date of Credit', datafield: 'date_of_credit', width: 100, editable: false, cellsalign: 'right' },
		{ text: 'Basic Total', datafield: 'basic_total', width: 100, editable: false, cellsalign: 'right', renderer: Common.numberIconHeader },
		{ text: 'EE Contri', datafield: 'ee_contri', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader },
		{ text: 'VPF', datafield: 'vpf', width: 100, editable: false, cellsalign: 'right', renderer: Common.numberIconHeader },
		{ text: 'EE Interest', datafield: 'ee_interest', width: 100, editable: false, cellsalign: 'right', renderer: Common.numberIconHeader }, 
		{ text: 'EE Total', datafield: 'ee_total', width: 100, editable: false, cellsalign: 'right', renderer: Common.numberIconHeader }, 
		{ text: 'ER Contri', datafield: 'er_contri', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader },
		{ text: 'ER Interest', datafield: 'er_interest', width: 100, editable: false, cellsalign: 'right', renderer: Common.numberIconHeader }, 		      
		{ text: 'ER Total', datafield: 'er_total', editable: false, width: 100, cellsalign: 'right', renderer: Common.numberIconHeader}
	];
    
    var renderNestedGrid = function (index, parentElement, gridElement, record) {		
        var grid = $($(parentElement).children()[0]);
        
        if(record != null && record.withdrawals != null){
			srcWithdraw.localdata = record.withdrawals;
	        
	        if (grid != null) {
	            grid.jqxGrid({
	                source: new $.jqx.dataAdapter(srcWithdraw), 
	                width: '100%', 
	                height: 200,
	                sortable: true,
	                pageable: true,
	                columns: colWithdraw
	            });
	        }
		}
    }
    
	var renderGrid = function(){
		$("#dvPFAccount").jqxGrid({
            columns: colEmployee,
            theme: theme,
            selectionmode: 'multiplerowsextended',
            sortable: true,
            pageable: true,
            autoheight: true,
            columnsresize: false,
            altrows: true,
            editable: true,
            filterable: true,
            rowdetails: true,
            source: new $.jqx.dataAdapter(srcEmployee),
            initrowdetails: renderNestedGrid,
            rowdetailstemplate: { 
				rowdetails: "<div></div>", 
				rowdetailsheight: 220, 
				rowdetailshidden: true 
			},
            width: '100%'
        }).on('cellvaluechanged', function (event) 
		{
			console.log('cellvaluechanged', event.args)
		}).on('cellclick', function (event) 
		{
			if(event.args.datafield == 'View' && event.args.row.bounddata != null && event.args.row.bounddata.lstContributions != null){
				//srcContribution.localdata = event.args.row.bounddata.lstContributions;
				//$("#dvMasterLedger").jqxGrid('updatebounddata', 'cells');
			}
		});
		
		$("#dvPFAccount").on('rowselect', function (event) {
           if(event.args.row != null && event.args.row.lstContributions != null){
				srcContribution.localdata = event.args.row.lstContributions;
				$("#dvMasterLedger").jqxGrid('updatebounddata', 'cells');
			}
        });
		
		$("#dvMasterLedger").jqxGrid({
            columns: colContribution,
            theme: theme,
            selectionmode: 'multiplerowsextended',
            sortable: true,
            pageable: true,
            autoheight: true,
            columnsresize: false,
            altrows: true,
            editable: true,
            filterable: true,
            source: new $.jqx.dataAdapter(srcContribution),
            width: '100%'
        });
		
        loadData();
	}
	
	var loadData = function(){
		$.ajax({
			type: 'GET',
			url: HOST + '/ledger/getall',
			contentType: "application/json; charset=utf-8",
			success: function(result){
				if(result != null && result != ''){
					srcEmployee.localdata = result;
					$("#dvPFAccount").jqxGrid('updatebounddata', 'cells');
					$("#dvPFAccount").jqxGrid('selectrow', 0);
				}
			}
		});
	}
	
	this.init = function(){
		renderGrid();
	}
}
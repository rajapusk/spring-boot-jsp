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
            {name: 'contributions', type: 'object'}
       	],
       	id: 'employeeID',
        localdata: []
    };
    
	var colEmployee =  [
		Common.iconCell({icon: 'fa-eye', datafield: 'View'}),
		Common.snoCell(),
		{ text: 'EMP Code', datafield: 'empCode', width: 90, editable: false },
		{ text: 'Name', datafield: 'name', editable: false },
		{ text: 'DOJ', datafield: 'doj', width: 140, editable: false, cellsalign: 'right' },
		{ text: 'Experience', datafield: 'experience', width: 140, editable: false, cellsalign: 'right' },
		{ text: 'OB-EE Contribution', datafield: 'ob_ee_contri', editable: false, width: 140, cellsalign: 'right' },
		{ text: 'OB-VPF', datafield: 'ob_vpf', editable: false, width: 140, cellsalign: 'right' },
		{ text: 'OB-ER Contribution', datafield: 'ob_er_contri', width: 140, editable: false, cellsalign: 'right' }, 
		{ text: 'OB-ER Contribution', datafield: 'pr_exp', width: 140, editable: false, cellsalign: 'right' },
		{ text: 'ROI', datafield: 'roi', width: 140, editable: false, cellsalign: 'right'}
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
		{ text: 'Withdrawn Amount', datafield: 'withdrawnAmount', width: '25%', editable: false },
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
            {name: 'er_contri', type: 'number'}, 
            {name: 'vpf', type: 'number'},
            {name: 'ee_interest', type: 'number'},   
            {name: 'er_interest', type: 'number'},
            {name: 'ee_total', type: 'number'},
            {name: 'er_total', type: 'number'}
       	],
        localdata: []
    };
    
	var colContribution =  [
		Common.snoCell(),		
		{ text: 'EMP Code', datafield: 'empCode', width: 90, editable: false },
		{ text: 'Month', datafield: 'wage_month', editable: false },
		{ text: 'Date of Credit', datafield: 'date_of_credit', width: 120, editable: false, cellsalign: 'right' },
		{ text: 'Basic Total', datafield: 'basic_total', width: 120, editable: false, cellsalign: 'right' },
		{ text: 'EE Contribution', datafield: 'ee_contri', editable: false, width: 120, cellsalign: 'right' },
		{ text: 'ER Contribution', datafield: 'er_contri', editable: false, width: 120, cellsalign: 'right' },
		{ text: 'VPF', datafield: 'vpf', width: 120, editable: false, cellsalign: 'right' },
		{ text: 'EE Interest', datafield: 'ee_interest', width: 120, editable: false, cellsalign: 'right' }, 
		{ text: 'ER Interest', datafield: 'er_interest', width: 120, editable: false, cellsalign: 'right' }, 
		{ text: 'EE Total', datafield: 'ee_total', width: 120, editable: false, cellsalign: 'right' },       
		{ text: 'ER Total', datafield: 'er_total', width: 120, editable: false, cellsalign: 'right'}
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
			if(event.args.datafield == 'View' && event.args.row.bounddata != null && event.args.row.bounddata.contributions != null){
				//srcContribution.localdata = event.args.row.bounddata.contributions;
				//$("#dvMasterLedger").jqxGrid('updatebounddata', 'cells');
			}
		});
		
		$("#dvPFAccount").on('rowselect', function (event) {
           if(event.args.row != null && event.args.row.contributions != null){
				srcContribution.localdata = event.args.row.contributions;
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
				srcEmployee.localdata = result;
				$("#dvPFAccount").jqxGrid('updatebounddata', 'cells');
				$("#dvPFAccount").jqxGrid('selectrow', 0);
			}
		});
	}
	
	this.init = function(){
		renderGrid();
	}
}
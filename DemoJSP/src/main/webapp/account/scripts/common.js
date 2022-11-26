var CommonMethod = function(theme){
	var _common = {};
	var notifyConfig = {};
	
	var init = function(){
		var body = $("body");
		
		body.append('<div id="dvNotification"><div id="dvMessage"></div></div>');
		$("#dvNotification").jqxNotification({
			width: 400, 
			position: "top-right", 
			opacity: 0.9,
			theme: theme, 
            autoOpen: false, 
            animationOpenDelay: 800, 
            autoClose: true, 
            autoCloseDelay: 3000, 
            template: "info"
        });
        
        $('#dvNotification').on('close', function () { 
		     notifyConfig = {};
		});
	}
	
	_common.showToast = function(config){
		if(config != null && config.message != notifyConfig.message){
			notifyConfig = config;
			
			$("#dvMessage").html(config.message);
			$("#dvNotification").jqxNotification('open');
			
		}
	}
	
	_common.dateDiff = function(fromDate, toDate){
	    var month_diff = toDate.getTime() - fromDate.getTime();
	    var age_dt = new Date(month_diff); 
	    var year = age_dt.getUTCFullYear();  
	    var age = Math.abs(year - 1970);  
	    
	    return {year: age};
	}
	
	_common.updateDisable = function(formId, row, config){
		if(row.name != null){
			var input = $('#' + formId).jqxForm('getComponentByName', row.name);
	        
			if(input != null){
				if(row.disabled == true){
					if(row.type == 'text'){
						input.jqxInput({disabled: true,  theme: theme});
					}
					else if(row.type == 'date'){
						input.jqxDateTimeInput({disabled: true, theme: theme});
					}else if(row.type == 'number'){
						input.jqxNumberInput({disabled: true, theme: theme});
						input.addClass("scNumberInput");
					}else if(row.type == 'option'){
						input.jqxDropDownList({disabled: true, theme: theme});
					}
				}
				else{
					if(row.type == 'number'){
						input.addClass("scNumberInput");
					}
				}
			}
		}
	};
	
	_common.updateValue = function(formId, row, config){
		if(row.name != null && row.bind != null){
			var input = $('#' + formId).jqxForm('getComponentByName', row.name);
	        
	        if(config[row.bind] == null)
			{
				if(row.type == 'text'){
					config[row.bind] = '';
				}
				else if(row.type == 'number'){
					config[row.bind] = 0;
				}
				else if(row.type == 'date'){
					config[row.bind] = new Date();
				}
			}
					
			if(input != null && config[row.bind] != null){
				if(row.type == 'date'){
					input.val(config[row.bind]);
				}
				else{					
					input.val(config[row.bind]);
				}
			}
		}
	};
	
	_common.loopInput = function(template, formId, handler, config){
		if(template != null){
			for(var sKey in template){
				var row = template[sKey];
				
				if(row.bind != null){
					if(handler != null){
						handler(formId, row, config);
					}
				}
				else if(row.columns != null && row.columns.length > 0){
					_common.loopInput(row.columns, formId, handler, config)
	        	}
			}
	   	}
	};
	
	init();
	
	return _common;
}
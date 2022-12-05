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
						
						if(row.icon == 'percentage'){
							input.addClass("scPercentageInput");
						}
					}else if(row.type == 'option'){
						input.jqxDropDownList({disabled: true, theme: theme});
					}
				}
				else{
					if(row.type == 'number'){
						input.addClass("scNumberInput");
						
						console.log(row)
						
						if(row.icon == 'percentage'){
							input.addClass("scPercentageInput");
						}
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
					
					if(row.format){
						_common.formatter(input, row, config[row.bind]);
					}
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
	
	_common.getRow = function(rowData){
		var rowHTML = '<tr>\n';
		
		if(rowData != null){
			for(var sKey in rowData){
				var row = rowData[sKey];				
				
				if(row != null && row.label != null){
					rowHTML += '<td align="left">' + (row.required ? '<sup style="color: red;">*</sup>' : '') + row.label + '</td>\n'
						+ '<td style="display: flex;" align="left">:&nbsp;&nbsp;';
						
					if(row.type == 'option'){
						rowHTML += '<div id="' + row.name + '"></div>';
					}else if(row.type == 'ratio'){						
						for(var src in row.source){
							var source = row.source[src];
							
							rowHTML += '<div id="' + source.name + '">' + source.label + '</div>';
						}
					}
					else{
						rowHTML += '<input id="' + row.name + '" /></td>\n';
					}						
				}
			}
		}
		
		return (rowHTML + '</tr>\n');
	}
	
	_common.renderPopup = function(template, dvParent){
		var sHTML = '';
		
		if(template != null){
			var rowId = 0;
			
			sHTML = '<div>' + template.title + '</div>\n'
				+ '<div class="scPopup">\n'
				+ '<table style="width: 100%; padding: 0px 15px;">\n';
				
			if(template.showAvatar){
				sHTML += '<tr>\n'
                    + '<td colspan="4" align="center"><img id="' + template.avatatId + '" class="scAvatar"/></td>\n'
                + '</tr>\n';
			}
			
			var tr = [];
			template.rows = [];
			
			for(var sKey in template.column){
				let column = template.column[sKey];
				
				if(rowId == template.colSpan){
					rowId = 0;
					template.rows.push(_common.clone(tr));
					tr.length = 0;
				}
				
				tr.push(column);
				rowId++;
			}
			
			if(tr.length > 0){
				template.rows.push(_common.clone(tr));
				tr.length = 0;
			}
			
			for(var sKey in template.rows){
				let row = template.rows[sKey];
				
				sHTML += _common.getRow(row);
			}
			
			if(template.buttons != null){
				sHTML += '<tr style="height: 50px;">\n'
                    + '<td colspan="4" style="padding-top: 20px; padding-bottom: 20px;" align="center">\n';
	                    	
				for(var sKey in template.buttons){
					let button = template.buttons[sKey];
					
					sHTML += '<input style="margin-right: 5px; width: 100px;" type="button" id="' + button.name + '" value="' + button.label + '" />\n';
				}
			
				sHTML +=  '</td>\n</tr>\n';
			}
			
			sHTML += '</table>\n</div>';
		}
		
		$("#" + dvParent).append(sHTML);		
	};
	
	_common.clone = function(json){
		return JSON.parse(JSON.stringify(json));
	};
	
	_common.renderInputs = function(dataModel, popupConfig){
		for(var sKey in popupConfig){
			var row = popupConfig[sKey];
			
			if(dataModel == null || dataModel == {}){
				if(row.type == 'input'){
					$("#" + row.name).jqxInput({ theme: theme, disabled: row.disabled });
				}else if(row.type == 'date'){
					$("#" + row.name).jqxDateTimeInput({theme: theme});
				}else if(row.type == 'option') {
					$("#" + row.name).jqxDropDownList({theme: theme, source: row.source});
				}
			}
			else{
				if(dataModel[row.bind] == null){
					if(row.type == 'option') {
						$("#" + row.name).jqxDropDownList('clearSelection'); 
					} else {
						$("#" + row.name).val('');
					}
				}
				else{
					$("#" + row.name).val(dataModel[row.bind]);
				}
			}
		}
	}
	
	_common.getModel = function(popupConfig){
		var config = {dataModel: {}, valid: true};
		
		for(var sKey in popupConfig){
			var row = popupConfig[sKey];
			
			config.dataModel[row.bind] = $("#" + row.name).val();
			
			if(row.required && (config.dataModel[row.bind] == null || config.dataModel[row.bind] == '')){
				config.valid = false;
			}
		}
		
		return config;
	}
	
	_common.formatter = function(input, row, val){		
		if(val != null && val != ''){
			var r = /\d{4}/g;			
			var results = val.match(r);
			
			if(results != null){
				var final_cc_str = results.join(row.format.formatStr);

				input.val(final_cc_str);
			}
		}
	}
	
	_common.dateFormat = function(val, formatText){		
		if(val != null && val != ''){
			return $.jqx.dataFormat.formatdate(val, formatText);
		}
		
		return '';
	}
	
	init();
	
	return _common;
}
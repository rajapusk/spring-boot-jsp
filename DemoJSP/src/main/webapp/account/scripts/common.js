var CommonMethod = function(theme){
	var notifyConfig = {};
	var _common = {
		HOST: 'http://localhost:' + 9092
	};
	
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
		var tempDate = fromDate;
		
		if(toDate.getTime() < fromDate.getTime()){
			fromDate = toDate;
			toDate = tempDate;
		}
		
	    var month_diff = toDate.getTime() - fromDate.getTime();
	    var age_dt = new Date(month_diff); 
	    var year = age_dt.getUTCFullYear();
	    var month = age_dt.getUTCMonth();    
	    var days = age_dt.getUTCDay();
	    var age = Math.abs(year - 1970);  
	    var totalDays = (month_diff / (1000 * 3600 * 24));
	    
	    return {year: age, month: month, days: days, totalDays: Math.floor(totalDays)};
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
						applyIcon(row, input);
					}else if(row.type == 'option'){
						input.jqxDropDownList({disabled: true, theme: theme});
					}
				}
				else{
					applyIcon(row, input);
				}
				
				if(row.info != null){
					input.append($('<div id="' + row.name + '_info" class="scInfo"></div>'));
				}
			}
		}
	};
	
	_common.setInfo = function(config){
		var duration = (config.duration == null ? 5 : config.duration) * 1000;
		var info = $('#' + config.prob + '_info');
		
		$(config.event.currentTarget).find('input').css('color', 'red');
		info.html(config.msg);
		info.css('display', 'block');
		
		_common.infoHandler = setTimeout(function(){
			info.css('display', 'none');
		}, duration);
	}
	
	_common.closeInfo = function(config){
		$('#' + config.prob + '_info').css('display', 'none');
		$(config.event.currentTarget).find('input').css('color', 'black');
			
		if(_common.infoHandler){
			clearTimeout(_common.infoHandler);
			_common.infoHandler = null;
		}
	}
	
	var applyIcon = function(row, input){
		if(row.type == 'number'){
			input.addClass("scIconInput");
			
			if(row.icon != null){
				input.addClass("fa " + row.icon);
			} else {
				input.addClass("fa fa-inr");
			}
		}
	}
	
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
					$("#" + row.name).jqxInput({ theme: theme, disabled: (row.disabled != null ? row.disabled : false) });
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
	
	_common.updateModel = function(dataModel, nominee){		
		if(dataModel != null && nominee != null){
			for(var sKey in dataModel){
				var value = dataModel[sKey];
				
				if(value != null){
					nominee[sKey] = value;
				}
			}
		}
	}
	
	_common.snoCell = function(){
		var cellRender = function (row, column, value) {
	        return "<div class='scCenterXY'>" + (value + 1) + "</div>";
	    }
	    		
		return {
			text: '#', sortable: false, filterable: false, editable: false, groupable: false, draggable: false, resizable: false, cellsalign: 'center',
			datafield: '', columntype: 'number', width: 50, cellsrenderer: cellRender
		}
	}
		
	_common.iconCell = function(config){
		if(config.width == null){
			config.width = 40;
		}
		
		if(config.text == null){
			config.text = '';
		}
		
		if(config.datafield == null){
			config.datafield = '';
		}
		
		var cellRender = function (row, column, value) {
			if(config.icon != null){
				return '<div class="scCenterXY scIcon"><i class="fa ' + config.icon + '" aria-hidden="true"></i></div>';
			}
	        
	        return '<div class="scCenterXY scIcon"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i></div>';
	    }
			
		return { text: config.text, datafield: config.datafield, width: config.width, editable: false, cellsrenderer: cellRender};
	}
		
	_common.getDummyData = function(source, gridId){
		var result = [];
		
		var formData = function(row, datafields, appendText){
			for(let sKey in datafields)
			{
				let field = datafields[sKey];
				
				if(field.type == 'string'){
					row[field.name] = 'Testing ' + appendText;
				}
				else if(field.type == 'number'){
					row[field.name] = 10;
				}
			}
		}
		
		for(var i=0; i<50; i++){
			var row = {id: i, widthdraw: [], contribution: []};
			formData(row, source.datafields, i);
			result[i] = row;
		}
		
		source.localdata = result;
		$("#" + gridId).jqxGrid('updatebounddata', 'cells');
	}
	
	var iconHeader = function(defaultText, icon, align){
		var iconHTML = '<i class="fa ' + icon + ' scIconColor" aria-hidden="true"></i>';
		var textHTML = '<span style="padding: 0px 5px;">' + defaultText + '</span>';
		var sHTML = '<div style="height: 100%; display: flex; align-items: center; margin: 0px 5px;">' + (align == 'left' ? (iconHTML + textHTML) : (textHTML + iconHTML)) + '</div>';
		
		return sHTML;
	}
	
	_common.numberIconHeader = function(defaultText, alignment, height){		
		return iconHeader(defaultText, 'fa-inr', 'left');
	};
	
	_common.percentageIconHeader = function(defaultText, alignment, height){		
		return iconHeader(defaultText, 'fa-percent', 'right');
	};
	
	_common.uploadFiles = function(config){	
		if(config != null){
			if(config.fileInput != null && config.fileInput.length > 0 && config.fileIndex < config.fileInput.length){
				var file = config.fileInput[config.fileIndex].files;
				
				if(file != null && file.length > 0){
					var fileData = new FormData();
					fileData.append("pf_loan_doc", file[0]);
					fileData.append("pfLoanId", config.id);
					fileData.append("empCode", config.empCode);
				
					$.ajax({
						type: 'post',
						url: config.url, 
						data: fileData,
						contentType: false,
						cache: false,
						processData:false,
						success: function(result){
							config.fileIndex++;
							
							_common.uploadFiles(config);
						}
					});
				} else {
					config.fileIndex++;
							
					_common.uploadFiles(config);
				}
			}else{
				if(config.successHandler != null){
					config.successHandler();
				}
			}
		}
	};
	
	_common.callPOST = function(postData, sURL, handler){
		$.ajax({
			type: 'post',
			url: sURL, 
			data: JSON.stringify(postData),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				handler(result);
			}
		});
	}
	
	_common.callGET = function(sURL, handler){
		$.ajax({
			url: sURL, 
			success: function(result){
				handler(result);
			}
		});
	}
	
	init();
	
	return _common;
}
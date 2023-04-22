import { jqx }  from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxgrid';
import HttpAJAX from './httpAJAX.js';

function CommonService() { 
    var theme = '';
	this.collection = {};		
	this.FORMAT = {
		DATE: 'yyyy-MM-dd',
		DATETIME: 'yyyy-MM-ddTHH:mm:ss',
		TIME: 'HH:mm:ss',
		DISP_DATE: 'dd/MM/yyyy',
		DISP_DATETIME: 'dd/MM/yyyy hh:mm tt',
		DISP_TIME: 'hh:mm tt',
	};
	this.WATCH = {
		HTTP_CALL: 'HTTP_CALL',
        THEME: 'THEME',
        NOTIFICATION: 'NOTIFICATION',
	};

	this.TODAY = null;
	this.YESTERDAY = null;
	this.TOMORROW = null;
	
	this.init = function(){
		this.TODAY = new Date();
		this.YESTERDAY = new Date();
		this.TOMORROW = new Date();
		this.YESTERDAY.setDate(this.TODAY.getDate() - 1);
		this.TOMORROW.setDate(this.TODAY.getDate() + 1);
	}

    this.getWatchValue = function(keyfield) {
        return (this.collection[keyfield] != null ? this.collection[keyfield].value : null);
    };
	
	this.getObj = function(keyfield, newState)
	{
		if(this.collection[keyfield] == null){
            this.collection[keyfield] = {value: newState, subscribers: []};
        }
		
		return this.collection[keyfield];
	};

    this.setValue = function (keyfield, newState) { 
		var find = this.getObj(keyfield, newState);
		
        if(keyfield == this.WATCH.THEME){
            theme = newState;
        }

        if (find == null || find.value === newState) {
            return;
        }

		if(find != null)
		{
            find.value = newState;

			this.runAll(find);            
		}
    };

    this.runAll = function(find){
        if(find != null && find.value != null){
            find.subscribers.forEach(subscriber => {
				subscriber(find.value);
			}); 
        }
    }

    this.subscribe = function (keyfield, itemToSubscribe) {
		var find = this.getObj(keyfield, null);
		
        if (find == null || find.subscribers.indexOf(itemToSubscribe) > -1) {
            return;
        }
		
        find.subscribers.push(itemToSubscribe);
        this.runAll(find);
    };

    this.unsubscribe = function (keyfield, itemToUnsubscribe) {
		var find = this.collection[keyfield];
		
		if(find == null)
			return;
			
        find.subscribers = find.subscribers.filter(
            subscriber => subscriber !== itemToUnsubscribe
        );
    };

    this.loopInput = function(template, form, handler, config){
		if(template != null){
			var nextRows = [];
			let changeModel = [];

			loopFindNextElement(template, nextRows);
			
			for(var sKey in nextRows){
				var row = nextRows[sKey];
				
				if(row.nextRow == null){
					if(row.disabled != true && (row.type == 'text' || row.type == 'textArea' || row.type == 'number' || (row.type == 'blank' && row.name != null && row.name.indexOf('search') > -1))){
						findNextElement(nextRows, row, (sKey * 1), form);
					}
				}
			}
						
			loopInputHandler(template, form, handler, config, changeModel);
			fireChange(changeModel);
	   	}
	};

	this.fieldUpdate = function(template, form, config, fieldId){
		let newObj = Object.keys(config);

		if(template != null && newObj.length > 0){
			let changeModel = [];
			var row = this.getFormRow(template, fieldId);

			this.updateValue(form, row, config, changeModel);
			fireChange(changeModel);
	   	}
	};

	this.dateDiff = function(fromDate, toDate){
		var tempDate = fromDate;
		
		if(toDate.getTime() < fromDate.getTime()){
			fromDate = toDate;
			toDate = tempDate;
		}
		
	    var month_diff = (toDate.getTime() - fromDate.getTime());
	    var age_dt = new Date(month_diff); 
	    var year = age_dt.getUTCFullYear();
	    var month = age_dt.getUTCMonth();    
	    var days = age_dt.getUTCDay();
	    var age = Math.abs(year - 1970);  
	    var totalDays = Math.floor(month_diff / (1000 * 3600 * 24));
	    
	    return {year: age, month: month, days: (totalDays <= 31 ? totalDays : days), totalDays: totalDays};
	}

    this.updateDisable = function(form, row, config){
		if(row.name != null && form != null){
			var input = form.getComponentByName(row.name);
	        
			if(input != null){
                if(row.type === 'button'){
                    input.jqxButton({theme: theme});
                }

				Common.inputValidation(input, row);

				if(row.type === 'option'){
					input.jqxDropDownList({width: row.width});
				} 

				if(row.type === 'date' || row.type == 'datetime' || row.type == 'time'){
					input.jqxDateTimeInput({formatString: row.dispFormat, min: row.minDate, max: row.maxDate});

					if(row.bindEvent != true){
						row.bindEvent = true;

						input.change((event)=>{
							row.value = event.args.date;
	
							if(row.change != null){
								row.change(row.value);
							}
						})
					};					
				}

				if(row.disabled === true){
					if(row.type === 'text'){
						input.jqxInput({disabled: true,  theme: theme});						
					}
					else if(row.type === 'date' || row.type == 'datetime' || row.type == 'time'){
						input.jqxDateTimeInput({disabled: true, theme: theme});
					}else if(row.type === 'number'){
						input.jqxNumberInput({disabled: true, theme: theme});
						applyIcon(row, input);
					}else if(row.type === 'option'){
						input.jqxDropDownList({disabled: true, theme: theme});
					} 
				}
				else{
					applyIcon(row, input);
				}
				
				if(row.info != null){
					//input.append($('<div id="' + row.name + '_info" class="scInfo"></div>'));
				}
			}
		}
	};

	this.viewMode = function(form, row, config){
		if(row.name != null && form != null){
			var input = form.getComponentByName(row.name);
	        
			if(input != null){
				let disabled = (config.viewMode || row.disabled);

                if(row.type == 'text'){
					input.jqxInput({disabled: disabled});						
				}
				else if(row.type == 'date' || row.type == 'datetime' || row.type == 'time'){
					input.jqxDateTimeInput({disabled: disabled});
				}else if(row.type == 'number'){
					input.jqxNumberInput({disabled: disabled});
					applyIcon(row, input);
				}else if(row.type == 'option'){
					input.jqxDropDownList({disabled: disabled});
				}
			}
		}
	};

	this.inputValidation = function(input, row){
		if(input != null && row != null){
			if(row.numberOnly){
				var reg = /^\d+$/;
				var handler = (event) =>{
					let value = ("" + (input.val != null ? input.val() : input.value) + event.key);
		
					if(!reg.test(event.key) || (row.maxLength != null && value != null && value.length > row.maxLength)){
						event.stopPropagation()
						event.preventDefault()
					}
				}

				if(input.keypress != null){
					input.keypress(handler);
				} else {
					input.onkeypress = handler
				}
			}
		}
	}

    this.updateValue = function(form, row, config, changeModel){
		if(row.name != null && row.bind != null){
			var input =form.getComponentByName(row.name);
	        
	        if(config[row.bind] == null)
			{
				if(row.type == 'text'){
					config[row.bind] = '';
				}
				else if(row.type == 'number'){
					config[row.bind] = 0;
				}
				else if(row.type == 'date' || row.type == 'datetime' || row.type == 'time'){
					config[row.bind] = new Date();
					row.value = config[row.bind];
				}
			}

			if(config[row.bind + 'Extra'] != null){
				row.valueExtra = config[row.bind + 'Extra'];
			}
					
			if(input != null && config[row.bind] != null){
				if(row.type == 'date' || row.type == 'datetime' || row.type == 'time'){
					var time = config[row.bind];
					var timeVal = null;

					if(typeof time == 'string'){
						if(time.includes(':')){
							timeVal = Common.stringToDate(time);
						} else{
							timeVal = new Date(config[row.bind]);
						}
					} else {
						timeVal = time;	
					}

					if(timeVal != null){
						input.val(timeVal);
						row.inputValue = timeVal;

						if(row.change != null){
							changeModel.push({row: row, value: timeVal});
						}
					}
				}
				else{					
					input.val(config[row.bind]);
					row.inputValue = config[row.bind];

					if(row.change != null){
						changeModel.push({row: row, value: config[row.bind]});					
					}

					/*if(row.format){
						formatter(input, row, config[row.bind]);
					}*/
				}
			}
		}
	};

	this.stringToDate = function(time){
		let timeVal = null;

		if(time!= null && typeof time == 'string' && (time.includes(':') || time.includes('-'))){
			if(time.includes('-')){
				var dateTime = time.split('-');

				if(dateTime.length > 2){
					let splitDate = dateTime[2].split('T');

					if(splitDate.length > 1){
						let splitTime = splitDate[1].split(':');

						timeVal = new Date(dateTime[0], dateTime[1], splitDate[0], splitTime[0], (splitTime.length > 1 ? splitTime[1] : 0), (splitTime.length > 2 ? splitTime[2] : 0));
					} else {
						timeVal = new Date(dateTime[0], dateTime[1], splitDate[0]);
					}				
				}
			} else {
				let splitTime = time.split(':');
				var today = new Date();

				if(splitTime.length > 0){
					timeVal = new Date(today.getFullYear(), today.getMonth(), today.getDay(), splitTime[0], (splitTime.length > 1 ? splitTime[1] : 0), (splitTime.length > 2 ? splitTime[2] : 0));
				}
			}
		}

		return timeVal;
	}

	this.getValue = function(form, row, config){
		if(row.name != null && row.bind != null && config != null){
			var input = form.getComponentByName(row.name);	    
					
			if(input != null){
				config[row.bind] = input.val();
			}

			if(config[row.bind] == null)
			{
				if(row.type == 'text'){
					config[row.bind] = '';
				}
				else if(row.type == 'number'){
					config[row.bind] = 0;
				} else if(row.type == 'option'){
					config[row.bind] = null;
				} 
			}

			if(row.type == 'date' || row.type == 'datetime' || row.type == 'time'){
				var formatText = (row.format != null ? row.format : Common.FORMAT.DATETIME);

				if(config.dateModel == null){
					config.dateModel = {key: []};
				}
								
				config.dateModel.key.push(row.bind);
				config.dateModel[row.bind] = {};
				config.dateModel[row.bind]['format'] = dateFormat(row.value, formatText);
				config.dateModel[row.bind]['date']  = row.value;
				config.dateModel[row.bind]['value'] = config[row.bind];
			} else if(row.value != null){
				config[row.bind] = row.value;
			}

			if(row.valueExtra != null){
				config[row.bind + 'Extra'] = row.valueExtra;
			}
		}
	};

	this.ageCalculator = function(value){
		let result = {ageText: '', diff: null};

		if(value != null){
			result.diff = Common.dateDiff(value, new Date());

			if(result.diff != null){
			  	let ageText = '';
		
			  	if(result.diff.year > 0){
					ageText = result.diff.year + ' year(s) ';
			  	}
		
			  	if(result.diff.month > 0){
					if(ageText.length == 0){
				  		ageText = result.diff.month + ' month(s) baby';
					} else {
				  		ageText += result.diff.month + ' month(s)';
					}
				}
		
				if(ageText.length == 0){
					ageText = result.diff.days + ' day(s) baby';
				}
		
				result.ageText = ageText;
			}
		}

		return result;
	}

	this.getColTemplate = function(config){
		var template = [];

		if(config != null){
			let count = 0;
			let columns = [];
			
			config.rows.forEach((elm) => {
				let col = {
					bind: elm.bind,
					name: elm.bind,
					type: (elm.type != null ? elm.type : 'text'),
					label: elm.label,
					disabled: (elm.disabled != null ? elm.disabled : false),
					labelPosition: 'top',
					dispFormat: elm.dispFormat,
					format: elm.format,
					change: elm.change,
					maxLength: elm.maxLength,
					minLength: elm.minLength,
					minDate: elm.minDate,
					maxDate: elm.maxDate,
					numberOnly: elm.numberOnly,
					required: elm.required,
					init: elm.init,
					labelWidth: config.labelWidth + 'px',
					width: config.controlWidth + 'px',
					align: 'left',
					columnWidth: (100 / config.noOfCols) + '%'
				}

				if(elm.type === 'option'){
					if(elm.component != null){
						col.component = elm.component;
					} else if(elm.optionsLayout != null && config.noOfCols == 1){
						col.optionsLayout = elm.optionsLayout;
						col.labelWidth = undefined;
						col.columnWidth = "150px";
						col.labelPosition = 'right';
					}
					
					col.options = elm.options;
				} else if(elm.template != null){
					col.template = elm.template;
				}

				count++;

				if(config.noOfCols == 1){
					template.push(col);
					count = 0;
				} else {
					columns.push(col);
	
					if(count === config.noOfCols){
						let cols = Array.from(columns);
						template.push({columns: cols});
						columns.length = 0;
						count = 0;
					}
				}
			});

			if(columns.length > 0){
				let cols = Array.from(columns);

				template.push({columns: cols});
			}
		}

		return template;
	}

	this.defaultInit = function(event, row){
		let disabled = (row.disabled != null ? row.disabled : false);

		if(row.type == 'text'){
			event.jqxInput({disabled: disabled, theme: theme});						
		}
		else if(row.type == 'date' || row.type == 'datetime' || row.type == 'time'){
			event.jqxDateTimeInput({disabled: disabled, theme: theme});
		}else if(row.type == 'number'){
			event.jqxNumberInput({disabled: disabled, theme: theme});
		} else if(row.type === 'option'){
			event.jqxDropDownList({disabled: disabled, theme: theme});
		}
	}

	this.clone = function(json_data){
		let result = Array.from(json_data);

		if(result.length > 0){
			return result;
		} else {
			return JSON.parse(JSON.stringify(json_data));
		}
	}

	this.mapValue = function(from, to, fromTo){
		if(to != null && from != null){
			if(fromTo){
				for(let sKey in to){
					let item = to[sKey];

					if(typeof item == 'object'){
						this.mapValue(from[sKey], item, fromTo);
					} else {
						if(from[sKey] != null){
							to[sKey] = from[sKey];
						}
					}
				}
			} else {
				for(let sKey in from){
					let item = from[sKey];

					if(typeof item == 'object'){
						if(to[sKey] == null){
							to[sKey] = {};
						}

						this.mapValue(item, to[sKey], fromTo);
					} else {
						if(from[sKey] != null){
							to[sKey] = from[sKey];
						}
					}
				}
			}
		}
	}

	this.getFormRow = function(template, fieldId){
		var foundRow = null;

		if(template != null){
			for(var sKey in template){
				var row = template[sKey];
				
				if(foundRow == null){
					if(row.bind != null){
						if(row.bind == fieldId){
							foundRow = row;
						}
					}
					else if(row.columns != null && row.columns.length > 0){
						foundRow = this.getFormRow(row.columns, fieldId);
					}
				}
			}
	   	}

		return foundRow;
	};

	this.uploadFiles = function(config){	
		if(config != null){
			if(config.fileInput != null && config.fileInput.length > 0 && config.fileIndex < config.fileInput.length){
				var file = config.fileInput[config.fileIndex].files;
				
				if(file != null && file.length > 0){
					var fileData = new FormData();
					fileData.append("document", file[0]);
					fileData.append("pageId", config.id);
					fileData.append("empCode", config.empCode);
				
					HttpAJAX.UPLOAD(config.url, fileData, (result) =>{
						config.fileIndex++;
							
						this.uploadFiles(config);
					});
				} else {
					config.fileIndex++;
							
					this.uploadFiles(config);
				}
			}else{
				if(config.successHandler != null){
					config.successHandler();
				}
			}
		}
	};

	this.init();

	var fireChange = function(changeModel){
		if(changeModel != null && changeModel.length > 0){	
			setTimeout(function(){
				changeModel.forEach((element) => {
					if(element.row != null){
						if(element.row.change != null){
							element.row.change(element.value);
						}
					}
				})
			}, 0);
		}
	}

	var dateFormat = function(val, formatText){		
		if(val != null && val != ''){
			return jqx.dataFormat.formatdate(val, formatText);
		}
		
		return '';
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

    var formatter = function(input, row, val){
		if(val != null && val != ''){
			var r = /\d{4}/g;			
			var results = val.match(r);
			
			if(results != null){
				var final_cc_str = results.join(row.format.formatStr);

				input.val(final_cc_str);
			}
		}
	}

	var loopFindNextElement = function(template, nextRows){
		if(template != null){
			for(var sKey in template){
				var row = template[sKey];
				
				if(row.columns != null && row.columns.length > 0){
					loopFindNextElement(row.columns, nextRows);
	        	} else {
					nextRows.push(row);
				}
			}
	   	}
	};
	
	var loopInputHandler = function(template, formId, handler, config, changeModel){
		if(template != null){
			for(var sKey in template){
				var row = template[sKey];
				
				if(row.bind != null){
					if(handler != null){
						handler(formId, row, config, changeModel);
					}
				}
				else if(row.columns != null && row.columns.length > 0){
					loopInputHandler(row.columns, formId, handler, config, changeModel);
	        	}
			}
	   	}
	};

    var findNextElement = function(template, currentRow, currentIdx, formId){
		if(template != null && currentRow != null){
			for(var idx = (currentIdx + 1); idx < template.length; idx++){
				var row = template[idx];
				
				if(row.disabled != true && (row.type == 'text' || row.type == 'textArea' ||row.type == 'number' || (row.type == 'blank' && row.name != null && row.name.indexOf('search') > -1))){
					currentRow.nextRow = row;
					bindKeypressEvent(currentRow, formId);
					
					break;
				}
			}
		}
	}

    var bindKeypressEvent = function(row, form){
		if(row != null && form != null){
			var input = form.getComponentByName(row.name);
			/*var rootEvent = $(input[0].querySelector('input'));
				
			rootEvent.keypress(function(event) {
				if(event.which == 13 && row.nextRow != null){
					var nextInput = $('#' + formId).jqxForm('getComponentByName', row.nextRow.name);
					
					if(nextInput != null && nextInput.length > 0){
						let target = null;
						
						if(row.nextRow.type == 'textArea'){
							target = $(nextInput[0].querySelector('textarea'));
						} else {
							target = $(nextInput[0].querySelector('input'));
						}
						
						if(target != null && target.length > 0){
							target.focus();
							target[0].setSelectionRange(0,0);
						}
					}
				}
			});*/
		}
	}
};

const Common = new CommonService();
export default Common;
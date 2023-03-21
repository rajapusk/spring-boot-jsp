import { jqx }  from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxgrid';

function CommonService() { 
    var theme = '';
	this.collection = {};	
	this.HOST = 'http://localhost:' + 9092;
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
        THEME: 'THEME'
	};
	
    this.getValue = function(keyfield) {
        return (this.collection[keyfield] != null ? this.collection[keyfield].value : null);
    };
	
	this.getObj = function(keyfield, newState)
	{
		var find = this.collection[keyfield];
		
		if(find == null){
            this.collection[keyfield] = {value: newState, subscribers: []};
        }
		
		return find;
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
			
			loopFindNextElement(template, nextRows);
			
			for(var sKey in nextRows){
				var row = nextRows[sKey];
				
				if(row.nextRow == null){
					if(row.disabled != true && (row.type == 'text' || row.type == 'textArea' || row.type == 'number' || (row.type == 'blank' && row.name != null && row.name.indexOf('search') > -1))){
						findNextElement(nextRows, row, (sKey * 1), form);
					}
				}
			}
			
			loopInputHandler(template, form, handler, config);
	   	}
	};

    this.updateDisable = function(form, row, config){
		if(row.name != null && form != null){
			var input = form.getComponentByName(row.name);
	        
			if(input != null){
                if(row.type === 'button'){
                    input.jqxButton({theme: theme});
                }

				if(row.type === 'option'){
					input.jqxDropDownList({width: row.width});
				} 

				if(row.type === 'date' || row.type == 'datetime' || row.type == 'time'){
					input.jqxDateTimeInput({formatString: row.dispFormat});

					input.change((event)=>{
						row.value = event.args.date;
					})
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

    this.updateValue = function(form, row, config){
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
				}
			}
					
			if(input != null && config[row.bind] != null){
				if(row.type == 'date' || row.type == 'datetime' || row.type == 'time'){
					input.val(config[row.bind]);
				}
				else{					
					input.val(config[row.bind]);
					
					if(row.format){
						formatter(input, row, config[row.bind]);
					}
				}
			}
		}
	};

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
			}
		}
	};

	this.getIcon = function(icon){
		let iconHTML = '<div class="scCenterXY scFullHeight">';

		if(icon === 'pen'){
			iconHTML += penIcon();
		} else if(icon === 'eye'){
			iconHTML += eyeIcon();
		} else if(icon === 'remove'){
			iconHTML += deleteIcon();
		} else {
			iconHTML += bufferIcon();
		}

		iconHTML += '</div>';

		return iconHTML;
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

	var bindDateModel = function(model){
		if(model){
			for(var sKey in model){
				let item = model[sKey];
				
				if(sKey == 'dateModel'){
					model.dateModel.key.forEach((element) => {
						model[element] = model.dateModel[element]['format'];
					})

					delete model.dateModel;
				} else if(item.dateModel != null){
					item.dateModel.key.forEach((element) => {
						item[element] = item.dateModel[element]['format'];
					})

					delete item.dateModel;
				} else if(typeof item == "object"){
					bindDateModel(item);
				}
			}
		}
	}

	this.POST = function(url, postData, successHandler, errorHandler)
	{
		try{
			bindDateModel(postData);

			let config = {
				"method": "POST",
				"body": JSON.stringify(postData),
				"headers": { 
					crossDomain: true,
					'Content-Type': "application/json"
				}
			};

			this.setValue(this.WATCH.HTTP_CALL, true);
			fetch(this.HOST + url, config)
			.then(res => res.json())
			.then(
				(result) => {
					if(successHandler != null){
						successHandler(result);
					}
				},
				(error) => {
					if(errorHandler != null){
						errorHandler(error);
					}
				}
			)
		}
		catch(e){
			console.error(e)
			this.setValue(this.WATCH.HTTP_CALL, false);
		}
    };

	this.GET = function(url, successHandler, errorHandler)
	{
		let config = {
			"method": "GET",
			"headers": { 
				crossDomain: true,
				'Content-Type': "application/json"
			}
		};

		fetch(this.HOST + url, config)
		.then(res => res.json())
		.then(
			(result) => {
				if(successHandler != null){
					successHandler(result);
				}
			},
			(error) => {
				if(errorHandler != null){
					errorHandler(error);
				}
			}
		)
    };

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
	
	var loopInputHandler = function(template, formId, handler, config){
		if(template != null){		
			for(var sKey in template){
				var row = template[sKey];
				
				if(row.bind != null){
					if(handler != null){
						handler(formId, row, config);
					}
				}
				else if(row.columns != null && row.columns.length > 0){
					loopInputHandler(row.columns, formId, handler, config);
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

	var eyeIcon = function(){
		return '<svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg">'
				+'<path d="M288 144a110.94 110.94 0 0 0-31.24 5 55.4 55.4 0 0 1 7.24 27 56 56 0 0 1-56 56 55.4 55.4 0 0 1-27-7.24A111.71 111.71 0 1 0 288 144zm284.52 97.4C518.29 135.59 410.93 64 288 64S57.68 135.64 3.48 241.41a32.35 32.35 0 0 0 0 29.19C57.71 376.41 165.07 448 288 448s230.32-71.64 284.52-177.41a32.35 32.35 0 0 0 0-29.19zM288 400c-98.65 0-189.09-55-237.93-144C98.91 167 189.34 112 288 112s189.09 55 237.93 144C477.1 345 386.66 400 288 400z"></path>'
			+'</svg>';
	}

	var penIcon = function(){
		return '<svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg">'
				+'<path d="M290.74 93.24l128.02 128.02-277.99 277.99-114.14 12.6C11.35 513.54-1.56 500.62.14 485.34l12.7-114.22 277.9-277.88zm207.2-19.06l-60.11-60.11c-18.75-18.75-49.16-18.75-67.91 0l-56.55 56.55 128.02 128.02 56.55-56.55c18.75-18.76 18.75-49.16 0-67.91z"></path>'
			+'</svg>';
	}

	var deleteIcon = function(){
		return '<svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg">'
				+'<path d="M32 464a48 48 0 0 0 48 48h288a48 48 0 0 0 48-48V128H32zm272-256a16 16 0 0 1 32 0v224a16 16 0 0 1-32 0zm-96 0a16 16 0 0 1 32 0v224a16 16 0 0 1-32 0zm-96 0a16 16 0 0 1 32 0v224a16 16 0 0 1-32 0zM432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16z"></path>'
			+'</svg>';
	}

	var bufferIcon = function(){
		return '<svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg">'
				+'<path d="M427.84 380.67l-196.5 97.82a18.6 18.6 0 0 1-14.67 0L20.16 380.67c-4-2-4-5.28 0-7.29L67.22 350a18.65 18.65 0 0 1 14.69 0l134.76 67a18.51 18.51 0 0 0 14.67 0l134.76-67a18.62 18.62 0 0 1 14.68 0l47.06 23.43c4.05 1.96 4.05 5.24 0 7.24zm0-136.53l-47.06-23.43a18.62 18.62 0 0 0-14.68 0l-134.76 67.08a18.68 18.68 0 0 1-14.67 0L81.91 220.71a18.65 18.65 0 0 0-14.69 0l-47.06 23.43c-4 2-4 5.29 0 7.31l196.51 97.8a18.6 18.6 0 0 0 14.67 0l196.5-97.8c4.05-2.02 4.05-5.3 0-7.31zM20.16 130.42l196.5 90.29a20.08 20.08 0 0 0 14.67 0l196.51-90.29c4-1.86 4-4.89 0-6.74L231.33 33.4a19.88 19.88 0 0 0-14.67 0l-196.5 90.28c-4.05 1.85-4.05 4.88 0 6.74z"></path>'
			+'</svg>';
	}
};

const Common = new CommonService();
export default Common;
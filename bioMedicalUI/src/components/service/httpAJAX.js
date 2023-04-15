import Common from '../service/common.js';

function HttpAJAXService(){
    var HOST = 'http://localhost:' + 9092;

    this.UPLOAD = function(url, fileData, successHandler, toast)
	{
		try{
			let config = {
				method: "POST",
				body: fileData
			};

			makeAJAX(config, url, successHandler, {message: (toast != null && toast.message ? toast.message : 'The record updated successfully.'), type: "success"});
		}
		catch(e){
			console.error(e)
			Common.setValue(Common.WATCH.HTTP_CALL, false);
		}
    };

	this.POST = function(url, postData, successHandler, toast)
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

			let toastConfig = (toast != null && toast.message ? {message: toast.message, type: "success"} :  null);
			makeAJAX(config, url, successHandler, toastConfig);
		}
		catch(e){
			console.error(e)
			Common.setValue(Common.WATCH.HTTP_CALL, false);
		}
    };

	this.GET = function(url, successHandler)
	{
		try{
			let config = {
				"method": "GET",
				"headers": { 
					crossDomain: true,
					'Content-Type': "application/json"
				}
			};

			makeAJAX(config, url, successHandler);
		}
		catch(e){
			console.error(e)
			Common.setValue(Common.WATCH.HTTP_CALL, false);
		}
    };

	this.DELETE = function(url, successHandler, toast)
	{
		try{
			let config = {
				"method": "DELETE",
				"headers": { 
					crossDomain: true,
					'Content-Type': "application/json"
				}
			};

			makeAJAX(config, url, successHandler, {message: (toast != null && toast.message ? toast.message : 'The record deleted successfully.'), type: "success"});
		}
		catch(e){
			console.error(e)
			Common.setValue(Common.WATCH.HTTP_CALL, false);
		}
    };

	var makeAJAX = function(config, url, successHandler, toast){
		Common.setValue(Common.WATCH.HTTP_CALL, true);
		fetch(HOST + url, config)
		.then(res => res.json())
		.then(
			(result) => {
                Common.setValue(Common.WATCH.HTTP_CALL, false);

				if(toast!= null){
					Common.setValue(Common.WATCH.NOTIFICATION, toast);
				}

				if(successHandler != null){
					successHandler(result);
				}
			},
			(error) => {
                Common.setValue(Common.WATCH.HTTP_CALL, false);
                console.log(error)
			}
		)
	}

    var bindDateModel = function(model){
		if(model){
			for(var sKey in model){
				let item = model[sKey];
				
				if(item != null){
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
	}
}

const HttpAJAX = new HttpAJAXService();
export default HttpAJAX;
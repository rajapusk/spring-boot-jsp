import Common from '../service/common.js';

function HttpAJAXService(){
    var HOST = 'http://localhost:' + 9092;

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

			Common.setValue(Common.WATCH.HTTP_CALL, true);
			fetch(HOST + url, config)
			.then(res => res.json())
			.then(
				(result) => {
                    Common.setValue(Common.WATCH.HTTP_CALL, false);

					if(successHandler != null){
						successHandler(result);
					}
				},
				(error) => {
                    Common.setValue(Common.WATCH.HTTP_CALL, false);

					if(errorHandler != null){
						errorHandler(error);
					}
				}
			)
		}
		catch(e){
			console.error(e)
			Common.setValue(Common.WATCH.HTTP_CALL, false);
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

        Common.setValue(Common.WATCH.HTTP_CALL, true);
		fetch(HOST + url, config)
		.then(res => res.json())
		.then(
			(result) => {
                Common.setValue(Common.WATCH.HTTP_CALL, false);

				if(successHandler != null){
					successHandler(result);
				}
			},
			(error) => {
                Common.setValue(Common.WATCH.HTTP_CALL, false);
                
				if(errorHandler != null){
					errorHandler(error);
				}
			}
		)
    };

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
}

const HttpAJAX = new HttpAJAXService();
export default HttpAJAX;
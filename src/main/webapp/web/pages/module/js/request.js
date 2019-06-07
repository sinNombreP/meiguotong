function requestGet(baseUrl, params, callback) {

	$.ajax(baseUrl, {
		data: params,
		dataType: 'json', //服务器返回json格式数据
		type: 'get', //HTTP请求类型
		timeout: 10000, //超时时间设置为10秒；
		success: function(dataInfo) {
	//		layer.close(index);
			let success = dataInfo.success;
			let message = dataInfo.msg;
			if(success) {
				console.log(JSON.stringify(dataInfo));
				callback(dataInfo);
			} else {
				layerMsg(message);
			}
		},
		error: function(xhr, type, errorThrown) {
	/*		layer.close(index);*/
				layerMsg(type);
		}
	});
}

function requestPost(baseUrl, params, callback) {

	$.ajax(baseUrl, {
		data: params,
		dataType: 'json', //服务器返回json格式数据
		type: 'post', //HTTP请求类型
		timeout: 10000, //超时时间设置为10秒；
		success: function(dataInfo) {
	//		layer.close(index);
			let success = dataInfo.success;
			let message = dataInfo.msg;
			if(success) {
	//			console.log(JSON.stringify(dataInfo));
				callback(dataInfo);
			} else {
				layerMsg(message);
			}
		},
		error: function(xhr, type, errorThrown) {
//			layer.close(index);
				layerMsg(type);
		}
	});
}



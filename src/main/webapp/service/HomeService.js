var app = angular.module('ToDo');
app.factory('homeService', function($http ) {
	var notes = {};
	console.log("asdhasdasd");
	// this is my generic method accepted all method 
	notes.getAllnode=function(url,method,token,data){
		return $http({
			method:method,
			url:url,
			headers:{jwt:token},
			data:data
		})
	}
	return notes;
	
});
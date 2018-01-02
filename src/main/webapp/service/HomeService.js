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
	
	/*==========================this service use for Collaborator ========================*/
	notes.collobratore=function(note,email){
		console.log("home service "+email);
		console.log(" home service "+note.id);
		return $http({
			method:'post',
			url:'',
			header:{jwt:email},
			data:note
		})
	}
	return notes;
});


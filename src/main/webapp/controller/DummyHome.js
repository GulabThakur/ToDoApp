var app = angular.module('ToDo');
app.controller('dummyHomeCrt' , function($state,dummyService){
	console.log("coming in dummy controller");
	var token=dummyService.getToken();
	token.then(function(response){
		console.log("value data"+response.data.message);
		localStorage.setItem('jwt',response.data.message);
		$state.go('homepage');
	},function(response){
		console.log("home error");
	})
});


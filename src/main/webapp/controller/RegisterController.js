var app = angular.module('ToDo');

app.controller('registerController',function($scope,registrationService,$location){
	console.log("inside registration");
	$scope.registerUser= function(){
		var a = registrationService.registerUser($scope.user);
		console.log(a);
			a.then(function(response){
				console.log(response.data.message);
				localStorage.setItem('token',response.data.message);
				
				console.log("register success");
				$location.path();
			},function(response){
				if(response.status==409)
					{
						$scope.error=response.data.message;
					}
				else
					{	
						console.log("fail");
						$scope.error="Enter valid data";
					}
			});
	}
});

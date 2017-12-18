var app = angular.module('ToDo');
app.controller('registerController',function($scope,registrationService,$location){

	$scope.registerUser= function(){
		registrationService.registerUser($scope.user)
				.then(function(response){
				console.log(response.data.message);
				localStorage.setItem('token',response.data.message);
				
				$location.path();
			},function(response){
				if(response.status==409)
					{
						$scope.error=response.data.message;
					}
				else
					{	
						$scope.error="Enter valid data";
					}
			});
	}
});

var app = angular.module('ToDo');
app.controller('registerController',function($scope,registrationService,$location,$state){

	$scope.registerUser= function(){
		var objectUser=$scope.user;
		var password=objectUser.password;
		var conform=objectUser.conform_psd;
		$scope.password="";
		var result= angular.equals(password,conform);
		if(result){
			
			registrationService.registerUser($scope.user)
			.then(function(response){
			console.log(response.data.message);
			localStorage.setItem('token',response.data.message);
			$state.go('login');
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
		else{
			
			$scope.errorMissMatch="Please Enter same password and conform password"
			console.log(" is not match ....." ,result);
		}
	}
});

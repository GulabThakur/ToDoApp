var app=angular.module('ToDo');
app.controller('forgotPasswodCrtl',function ($scope,forgotPsdService,$location)
		{
			$scope.fogotClick=function()
			{
				var a=forgotPsdService.forgotUser($scope.user);
				
				a.then(function(response)
						{
							console.log(response.data.message);
							localStorage.setItem('token',response.data.message);
						}),function(response)
						{
							if(response.status=409)
							{
								$scope.error=response.data.message;
							}
							else{
								console.log("fail");
								$scope.error="Enter the valid data";
							}
						}
				
			}
		});

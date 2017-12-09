var app=angular.module('ToDo');
app.controller('loginController',loginFun);
function loginFun($scope)
{
	$scope="hello";
	console.log($scope);
	
}
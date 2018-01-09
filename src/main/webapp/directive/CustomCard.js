var app=angular.module('ToDo');

app.directive('cardDrective', function(){
	
	return {
		 restrict: 'E',
		 scope: { 
			 data: '='
			 },
		 templateUrl:"template/cardDriective.html",
		 link:function($scope,$element , $attribute){
			 console.log("gggggggggg::",$scope.data);
		 }
	};
});



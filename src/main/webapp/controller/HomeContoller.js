
/**
 * @author ThakurGulab
 * @Decription home controller 
 * 
 */
var app = angular.module('ToDo');
app.controller('homepageCrt', function($scope, homeService, $location,$state,$window) {
	 
	$scope.addNode = function() {
		$scope.allNodes();
		var url="create";
		var method="post";
		var token =localStorage.getItem('jwt');
		var data=$scope.note;
		var nodes=homeService.getAllnode(url,method,token,data);
		nodes.then(function(response){
			console.log("Note created ");
			$scope.allNodes();
		}, function(){
				console.log(response.data.message);
		});

	}
	
	$scope.allNodes=function(){
		console.log("inside all note");
		var url="all";
		var method="post";
		$scope.notes=[];
		var token =localStorage.getItem('jwt');
		var data=null;
		console.log("inside all note")
		var nodes=homeService.getAllnode(url,method,token,data);
		nodes.then(function(response){
			$scope.notes=response.data;
			console.log(response.data);
			console.log("fetch the data sucessfull ");
		}, function(response){
			var rep=response.data.meResponse;
				console.log(rep);
		});
	}
	//here i am call automatically allnodes();
	$scope.allNodes();
	$scope.getById=function(){
		var url="record";
		var method="get";
		var token =localStorage.getItem('jwt');
		//var data=$scope.note;
		var nodes=homeService.getAllnode(url,method,token,data);
		nodes.then(function(response){
			console.log("fetch the data sucessfull by id ");
		}, function(){
				console.log(response.data.message);
		});
	}
	$scope.deletebyId=function($event){
		var url="delete/"+$event;
		var method="delete";
		var token =null;
		var data=null;
		var nodes=homeService.getAllnode(url,method,token,data);
		nodes.then(function(response){
			console.log("delete the data sucessfull by id ");
			$scope.allNodes();
		}, function(){
				console.log(response.data.message);
		});
	}
	$scope.updateById=function($event){
		var url="update/id";
		var method="put";
		var token =localStorage.getItem('jwt');
		var data=$scope.note;
		var nodes=homeService.getAllnode(url,method,token,data);
		nodes.then(function(response){
			console.log("delete the data sucessfull by id ");
		}, function(){
				console.log(response.data.message);
		});
	}
	
	
});
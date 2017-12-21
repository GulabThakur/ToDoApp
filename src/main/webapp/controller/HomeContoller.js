
/**
 * @author ThakurGulab
 * @Decription home controller 
 * 
 */
var app = angular.module('ToDo');
app.controller('homepageCrt',function($scope, homeService, $location,$state,$window,$mdToast, $document) {
	 
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
		}, function(response){
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
	$scope.deletebyId=function(note){
		var url="delete/"+note.id;
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
	$scope.updateById=function(note){
		var url="update/"+note.id;
		var method="put";
		var token =localStorage.getItem('jwt');
		var data=note;
		console.log(note);
		var nodes=homeService.getAllnode(url,method,token,data);
		nodes.then(function(response){
			console.log("update the data sucessfull by id ");
		}, function(response){
				console.log(response.data.meResponse);
		});
	}
	// archive on
	$scope.archive=function(note)
	{
		$mdToast.show (
                $mdToast.simple()
                .textContent('Note Archived ..')                       
                .hideDelay(3000)
             )
		if(note.archive==false){
			note.archive = true;
			
		}else{
			note.archive = false;
		}
		$scope.updateById(note);
	console.log(note);
	}
	
	// delete note..
	$scope.trash=function(note)
	{
		if(note.trash==false){
			note.trash=true;
		}
		else{
			note.trash=false;
		}
		$mdToast.show (
                $mdToast.simple()
                .textContent('Note trashed ..')                       
                .hideDelay(3000)
             )
		$scope.updateById(note);
		console.log("sucessfull...");
	}
	
	
	// pin notes....
	$scope.pin=function(note)
	{
		 
		if(note.pin==false){
			note.pin=true;
		}else{
			note.pin=false;
		}
		$scope.updateById(note);
		console.log("sucessfull pined");
	}
	
	// trashFunction....
	$scope.trashFunction=function()
	{
		$state.go('TrashLoad');
		console.log("come inside sucessfull")
	}
	//  call from notes..
	$scope.notesFunction=function()
	{
		$state.go('homepage');
		console.log("sucessfull come..");
	}
	
	
	// archiveFunction...
	$scope.archiveFunction=function()
	{
		$state.go('ArchiveLoad');
		console.log("sucessfull come..");
	}
	
	$scope.Mydate=function(){
		
            
	}
	
});
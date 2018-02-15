/**
 * @author ThakurGulab
 * @Decription home controller
 * 
 */
// this this my home controller ....
var app = angular.module('ToDo');
app.controller('homepageCrt', function($scope, homeService, $location, $state,
		$window, $mdToast, $document, mdcDateTimeDialog, $filter, $interval,
		$mdDialog,$timeout,$mdSidenav) {
	
	 
	
	// FOR Image crop
	
	
	  $scope.cropper = {};
      $scope.cropper.sourceImage = null;
      $scope.cropper.croppedImage = null;
      $scope.bounds = {};
      $scope.bounds.left = 0;
      $scope.bounds.right = 0;
      $scope.bounds.top = 0;
      $scope.bounds.bottom = 0;
	
	//
	
	$(function(){
		 $("#Div1 input").keypress(function (e) {
		    if (e.keyCode == 13) {
		    	 alert('You pressed enter!');
		    }
		 });
		});
	
	/*================================================================================================================*/
	
	var allNotes=[];
	
	var token = localStorage.getItem('jwt');
	
	$scope.labelNote =function(note){
		
		var parentEl=angular.element(document.body);
		console.log(note);
		$mdDialog
				.show({
					parent:parentEl,
					/*targetEvent:event,*/
					templateUrl:'template/LabelDialogNote.html',
					locals:{
						data:$scope.labels,
						noteData : note
					},
					clickOutsideToClose : true,
					controller : function($scope,homeService,data,noteData){
						$scope.labels=data;
						$scope.labels[0].checked = true;
						$scope.selected=[];

						$scope.note=noteData;
						var selectedLabels = noteData.labels.map(function(data){
							return data.labelsId;
						});
//						$scope.labels = $scope.labels.ma
						$scope.toggle=function(label,list,note){
							
							 var idx = list.indexOf(label);
						        if (idx > -1) {
						          list.splice(idx, 1);
						        }
						        else {
						        	
						          console.log(label);
						          $scope.selected=list;
						          var lables=homeService.getAllnode("setLabels/"+note.id+"/"+label.labelsId+"","post",token,null);
						        }
						};
						
						$scope.exists=function(label, list){
							return selectedLabels.indexOf(label.labelsId) > -1
//							list.indexOf(label) > -1;
							 
						};
					}
				})
	}
	
	/*================================================================================================================*/
	
	$scope.deleteLavel=function(noteId,labelsId){
		console.log("    ILU hutyu    :",noteId,labelsId);
		var lableDel=homeService.getAllnode("lavelDelete/"+noteId+"/"+labelsId+"","post",token,null);
		lableDel.then(function(response){
			console.log(response.data.message);
		},function(response){
			console.log(response.data.message)
		});
	}
	
	/*================================================================================================================*/
	$scope.lablesAlert=function(){
		var parentEl=angular.element(document.body);
		$mdDialog
				.show({
					parent:parentEl,
					/*targetEvent:event,*/
					templateUrl:'template/LablesDilog.html',
					locals:{
						data:$scope.labels
					},
					clickOutsideToClose : true,
					controller : function($scope,homeService,data){
						$scope.labels=data;
						
						/*=======================================================================*/
						$scope.addLabel=function(label){
							$mdDialog.hide();
							var addLabel=homeService.getAllnode("createLabel","post",token,label);
						}
						/*=======================================================================*/
						$scope.updateLabel=function(label){
							$mdDialog.hide();
							var updateLabel=homeService.getAllnode("updateLables","post",token,label);
							
						}
						
						/*=======================================================================*/
						
						$scope.deleteLabel=function(label){
							$mdDialog.hide();
							var deleteLabel=homeService.getAllnode("deleteLable","post",token,label);
						}
						/*=======================================================================*/
					}
				})
		
	}
	
	
	/*================================================================================================================*/
	$scope.labels={};
	$scope.getLabels=function(){
		var labels=homeService.getAllnode("fetchLabls","post",token,null);
		labels.then(function(response){
			$scope.labels=response.data;
			console.log(response.data);
		},function(response){
			console.log(response.data)
		});
	}
	
	$scope.getLabels();
	/*================================================================================================================*/
	
	var userData = null;
	$scope.showAlert = function(note) {
		var parentEl = angular.element(document.body);
		/*$scope.owner(note);*/
		$scope.getUser(note);
		$mdDialog
				.show({
					parent : parentEl,
					/*targetEvent : event,*/
					templateUrl : 'template/collaboratore.html',
					locals : {
						data : note,
						owner : $scope.ownerdetails,
						userListData:$scope.userList
					},
					clickOutsideToClose : true,
					controller : function($scope, data, owner, homeService) {
						$scope.note = data;
						$scope.user = owner
						
						/* console.log(user); */
						$scope.closeDialog = function(note,email) {
							var token = localStorage.getItem('jwt');
							var nodes = homeService.getAllnode("notesShare/"+email+"/"+note.id+"", "post", token, null);
							console.log(note.id);
							console.log(email);
							console.log("close dilog box");
							 $mdDialog.hide();
						}
						
						
						$scope.removeCollab=function(note,email){
					 $mdDialog.hide();
							var token =localStorage.getItem('jwt');
							var deleteUser = homeService.getAllnode("deleteUser/"+email+"", "post", token, note);
							deleteUser.then(function(response){
								console.log("Remove User");
							},function(response){
								console.log("helllo ")
							});
							
						}
					}
				});
		console.log("comint complete show alert");
	}
	
	/*================================================================================================================*/
	
	$scope.removeUser=function(note,email){
		var token =localStorage.getItem('jwt');
		var deleteUser = homeService.getAllnode("deleteUser/"+email+"", "post", token, note);
		deleteUser.then(function(response){
			console.log("Remove User");
		},function(response){
			console.log("helllo")
		});
	}

	/*================================================================================================================*/
	
	$scope.getUser=function(note){
		var token=localStorage.getItem('jwt');
		var userData=homeService.getAllnode("collabUser","post",token,note);
		userData.then(function(response){
			$scope.userList=response.data;
			console.log(response.data);
		},function(response){
			console.log("hello"+response.data.message);
		});
	}
	
	// this method is get owner...
	
	
	/*================================================================================================================*/
	
    // this function use for share note 
	$scope.shareNote = function(note, email) {
	   var token = localStorage.getItem('jwt');
	   var nodes = homeService.getAllnode("notesShare/"+email+"/"+note.id+"", "post", token, null);
	   nodes.then(function(response) {
			//$scope.user = response.data;
			//$scope.ownerdetails=response.data;
		   console.log("welcome to share note");
		}, function(response) {
			console.log(response.data);
		});
	}

	/*================================================================================================================*/
	
	// this method using for call profile..
	$scope.ownerdetails = {};
	$scope.profileData = function() {
		var nodes = homeService.getAllnode("userData", "post", token, null);
		nodes.then(function(response) {
			$scope.user = response.data;
			$scope.ownerdetails=response.data;
		}, function(response) {
			console.log(response.data);
		});

	}

	$scope.cropImage = function(userPic){
		console.log("welcome.....imageCrop....Function");
		
	}
	/*================================================================================================================*/
	
	
	$scope.profileData();

	// this is function for create note
	$scope.addNode = function() {
		$scope.allNodes();
		var data = $scope.note;
		var nodes = homeService.getAllnode("create", "post", token, data);
		nodes.then(function(response) {
			console.log("Note created ");
			$scope.allNodes();
			$scope.note.title='';
			$scope.note.description='';
		}, function(response) {
			console.log(response.data.message);
		});

	}
	
	
	$scope.addCopy=function(note){
		$scope.allNodes();
		var nodes = homeService.getAllnode("create", "post", token, note);
	}
	/*================================================================================================================*/
	
	// get all note with help this function ....
	$scope.allNodes = function() {
		console.log("inside all note");
		$scope.notes = [];
		var nodes = homeService.getAllnode("all",  "post", token, null);
		nodes.then(function(response) {
			$scope.notes = response.data;
			console.log(response.data);
			console.log("fetch the data sucessfull ");
			$scope.allNotes=response.data;
			$scope.data = response.data;
			var notes = response.data;
			console.log("Notes---->" + $scope.data.length);
			$interval(function() {
				for (var i = 0; i < $scope.data.length; i++) {
					if (notes[i].reminder != null) {
						reminderDate = $filter('date')(
								new Date(notes[i].reminder),
								'MMM dd yyyy HH:mm');
						var currentDate = $filter('date')(new Date(),
								'MMM dd yyyy HH:mm');
						//console.log("system Date----->" + currentDate);
						//console.log("Reminder Date------>" + reminderDate);
						if (currentDate == reminderDate) {
							alert(notes[i].description);
							notes[i].reminder = null;
							$scope.updateById(notes[i]);
						}
					}
				}
			}, 50000);
			$scope.notes = response.data;
		}, function(response) {
			var rep = response.data.message;
			console.log(rep);
		});
	}
	
	/*================================================================================================================*/
	
	// here i am call automatically allnodes();
	$scope.allNodes();
	$scope.getById = function() {
	
		// var data=$scope.note;
		var nodes = homeService.getAllnode( "record", "get", token, data);
		nodes.then(function(response) {
			console.log("fetch the data sucessfull by id ");
		}, function() {
			console.log(response.data.message);
		});
	}
	
	/*================================================================================================================*/
	
	// note will be delete by id
	$scope.deletebyId = function(note) {
		var url = "delete/" + note.id;
		var method = "delete";
		var token = null;
		var data = null;
		var nodes = homeService.getAllnode(url, method, token, data);
		nodes.then(function(response) {
			console.log("delete the data sucessfull by id ");
			$scope.allNodes();
		}, function() {
			console.log(response.data.message);
		});
	}
	
	/*================================================================================================================*/
	
	// note will be update by id....
	$scope.updateById = function(note) {
		
		var url = "update/" + note.id;
		var method = "put";
		var token = localStorage.getItem('jwt');
		var data = note;
		// console.log(note);
		var nodes = homeService.getAllnode(url, method, token, data);
		nodes.then(function(response) {
			console.log("update the data sucessfull by id ");
			$scope.allNodes();
		}, function(response) {
			console.log(response.data.meResponse);
		});
	}
	
	/*================================================================================================================*/
	
	// archive on
	$scope.archive = function(note) {
		$mdToast.show($mdToast.simple().textContent('Note Archived ..')
				.hideDelay(3000))
		if (note.archive == false) {
			note.archive = true;

		} else {
			note.archive = false;
		}
		$scope.updateById(note);
		console.log(note);
	}
	
	/*================================================================================================================*/
	
	// delete note..
	$scope.trash = function(note) {
		if (note.trash == false) {
			note.trash = true;
		} else {
			note.trash = false;
		}
		$mdToast.show($mdToast.simple().textContent('Note trashed ..')
				.hideDelay(3000)

		)
		$scope.updateById(note);
		console.log("sucessfull...");
	}

	/*================================================================================================================*/
	
	// pin notes....
	$scope.pin = function(note) {
		console.log("gdfgfhfhghhhhh");
		if (note.pin == false) {
			note.pin = true;
		} else {
			note.pin = false;
		}
		$scope.updateById(note);
		console.log("sucessfull pined");
	}
	/*================================================================================================================*/
	// trashFunction....
	$scope.trashFunction = function() {
		$state.go('TrashLoad');
		console.log("come inside sucessfull")
	}
	
	// reminderFunction....
	$scope.reminderLoad=function(){
		$state.go('Reminder');
		console.log("come in side reminder....");
	}
	
	/*================================================================================================================*/
	// call from notes..
	$scope.notesFunction = function() {
		$state.go('homepage');
		console.log("sucessfull come..");
	}
	
	/*================================================================================================================*/

	// archiveFunction...
	$scope.archiveFunction = function() {
		$state.go('ArchiveLoad');
		console.log("sucessfull come..");
	}
	
	/*================================================================================================================*/
	// for color picker...
	
	$scope.color = function() {
		//console.log("color calll");
		$scope.options = [ 'transparent', '#FF8A80', '#FFD180', '#FFFF8D',
				'#CFD8DC', '#80D8FF', '#A7FFEB', '#CCFF90' ];
		
		$scope.colorChanged = function(newColor, note) {
			console.log("COLORE CHANGE KKLKKJJJJJJ");
			note.color = newColor;
			$scope.updateById(note);
			console.log("Hi........" + note.color);
		}

	}
/*================================================================================================================*/
	// for using date time piker....

	$scope.displayDialog = function() {
		mdcDateTimeDialog.show({
			maxDate : $scope.maxDate,
			time : false
		}).then(function(date) {
			$scope.selectedDateTime = date;
			console.log('New Date / Time selected:', date);
		}, function() {
			console.log('Selection canceled');
		});
	}
	/*================================================================================================================*/
	// for reminder function......
	$scope.reminderFunction = function(note, reminderDate) {
		console.log("Inside Remainder..........");
		console.log("time is current :",reminderDate);
		console.log(note);
		note.reminder = reminderDate;
		var date = new Date(reminderDate);
		//var date=Date.parse(reminderDate);
		//console.log("......................:",date);
		var dates = $filter('datetime')(date, 'MM dd yyyy HH:mm');
		console.log("tiem will cahge",dates);
		// var parseDate = Date.parse();
		note.reminder = dates;
		console.log(" notes obj" , note.reminder);
		$scope.updateById(note);
		$scope.allNodes();
		
	}
	
	/*================================================================================================================*/

	// function to delete the reminder date
	$scope.deleteReminder = function(note) {

		console.log("it came inside delete reminder ")
		note.reminder = "";
		// here i am calling my update
		$scope.updateById(note);
		console.log("sucessfull update  note...");

	}
	/*================================================================================================================*/
	// This for image upload

	$scope.type = [];
	$scope.openHiddenButton = function(note) {
		console.log("welcome to open hidden function...");
		$('#image').trigger('click');
		$scope.type = note;
		console.log("helllo trigger after");
	}

	/*================================================================================================================*/
	$timeout(function() {$scope.imageUpload();}, 3000);
	$scope.stepsModel = [];
	$scope.imageUpload = function(note) {
		//localStorage.setItem('noteData',note);
		console.log("hello we r in imageUpload",note);
		var reader = new FileReader();
		console.log("" ,note );
		reader.onload = $scope.imageLoader;
		//var notedata=localStorage.getItem('noteData');
		reader.readAsDataURL(note.image);
		console.log("note.image : " ,note.imageNote);
	}

	
	/*================================================================================================================*/
	
	$scope.imageLoader = function(image) {

		$scope.$apply(function() {
			$scope.stepsModel.push(image.target.result);
			var imageSrc = image.target.result;
			$scope.type.imageNote = imageSrc;
			$scope.updateById($scope.type);

		});
	}
	/*================================================================================================================*/
	

	$scope.socialShare = function(note) {
		console.log("facebook share");
		FB.init({
			appId : '1976981345893014',
			status : true,
			cookie : true,
			xfbml : true,
			version : 'v2.4'
		});
		FB.ui({
			method : 'share_open_graph',
			action_type : 'og.likes',
			action_properties : JSON.stringify({
				object : {
					'og:title' : note.title,
					'og:description' : note.description
				}
			})
		}, function(response) {
			alert('Posting Successfull..');
		}, function(error) {
			alert('Somthing went Wrong,Posting of fb Unsuccessfull..');
		});
     };
     
     
     /*================================================================================================================*/
     

		$scope.searchAll=function(text){
			var result=[];
			$scope.searchNotes=result;
			if(text.length>0){
			var notes=$scope.allNotes;
			var index=0;
			var result=[];
			for(var i=0;i<notes.length;i++){
				if((notes[i].title.toLowerCase()).search(text)>-1){
				result[index++]=notes[i];
				}
				else if((notes[i].description.toLowerCase()).search(text)>-1){
					result[index++]=notes[i];
				}
				else if(notes[i].label){
					var label=notes[i].label;
					for(var j=0;j<label.length;j++){
						if((label[j].labelName.toLowerCase()).search(text)>-1){
							result[index++]=notes[i];
						}
					}
				}else if(notes[i].sharedUser){
					console.log("inside share");
					var shared=notes[i].sharedUser;
					for(var k=0;j<shared.length;k++){
						if((shared[k].email.toLowerCase()).search(text)>-1){
							result[index++]=notes[i];
						}else if((shared[k].userName.toLowerCase()).search(text)>-1){
							result[index++]=notes[i];
						}
					}
				}
			}
			console.log(result);
			$scope.searchNotes=result;
			}
			return result;
		}
		
		
		
		/*================================================================================================================*/
		$scope.editNote =function(note){
			var parentEl=angular.element(document.body);
			$mdDialog
					.show({
						parent:parentEl,
						targetEvent:event,
						templateUrl:'template/EditNote.html',
						locals:{
							data : note
						},
						clickOutsideToClose : true,
						controller : function($scope,data,homeService){
							$scope.note=data;
							$scope.updateNote =function(note){
								var data=note;
								$mdDialog.hide();
								$scope.updateById(data);
							}
							
							
						}
					})
		}
		/*================================================================================================================*/	
		$scope.logout=function(){
			localStorage.clear();
			$state.go('login');
			
	}
		
		$scope.searchFunction=function(){
			$state.go('Search');
		}

		
		
		/*================================================================================================================*/
		 $scope.showNav=true;
		    $scope.hideNav=function(){
		    	$scope.showNav=!$scope.showNav;
		    	console.log("show nav value is :",$scope.showNav);
		    	
	     } 
		
		
		/*================================================================================================================*/
		// for list and grid view
		$scope.view = "true";

		$scope.flex = "30";
		$scope.align1 = "row";
		
		//$scope.align2 = "column";

		/*================================================================================================================*/	
		$scope.changeView = function() {
			if ($scope.view) {
				$scope.flex = "50";
				$scope.align1 = "column";
				/*$scope.align2 = "center";*/
				$scope.view = !$scope.view;
			} else {
				$scope.flex = "30";
				$scope.align1 = "row";
				/*$scope.align2 = "start";*/
				$scope.view = !$scope.view;
			}
         }
		
	
		var scopepicImage;
		
		$scope.changeImage=function(){
			
			var parentEl=angular.element(document.body);
			$mdDialog
					.show({
						parent:parentEl,
						templateUrl:'template/imgeCrop.html',
						locals:{

							 cropper: $scope.cropper = {},
						     sourceImage:$scope.cropper.sourceImage = null,
						     croppedImage: $scope.cropper.croppedImage = null,
						     bounds:$scope.bounds = {},
						     left :$scope.bounds.left = 0,
						     right :$scope.bounds.right = 0,
						     top:$scope.bounds.top = 0,
						     bottom : $scope.bounds.bottom = 0,
						     userData:$scope.user
						},
						clickOutsideToClose : true,
						controller: function($scope,cropper,sourceImage,croppedImage,bounds,left,right,top,bottom,userData){
							 $scope.imageCrop= function(imageUrl){
								 // scopepicImage=$scope.cropper.croppedImage;
								    userData.proFile=$scope.cropper.croppedImage;
									var profile= homeService.getAllnode("profilChage", "post", token, userData);
									$mdDialog.hide();
									 }
						}
					})
		}
		$scope.profileUpdate=function(){
			if($scope.scopepicImage!=null){
				$scope.user.proFile=$scope.scopepicImage;
				var profile= homeService.getAllnode("profilChage", "post", token, user);
				console.log("change Image",scopepicImage);
			}
		}
		
		
		
		$scope.toggleLeft = buildToggler('left');
		 function buildToggler(componentId) {
		      return function() {
		    	 // console.log("welcome to ", componentId);
		        $mdSidenav(componentId).toggle();
		      };
		    }
		
		/*================================================================================================================*/
		
		/*
		$scope.homeAuth=function(){
			if(token==null){
				$state.go('login');
			}
			else{
				
				$state.go('homepage');
			}
		}
		
		//$scope.homeAuth();
*/})/*(window.angular)*/;
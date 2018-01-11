var app=angular.module('ToDo');

app.directive('cardDrective', function(){
	
	return {
		 restrict: 'E',
		
		 scope: { 
			 data: '=',
			 userData:'=',
			 pin:'&',
			 deleteReminder:'&',
			 showAlert:'&',
			 reminderFunction:'&',
			 imageUpload:'&',
			 openHiddenButton:'&',
			 trash:'&',
			 socialShare:'&',
			 addCopy:'&',
			 archive:'&',
			 labelNote:'&'
			 },
		 templateUrl:"template/cardDriective.html",
	};
});



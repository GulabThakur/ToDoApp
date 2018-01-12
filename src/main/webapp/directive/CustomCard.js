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
			 labelNote:'&',
			 color:'&',
			 option:'=',
			 col:'=',
			 colorChanged:'&'
			 },
		 templateUrl:"template/cardDriective.html",
	};
});



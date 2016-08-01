/**
 * 
 */

angular.module('app').controller("CountController", ["CountService", "$scope", function(CountService, $scope) {
	console.log('CountController')
	
	
	var ctrl = this;
	ctrl.number = 0;
	
	CountService.dataStream.onMessage(function(message) {
	      ctrl.number = JSON.parse(message.data).number;
	      console.log(ctrl.number);
	      $scope.$apply();
	    });
		

	
	ctrl.increment = CountService.increment;
	ctrl.close = CountService.close;
	
}]);
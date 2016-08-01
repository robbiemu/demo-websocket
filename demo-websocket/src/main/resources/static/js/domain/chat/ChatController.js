/**
 * 
 */

angular.module('app').controller("ChatController", ["ChatService", "$scope", function(ChatService, $scope) {
	console.log('ChatController')

	var ctrl = this
	ctrl.msg = {}
	ctrl.log = ""
	
	ChatService.dataStream.onMessage(function(message) {
			let msg = JSON.parse(message.data)

			console.dir(msg)

			ctrl.log += msg.username + ': ' + msg.message + '\n'
			$scope.$apply()
	    });
		

	
	ctrl.message = function () { ChatService.message(ctrl.msg) }
	ctrl.close = ChatService.close
	
}])
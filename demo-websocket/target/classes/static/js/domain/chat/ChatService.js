/**
 * 
 */

angular.module('app').service('ChatService',
[ '$http', '$websocket', function($http, $websocket) {
	
	var service = this;
		
	service.dataStream = $websocket('ws://10.160.113.167:8080/message');
	
    this.message = function(msg) {
    	cmd = {username: msg.username, message: msg.message}
    	service.dataStream.send("MESSAGE " + JSON.stringify(cmd))
    }
    
    this.close = function() {
    	service.dataStream.send("CLOSE");
    }
	
} ]);
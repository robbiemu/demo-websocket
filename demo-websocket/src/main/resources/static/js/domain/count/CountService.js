/**
 * 
 */

angular.module('app').service('CountService',
[ '$http', '$websocket', function($http, $websocket) {
	
	var service = this;
	
	service.dataStream = $websocket('ws://localhost:8080/number');
	
    this.increment = function() {
    	service.dataStream.send("INCREMENT");
    }
    
    this.close = function() {
    	service.dataStream.send("CLOSE");
    }
	
} ]);
angular.
  module('app').
  config(['$routeProvider', 'baseRoute',
    function config($routeProvider, baseRoute) {

      $routeProvider.
      	when('/count', {
          
          templateUrl: baseRoute + "count/template.html",
          controller: 'CountController',
          controllerAs: 'ctrl'
          
        }).
      	when('/chat', {
            templateUrl: baseRoute + "chat/template.html",
            controller: 'ChatController',
            controllerAs: 'ctrl'            
          }).
        otherwise('/count');
    }
  ]);
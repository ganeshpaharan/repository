var movieManager = angular.module('movieManager', [
  'ngRoute',
  'ui.bootstrap',
  'movieControllers'
]);

movieManager.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
  when('/list', {
    templateUrl: 'partials/list.html',
    controller: 'ListController'
  }).
  when('/browser', {
     templateUrl: 'partials/browser.html',
    controller: 'BrowserController'
  }).
  when('/details/:itemId', {
    templateUrl: 'partials/details.html',
    controller: 'DetailsController'
  }).
  otherwise({
    redirectTo: '/list'
  });
}]);

movieManager.filter('startFrom', function(){
  return function(data, start){
    return data.slice(start);
  }
})


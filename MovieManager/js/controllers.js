var movieControllers = angular.module('movieControllers', ['angularUtils.directives.dirPagination','ngAnimate']);

movieControllers.controller('ListController', ['$scope', '$http', function($scope, $http) {
  $http.get('js/data.json').success(function(data) {
    $scope.movies = data;
    $scope.movieOrder = 'Title';
    $scope.movieOrder = 'imdbRating';
  });

  $http.get('js/data.json').success(function(data) {
    $scope.movies = data;
    $scope.movies_list = 10;
    /*for (var i=0; i<$scope.movies.length; i++){
      $scope.release_date = $scope.movies[i].Released;
      return $scope.release_date;
    }*/
  });
}]);

movieControllers.controller('DetailsController', ['$scope', '$http','$routeParams', function($scope, $http, $routeParams) {
  $http.get('js/data.json').success(function(data) {
    $scope.movies = data;
    $scope.whichItem = $routeParams.itemId;

    if ($routeParams.itemId > 0) {
      $scope.prevItem = Number($routeParams.itemId)-1;
    } else {
      $scope.prevItem = $scope.movies.length-1;
    }

    if ($routeParams.itemId < $scope.movies.length-1) {
      $scope.nextItem = Number($routeParams.itemId)+1;
    } else {
      $scope.nextItem = 0;
    }  
  });
}]);

movieControllers.controller('BrowserController', ['$scope', '$http', function($scope, $http) {
  $http.get('js/data.json').success(function(data) {
    $scope.movies = [];
    $scope.pageSize = 20;
    $scope.currentPage = 1;
    $scope.movies = data;
    $scope.imdbRating = ["0+","1+","2+","3+","4+","5+","6+","7+","8+","9+"];
    $scope.actor = getActors(data);
    $scope.director = getDirector(data);
    $scope.genre = ["Action","Adventure","Animation","Biography","Comedy","Crime","Documentary","Drama","Family","Fantasy","Film-Noir","Game-Show","History","Horror","Music","Musical","Mystery","News","Reality-TV","Romance","Sci-Fi","Sport","Talk-Show","Thriller","War","Western"]});
       
    function getActors(movies){
      var actors;
      var actorsList = [];
      for(var i=0; i<movies.length; i++){
          actors = movies[i].Actors;
          if(actors){
            actorsList = actorsList.concat(actors.split(","));
          }
      }
      return actorsList;
    }

    function getDirector(movies){
      var director = [];
      movies.forEach(function(movies){
        director.push(movies.Director);
      });
      var directorLength=director.length;
      directorList=[];
      counts={};
      for (var i=0;i<directorLength;i++) {
        var item = director[i];
        counts[item] = counts[item] >= 1 ? counts[item] + 1 : 1;
        if (counts[item] <= 1) {
          directorList.push(item);
        }
      }
      return directorList;
    }

    $scope.ratingFilter = function(selectedrating){
      if(selectedrating != undefined || selectedrating !=null){
        return function( movie ) {
          var rating = selectedrating.substring(0,selectedrating.length-1);
          return movie.imdbRating >= rating;
        };
      }
    }

    $scope.clearFilter = function () {
      $scope.searchString = null;
      $scope.selectedrating = null;
      $scope.selectedgenre = null;
      $scope.selectedactor = null;
      $scope.selecteddirector = null;
    }
    
}]);

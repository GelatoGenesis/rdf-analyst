'use strict';

rdfAnalystControllers.controller('QueryDetailsController', ['$scope', '$routeParams', '$http',

    function($scope, $routeParams, $http) {

        var queryToBeRequested = $routeParams.queryName;

        $http.get('/queries/' + queryToBeRequested).success(function(queryData) {
          $scope.queryName = queryData.queryName;
          $scope.query = queryData.query;
          $scope.refreshResult = _initResultRDFs;

          _initResultRDFs();
        });


        function _initResultRDFs() {
            $http.get('/responses/' + queryToBeRequested).success(function(queryResponsesData) {
                $scope.queryResults = queryResponsesData;
            });
        }
    }]
);
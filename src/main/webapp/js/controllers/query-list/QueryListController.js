'use strict';

rdfAnalystControllers.controller('QueryListController', ['$scope', '$http',
    function($scope, $http) {

        _loadAllStreams();
        _loadActiveQueries();
        _loadLocalQueries();

        $scope.submitNewQuery = _addQuery;
        $scope.activeQueriesInvisible = true;
        $scope.localQueriesInvisible = true;
        $scope.toggleActiveQueries = function () {
            $scope.activeQueriesInvisible = !$scope.activeQueriesInvisible;
        }
        $scope.toggleLocalQueries = function () {
            $scope.localQueriesInvisible = !$scope.localQueriesInvisible;
        }

        function _loadAllStreams() {
            $http.get('/available-streams').success(function(data) {
              $scope.streams = data;
            });
        }

        function _loadActiveQueries() {
            $http.get('/available-queries').success(function(data) {
              $scope.queries = data;
            });
        }

        function _loadLocalQueries () {
            $http.get('/available-local-queries').success(function(data) {
                $scope.localQueries = data;
            });
        }

        function _addQuery() {
            var queryString = $scope.newQuery;
            var stream = $scope.newQueriesStream;
            $http.post('/add-query', {query: queryString}).success(function(data) {
                if (data.message == 'OK') {
                    _loadActiveQueries();
                    _loadLocalQueries();
                    $scope.newQuery = '';
                } else {
                    alert("Server responded with an error: " + data.message);
                }
            }).error(function(data, status, headers, config) {
                alert("Server responded with an error. Status: " + status);
            });;
        }
    }]
);

'use strict';

rdfAnalystControllers.controller('QueryListController', ['$scope', '$http',
    function($scope, $http) {

        _loadAllStreams();
        _loadActiveQueries();
        _loadArchivedQueries();

        $scope.submitNewQuery = _addQuery;
        $scope.activeQueriesInvisible = true;
        $scope.archivedQueriesInvisible = true;
        $scope.toggleActiveQueries = function () {
            $scope.activeQueriesInvisible = !$scope.activeQueriesInvisible;
        }
        $scope.toggleArchivedQueries = function () {
            $scope.archivedQueriesInvisible = !$scope.archivedQueriesInvisible;
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

        function _loadArchivedQueries () {
            $scope.archivedQueries = [{
                topic : "Mock Query",
                query: "Some random query."
            }];
        }

        function _addQuery() {
            var queryString = $scope.newQuery;
            var stream = $scope.newQueriesStream;
            $http.post('/add-query', {query: queryString}).success(function(data) {
                if (data.message == 'OK') {
                    _loadActiveQueries();
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

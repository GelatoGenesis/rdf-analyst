'use strict';

rdfAnalystControllers.controller('QueryListController', ['$scope', '$http',
    function($scope, $http) {

        _clearErrors();
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

        $scope.cancelQuery = function(topic) {
            if(confirm("Are you sure you want to cancel quering this topic?")) {
                $http.get('/cancel-query/' + topic).success(function(data) {
                    _clearErrors();
                    _loadAllStreams();
                    _loadActiveQueries();
                    _loadLocalQueries();
                }).error(function(data) {
                    $scope.generalError = "There was an error canceling the topic " + topic + ": " + (data != null && data.error != null ? data.error : data);
                });
            }
        }

        function _loadAllStreams() {
            $http.get('/available-streams').success(function(data) {
              $scope.streams = data;
              if (data.length == 0) {
                $scope.streamError = "No active streams found.";
              }
            }).error(function(data) {
                $scope.streamError = "There was an error getting info about active streams: " + data.error;
            });
        }

        function _loadActiveQueries() {
            $http.get('/available-queries').success(function(data) {
              $scope.queries = data;
              if (data.length == 0) {
                $scope.queryError = "No active queries found.";
              }
            }).error(function(data) {
              $scope.queryError = "There was an error getting info about active queries: " + data.error;
          });
        }

        function _loadLocalQueries () {
            $http.get('/available-local-queries').success(function(data) {
                $scope.localQueries = data;
                if (data.length == 0) {
                    $scope.localQueryError = "No local queries found.";
                }
            }).error(function(data, status, headers, config) {
              $scope.localQueryError = "There was an error getting info about local queries: " + data.error;
          });
        }

        function _addQuery() {
            var queryString = $scope.newQuery;
            var stream = $scope.newQueriesStream;
            $scope.queryAddingError = null;
            $http.post('/add-query', {query: queryString}).success(function(data) {
                if (data.message == 'OK') {
                    _clearErrors();
                    _loadAllStreams();
                    _loadActiveQueries();
                    _loadLocalQueries();
                    $scope.newQuery = '';
                } else {
                    $scope.errors = [];
                    $scope.queryAddingError = "Server responded with an error: " + data.message;
                }
            }).error(function(data, status, headers, config) {
                $scope.errors = [];
                $scope.queryAddingError = "Server responded with an error. Status: " + data.message;
            });;
        }

        function _clearErrors() {
            $scope.generalError = null;
            $scope.queryAddingError = null;
            $scope.streamError = null;
            $scope.localQueryError = null;
            $scope.queryError = null;
        }
    }]
);

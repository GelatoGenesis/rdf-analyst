'use strict';

rdfAnalystControllers.controller('QueryListController', ['$scope', '$http',
    function($scope, $http) {

        _loadAllStreams();
        _loadAllQueries();

        $scope.submitNewQuery = _addQuery;

        function _loadAllStreams() {
            $http.get('/available-streams').success(function(data) {
              $scope.streams = data;
            });
        }

        function _loadAllQueries() {
            $http.get('/all-queries').success(function(data) {
              $scope.queries = data;
            });
        }

        function _addQuery() {
            var queryString = $scope.newQuery;
            var stream = $scope.newQueriesStream;
            if(!stream) {
                alert("You have to pick a stream before registering a query. If no streams are available then query can not be registered.");
                return;
            }
            $http.post('/add-query', {query: queryString, stream: stream}).success(function(data) {
                if (data.message == 'OK') {
                    _loadAllQueries();
                    $scope.newQuery = '';
                } else {
                    alert("Server responded with an error: " + data.message);
                }
            }).error(function(data, status, headers, config) {
                alert("Server responded with an error.");
            });;
        }
    }]
);

'use strict';

rdfAnalystControllers.controller('QueryListController', ['$scope', '$http',
    function($scope, $http) {

        _loadAllQueries();

        $scope.submitNewQuery = _addQuery;

        function _loadAllQueries() {
            $http.get('/all-queries').success(function(data) {
              $scope.queries = data;
            });
        }

        function _addQuery() {
            var queryString = $scope.newQuery;
            $http.post('/add-query', queryString).success(function(data) {
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

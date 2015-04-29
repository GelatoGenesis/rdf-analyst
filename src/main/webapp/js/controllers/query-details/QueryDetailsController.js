'use strict';

rdfAnalystControllers.controller('QueryDetailsController', ['$scope', '$routeParams', '$http',

    function($scope, $routeParams, $http) {

        var queryToBeRequested = $routeParams.queryName;
        $scope.resultLimit = 100;
        $scope.countOfBars = 30;
        $scope.options = {width: 500, height: 300, 'bar': 'aaa'};
        $scope.data = [];
        $scope.refreshResult = _initResultRDFs;

        $http.get('/available-local-queries/' + queryToBeRequested).success(function(queryData) {
          $scope.query = queryData;
          _initResultRDFs();
        });


        function _initResultRDFs() {
            $http.get('/local-query-results/' + queryToBeRequested).success(function(queryResponsesData) {
                queryResponsesData = mergeSort(queryResponsesData);
                $scope.queryResults = queryResponsesData;
                $scope.data = analyzeResultsForGraph(queryResponsesData);
            });
        }

        function analyzeResultsForGraph(queryResponsesData) {
            if (queryResponsesData.length == 0) {
                return [];
            }
            var maxCreated = queryResponsesData[queryResponsesData.length - 1].created;
            var minCreated = queryResponsesData[0].created;
            var countOfBars = $scope.countOfBars;
            var intervalStep = (maxCreated - minCreated) / countOfBars;
            if (intervalStep < countOfBars) {
                return [queryResponsesData.length];
            }
            var intervalMaxLimit = minCreated + intervalStep;
            var consumationIterator = 0;
            var result = [];
            for (var i = 0; i < countOfBars; i++) {
                result[i] = 0;
                while(consumationIterator < queryResponsesData.length && queryResponsesData[consumationIterator].created <= intervalMaxLimit){
                    result[i] = result[i] + 1;
                    consumationIterator++;
                }
                intervalMaxLimit = intervalMaxLimit + intervalStep;
            }
            return result;
        }


        function mergeSort(arr)
        {
            if (arr.length < 2)
                return arr;

            var middle = parseInt(arr.length / 2);
            var left   = arr.slice(0, middle);
            var right  = arr.slice(middle, arr.length);

            return merge(mergeSort(left), mergeSort(right));
        }

        function merge(left, right)
        {
            var result = [];

            while (left.length && right.length) {
                if (left[0].created <= right[0].created) {
                    result.push(left.shift());
                } else {
                    result.push(right.shift());
                }
            }

            while (left.length)
                result.push(left.shift());

            while (right.length)
                result.push(right.shift());

            return result;
        }
    }]
).directive('barChart', function(){
     var chart = d3.custom.barChart();
     return {
         restrict: 'E',
         replace: true,
         template: '<div class="chart"></div>',
         scope:{
             height: '=height',
             data: '=data',
             hovered: '&hovered'
         },
         link: function(scope, element, attrs) {
             var chartEl = d3.select(element[0]);

             scope.$watch('data', function (newVal, oldVal) {
                 chartEl.datum(newVal).call(chart);
             });

             scope.$watch('height', function(d, i){
                 chartEl.call(chart.height(scope.height));
             });
         }
     }
});


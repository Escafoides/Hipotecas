(function() {
    'use strict';
    angular
        .module('hipotecasApp')
        .factory('ImporteFinal', ImporteFinal);

    ImporteFinal.$inject = ['$resource'];

    function ImporteFinal ($resource) {
        var resourceUrl =  'api/importe-finals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('hipotecasApp')
        .factory('Terreno', Terreno);

    Terreno.$inject = ['$resource'];

    function Terreno ($resource) {
        var resourceUrl =  'api/terrenos/:id';

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

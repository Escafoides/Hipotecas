(function() {
    'use strict';
    angular
        .module('hipotecasApp')
        .factory('ServiciosTerreno', ServiciosTerreno);

    ServiciosTerreno.$inject = ['$resource'];

    function ServiciosTerreno ($resource) {
        var resourceUrl =  'api/servicios-terrenos/:id';

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

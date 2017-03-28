(function() {
    'use strict';
    angular
        .module('hipotecasApp')
        .factory('CaracteristicasTerreno', CaracteristicasTerreno);

    CaracteristicasTerreno.$inject = ['$resource'];

    function CaracteristicasTerreno ($resource) {
        var resourceUrl =  'api/caracteristicas-terrenos/:id';

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

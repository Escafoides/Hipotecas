(function() {
    'use strict';
    angular
        .module('hipotecasApp')
        .factory('LocalizacionesTerreno', LocalizacionesTerreno);

    LocalizacionesTerreno.$inject = ['$resource'];

    function LocalizacionesTerreno ($resource) {
        var resourceUrl =  'api/localizaciones-terrenos/:id';

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

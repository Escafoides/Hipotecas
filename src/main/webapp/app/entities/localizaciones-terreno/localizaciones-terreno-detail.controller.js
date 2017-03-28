(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('LocalizacionesTerrenoDetailController', LocalizacionesTerrenoDetailController);

    LocalizacionesTerrenoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LocalizacionesTerreno'];

    function LocalizacionesTerrenoDetailController($scope, $rootScope, $stateParams, previousState, entity, LocalizacionesTerreno) {
        var vm = this;

        vm.localizacionesTerreno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hipotecasApp:localizacionesTerrenoUpdate', function(event, result) {
            vm.localizacionesTerreno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

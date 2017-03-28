(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('TerrenoDetailController', TerrenoDetailController);

    TerrenoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Terreno', 'CaracteristicasTerreno', 'ServiciosTerreno', 'LocalizacionesTerreno'];

    function TerrenoDetailController($scope, $rootScope, $stateParams, previousState, entity, Terreno, CaracteristicasTerreno, ServiciosTerreno, LocalizacionesTerreno) {
        var vm = this;

        vm.terreno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hipotecasApp:terrenoUpdate', function(event, result) {
            vm.terreno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

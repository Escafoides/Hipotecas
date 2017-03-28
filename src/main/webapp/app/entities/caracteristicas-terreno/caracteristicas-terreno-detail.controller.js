(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('CaracteristicasTerrenoDetailController', CaracteristicasTerrenoDetailController);

    CaracteristicasTerrenoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CaracteristicasTerreno'];

    function CaracteristicasTerrenoDetailController($scope, $rootScope, $stateParams, previousState, entity, CaracteristicasTerreno) {
        var vm = this;

        vm.caracteristicasTerreno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hipotecasApp:caracteristicasTerrenoUpdate', function(event, result) {
            vm.caracteristicasTerreno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('ServiciosTerrenoDetailController', ServiciosTerrenoDetailController);

    ServiciosTerrenoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ServiciosTerreno'];

    function ServiciosTerrenoDetailController($scope, $rootScope, $stateParams, previousState, entity, ServiciosTerreno) {
        var vm = this;

        vm.serviciosTerreno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hipotecasApp:serviciosTerrenoUpdate', function(event, result) {
            vm.serviciosTerreno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

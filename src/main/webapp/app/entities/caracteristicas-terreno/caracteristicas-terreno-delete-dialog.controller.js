(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('CaracteristicasTerrenoDeleteController',CaracteristicasTerrenoDeleteController);

    CaracteristicasTerrenoDeleteController.$inject = ['$uibModalInstance', 'entity', 'CaracteristicasTerreno'];

    function CaracteristicasTerrenoDeleteController($uibModalInstance, entity, CaracteristicasTerreno) {
        var vm = this;

        vm.caracteristicasTerreno = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CaracteristicasTerreno.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

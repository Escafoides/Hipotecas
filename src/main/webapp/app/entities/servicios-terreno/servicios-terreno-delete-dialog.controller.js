(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('ServiciosTerrenoDeleteController',ServiciosTerrenoDeleteController);

    ServiciosTerrenoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ServiciosTerreno'];

    function ServiciosTerrenoDeleteController($uibModalInstance, entity, ServiciosTerreno) {
        var vm = this;

        vm.serviciosTerreno = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ServiciosTerreno.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

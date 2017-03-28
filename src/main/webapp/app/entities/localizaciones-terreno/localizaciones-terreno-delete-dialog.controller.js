(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('LocalizacionesTerrenoDeleteController',LocalizacionesTerrenoDeleteController);

    LocalizacionesTerrenoDeleteController.$inject = ['$uibModalInstance', 'entity', 'LocalizacionesTerreno'];

    function LocalizacionesTerrenoDeleteController($uibModalInstance, entity, LocalizacionesTerreno) {
        var vm = this;

        vm.localizacionesTerreno = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LocalizacionesTerreno.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

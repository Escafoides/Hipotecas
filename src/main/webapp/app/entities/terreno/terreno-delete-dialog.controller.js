(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('TerrenoDeleteController',TerrenoDeleteController);

    TerrenoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Terreno'];

    function TerrenoDeleteController($uibModalInstance, entity, Terreno) {
        var vm = this;

        vm.terreno = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Terreno.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

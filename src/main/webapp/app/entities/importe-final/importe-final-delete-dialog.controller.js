(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('ImporteFinalDeleteController',ImporteFinalDeleteController);

    ImporteFinalDeleteController.$inject = ['$uibModalInstance', 'entity', 'ImporteFinal'];

    function ImporteFinalDeleteController($uibModalInstance, entity, ImporteFinal) {
        var vm = this;

        vm.importeFinal = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ImporteFinal.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

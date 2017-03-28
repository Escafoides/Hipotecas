(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('ImporteFinalDialogController', ImporteFinalDialogController);

    ImporteFinalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ImporteFinal', 'Terreno'];

    function ImporteFinalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ImporteFinal, Terreno) {
        var vm = this;

        vm.importeFinal = entity;
        vm.clear = clear;
        vm.save = save;
        vm.terrenos = Terreno.query({filter: 'importefinal-is-null'});
        $q.all([vm.importeFinal.$promise, vm.terrenos.$promise]).then(function() {
            if (!vm.importeFinal.terreno || !vm.importeFinal.terreno.id) {
                return $q.reject();
            }
            return Terreno.get({id : vm.importeFinal.terreno.id}).$promise;
        }).then(function(terreno) {
            vm.terrenos.push(terreno);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.importeFinal.id !== null) {
                ImporteFinal.update(vm.importeFinal, onSaveSuccess, onSaveError);
            } else {
                ImporteFinal.save(vm.importeFinal, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hipotecasApp:importeFinalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

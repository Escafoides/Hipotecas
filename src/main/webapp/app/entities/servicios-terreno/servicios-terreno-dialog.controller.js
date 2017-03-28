(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('ServiciosTerrenoDialogController', ServiciosTerrenoDialogController);

    ServiciosTerrenoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ServiciosTerreno'];

    function ServiciosTerrenoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ServiciosTerreno) {
        var vm = this;

        vm.serviciosTerreno = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.serviciosTerreno.id !== null) {
                ServiciosTerreno.update(vm.serviciosTerreno, onSaveSuccess, onSaveError);
            } else {
                ServiciosTerreno.save(vm.serviciosTerreno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hipotecasApp:serviciosTerrenoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

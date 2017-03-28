(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('CaracteristicasTerrenoDialogController', CaracteristicasTerrenoDialogController);

    CaracteristicasTerrenoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CaracteristicasTerreno'];

    function CaracteristicasTerrenoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CaracteristicasTerreno) {
        var vm = this;

        vm.caracteristicasTerreno = entity;
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
            if (vm.caracteristicasTerreno.id !== null) {
                CaracteristicasTerreno.update(vm.caracteristicasTerreno, onSaveSuccess, onSaveError);
            } else {
                CaracteristicasTerreno.save(vm.caracteristicasTerreno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hipotecasApp:caracteristicasTerrenoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

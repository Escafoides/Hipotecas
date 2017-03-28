(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('LocalizacionesTerrenoDialogController', LocalizacionesTerrenoDialogController);

    LocalizacionesTerrenoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LocalizacionesTerreno'];

    function LocalizacionesTerrenoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LocalizacionesTerreno) {
        var vm = this;

        vm.localizacionesTerreno = entity;
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
            if (vm.localizacionesTerreno.id !== null) {
                LocalizacionesTerreno.update(vm.localizacionesTerreno, onSaveSuccess, onSaveError);
            } else {
                LocalizacionesTerreno.save(vm.localizacionesTerreno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hipotecasApp:localizacionesTerrenoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

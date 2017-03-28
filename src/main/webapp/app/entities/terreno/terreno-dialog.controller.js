(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('TerrenoDialogController', TerrenoDialogController);

    TerrenoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Terreno', 'CaracteristicasTerreno', 'ServiciosTerreno', 'LocalizacionesTerreno'];

    function TerrenoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Terreno, CaracteristicasTerreno, ServiciosTerreno, LocalizacionesTerreno) {
        var vm = this;

        vm.terreno = entity;
        vm.clear = clear;
        vm.save = save;
        vm.caracteristicasterrenos = CaracteristicasTerreno.query({filter: 'terreno-is-null'});
        $q.all([vm.terreno.$promise, vm.caracteristicasterrenos.$promise]).then(function() {
            if (!vm.terreno.caracteristicasTerreno || !vm.terreno.caracteristicasTerreno.id) {
                return $q.reject();
            }
            return CaracteristicasTerreno.get({id : vm.terreno.caracteristicasTerreno.id}).$promise;
        }).then(function(caracteristicasTerreno) {
            vm.caracteristicasterrenos.push(caracteristicasTerreno);
        });
        vm.serviciosterrenos = ServiciosTerreno.query({filter: 'terreno-is-null'});
        $q.all([vm.terreno.$promise, vm.serviciosterrenos.$promise]).then(function() {
            if (!vm.terreno.serviciosTerreno || !vm.terreno.serviciosTerreno.id) {
                return $q.reject();
            }
            return ServiciosTerreno.get({id : vm.terreno.serviciosTerreno.id}).$promise;
        }).then(function(serviciosTerreno) {
            vm.serviciosterrenos.push(serviciosTerreno);
        });
        vm.localizacionesterrenos = LocalizacionesTerreno.query({filter: 'terreno-is-null'});
        $q.all([vm.terreno.$promise, vm.localizacionesterrenos.$promise]).then(function() {
            if (!vm.terreno.localizacionesTerreno || !vm.terreno.localizacionesTerreno.id) {
                return $q.reject();
            }
            return LocalizacionesTerreno.get({id : vm.terreno.localizacionesTerreno.id}).$promise;
        }).then(function(localizacionesTerreno) {
            vm.localizacionesterrenos.push(localizacionesTerreno);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.terreno.id !== null) {
                Terreno.update(vm.terreno, onSaveSuccess, onSaveError);
            } else {
                Terreno.save(vm.terreno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hipotecasApp:terrenoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

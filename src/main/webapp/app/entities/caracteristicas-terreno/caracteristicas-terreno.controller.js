(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('CaracteristicasTerrenoController', CaracteristicasTerrenoController);

    CaracteristicasTerrenoController.$inject = ['CaracteristicasTerreno'];

    function CaracteristicasTerrenoController(CaracteristicasTerreno) {

        var vm = this;

        vm.caracteristicasTerrenos = [];

        loadAll();

        function loadAll() {
            CaracteristicasTerreno.query(function(result) {
                vm.caracteristicasTerrenos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

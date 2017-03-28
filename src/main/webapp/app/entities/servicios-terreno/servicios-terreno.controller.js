(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('ServiciosTerrenoController', ServiciosTerrenoController);

    ServiciosTerrenoController.$inject = ['ServiciosTerreno'];

    function ServiciosTerrenoController(ServiciosTerreno) {

        var vm = this;

        vm.serviciosTerrenos = [];

        loadAll();

        function loadAll() {
            ServiciosTerreno.query(function(result) {
                vm.serviciosTerrenos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

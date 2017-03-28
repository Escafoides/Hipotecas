(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('LocalizacionesTerrenoController', LocalizacionesTerrenoController);

    LocalizacionesTerrenoController.$inject = ['LocalizacionesTerreno'];

    function LocalizacionesTerrenoController(LocalizacionesTerreno) {

        var vm = this;

        vm.localizacionesTerrenos = [];

        loadAll();

        function loadAll() {
            LocalizacionesTerreno.query(function(result) {
                vm.localizacionesTerrenos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

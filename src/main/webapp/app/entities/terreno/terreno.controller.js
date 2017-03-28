(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('TerrenoController', TerrenoController);

    TerrenoController.$inject = ['Terreno'];

    function TerrenoController(Terreno) {

        var vm = this;

        vm.terrenos = [];

        loadAll();

        function loadAll() {
            Terreno.query(function(result) {
                vm.terrenos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('ImporteFinalController', ImporteFinalController);

    ImporteFinalController.$inject = ['ImporteFinal'];

    function ImporteFinalController(ImporteFinal) {

        var vm = this;

        vm.importeFinals = [];

        loadAll();

        function loadAll() {
            ImporteFinal.query(function(result) {
                vm.importeFinals = result;
                vm.searchQuery = null;
            });
        }
    }
})();

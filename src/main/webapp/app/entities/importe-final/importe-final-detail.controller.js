(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .controller('ImporteFinalDetailController', ImporteFinalDetailController);

    ImporteFinalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ImporteFinal', 'Terreno'];

    function ImporteFinalDetailController($scope, $rootScope, $stateParams, previousState, entity, ImporteFinal, Terreno) {
        var vm = this;

        vm.importeFinal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hipotecasApp:importeFinalUpdate', function(event, result) {
            vm.importeFinal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

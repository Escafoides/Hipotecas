(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('caracteristicas-terreno', {
            parent: 'entity',
            url: '/caracteristicas-terreno',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CaracteristicasTerrenos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/caracteristicas-terreno/caracteristicas-terrenos.html',
                    controller: 'CaracteristicasTerrenoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('caracteristicas-terreno-detail', {
            parent: 'caracteristicas-terreno',
            url: '/caracteristicas-terreno/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CaracteristicasTerreno'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/caracteristicas-terreno/caracteristicas-terreno-detail.html',
                    controller: 'CaracteristicasTerrenoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CaracteristicasTerreno', function($stateParams, CaracteristicasTerreno) {
                    return CaracteristicasTerreno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'caracteristicas-terreno',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('caracteristicas-terreno-detail.edit', {
            parent: 'caracteristicas-terreno-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/caracteristicas-terreno/caracteristicas-terreno-dialog.html',
                    controller: 'CaracteristicasTerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CaracteristicasTerreno', function(CaracteristicasTerreno) {
                            return CaracteristicasTerreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('caracteristicas-terreno.new', {
            parent: 'caracteristicas-terreno',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/caracteristicas-terreno/caracteristicas-terreno-dialog.html',
                    controller: 'CaracteristicasTerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                superficie: null,
                                tipo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('caracteristicas-terreno', null, { reload: 'caracteristicas-terreno' });
                }, function() {
                    $state.go('caracteristicas-terreno');
                });
            }]
        })
        .state('caracteristicas-terreno.edit', {
            parent: 'caracteristicas-terreno',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/caracteristicas-terreno/caracteristicas-terreno-dialog.html',
                    controller: 'CaracteristicasTerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CaracteristicasTerreno', function(CaracteristicasTerreno) {
                            return CaracteristicasTerreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('caracteristicas-terreno', null, { reload: 'caracteristicas-terreno' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('caracteristicas-terreno.delete', {
            parent: 'caracteristicas-terreno',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/caracteristicas-terreno/caracteristicas-terreno-delete-dialog.html',
                    controller: 'CaracteristicasTerrenoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CaracteristicasTerreno', function(CaracteristicasTerreno) {
                            return CaracteristicasTerreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('caracteristicas-terreno', null, { reload: 'caracteristicas-terreno' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('servicios-terreno', {
            parent: 'entity',
            url: '/servicios-terreno',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ServiciosTerrenos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/servicios-terreno/servicios-terrenos.html',
                    controller: 'ServiciosTerrenoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('servicios-terreno-detail', {
            parent: 'servicios-terreno',
            url: '/servicios-terreno/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ServiciosTerreno'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/servicios-terreno/servicios-terreno-detail.html',
                    controller: 'ServiciosTerrenoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ServiciosTerreno', function($stateParams, ServiciosTerreno) {
                    return ServiciosTerreno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'servicios-terreno',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('servicios-terreno-detail.edit', {
            parent: 'servicios-terreno-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/servicios-terreno/servicios-terreno-dialog.html',
                    controller: 'ServiciosTerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiciosTerreno', function(ServiciosTerreno) {
                            return ServiciosTerreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('servicios-terreno.new', {
            parent: 'servicios-terreno',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/servicios-terreno/servicios-terreno-dialog.html',
                    controller: 'ServiciosTerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                agua: null,
                                luz: null,
                                telefono: null,
                                carretera: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('servicios-terreno', null, { reload: 'servicios-terreno' });
                }, function() {
                    $state.go('servicios-terreno');
                });
            }]
        })
        .state('servicios-terreno.edit', {
            parent: 'servicios-terreno',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/servicios-terreno/servicios-terreno-dialog.html',
                    controller: 'ServiciosTerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiciosTerreno', function(ServiciosTerreno) {
                            return ServiciosTerreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('servicios-terreno', null, { reload: 'servicios-terreno' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('servicios-terreno.delete', {
            parent: 'servicios-terreno',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/servicios-terreno/servicios-terreno-delete-dialog.html',
                    controller: 'ServiciosTerrenoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ServiciosTerreno', function(ServiciosTerreno) {
                            return ServiciosTerreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('servicios-terreno', null, { reload: 'servicios-terreno' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

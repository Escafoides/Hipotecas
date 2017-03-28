(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('localizaciones-terreno', {
            parent: 'entity',
            url: '/localizaciones-terreno',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LocalizacionesTerrenos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/localizaciones-terreno/localizaciones-terrenos.html',
                    controller: 'LocalizacionesTerrenoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('localizaciones-terreno-detail', {
            parent: 'localizaciones-terreno',
            url: '/localizaciones-terreno/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LocalizacionesTerreno'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/localizaciones-terreno/localizaciones-terreno-detail.html',
                    controller: 'LocalizacionesTerrenoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LocalizacionesTerreno', function($stateParams, LocalizacionesTerreno) {
                    return LocalizacionesTerreno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'localizaciones-terreno',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('localizaciones-terreno-detail.edit', {
            parent: 'localizaciones-terreno-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localizaciones-terreno/localizaciones-terreno-dialog.html',
                    controller: 'LocalizacionesTerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocalizacionesTerreno', function(LocalizacionesTerreno) {
                            return LocalizacionesTerreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('localizaciones-terreno.new', {
            parent: 'localizaciones-terreno',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localizaciones-terreno/localizaciones-terreno-dialog.html',
                    controller: 'LocalizacionesTerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                latitude: null,
                                longitude: null,
                                pais: null,
                                comunidad: null,
                                provincia: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('localizaciones-terreno', null, { reload: 'localizaciones-terreno' });
                }, function() {
                    $state.go('localizaciones-terreno');
                });
            }]
        })
        .state('localizaciones-terreno.edit', {
            parent: 'localizaciones-terreno',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localizaciones-terreno/localizaciones-terreno-dialog.html',
                    controller: 'LocalizacionesTerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocalizacionesTerreno', function(LocalizacionesTerreno) {
                            return LocalizacionesTerreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('localizaciones-terreno', null, { reload: 'localizaciones-terreno' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('localizaciones-terreno.delete', {
            parent: 'localizaciones-terreno',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localizaciones-terreno/localizaciones-terreno-delete-dialog.html',
                    controller: 'LocalizacionesTerrenoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LocalizacionesTerreno', function(LocalizacionesTerreno) {
                            return LocalizacionesTerreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('localizaciones-terreno', null, { reload: 'localizaciones-terreno' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

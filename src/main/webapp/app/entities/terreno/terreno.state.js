(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('terreno', {
            parent: 'entity',
            url: '/terreno',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Terrenos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/terreno/terrenos.html',
                    controller: 'TerrenoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('terreno-detail', {
            parent: 'terreno',
            url: '/terreno/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Terreno'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/terreno/terreno-detail.html',
                    controller: 'TerrenoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Terreno', function($stateParams, Terreno) {
                    return Terreno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'terreno',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('terreno-detail.edit', {
            parent: 'terreno-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/terreno/terreno-dialog.html',
                    controller: 'TerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Terreno', function(Terreno) {
                            return Terreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('terreno.new', {
            parent: 'terreno',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/terreno/terreno-dialog.html',
                    controller: 'TerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                precio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('terreno', null, { reload: 'terreno' });
                }, function() {
                    $state.go('terreno');
                });
            }]
        })
        .state('terreno.edit', {
            parent: 'terreno',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/terreno/terreno-dialog.html',
                    controller: 'TerrenoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Terreno', function(Terreno) {
                            return Terreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('terreno', null, { reload: 'terreno' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('terreno.delete', {
            parent: 'terreno',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/terreno/terreno-delete-dialog.html',
                    controller: 'TerrenoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Terreno', function(Terreno) {
                            return Terreno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('terreno', null, { reload: 'terreno' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

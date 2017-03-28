(function() {
    'use strict';

    angular
        .module('hipotecasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('importe-final', {
            parent: 'entity',
            url: '/importe-final',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ImporteFinals'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/importe-final/importe-finals.html',
                    controller: 'ImporteFinalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('importe-final-detail', {
            parent: 'importe-final',
            url: '/importe-final/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ImporteFinal'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/importe-final/importe-final-detail.html',
                    controller: 'ImporteFinalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ImporteFinal', function($stateParams, ImporteFinal) {
                    return ImporteFinal.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'importe-final',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('importe-final-detail.edit', {
            parent: 'importe-final-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/importe-final/importe-final-dialog.html',
                    controller: 'ImporteFinalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ImporteFinal', function(ImporteFinal) {
                            return ImporteFinal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('importe-final.new', {
            parent: 'importe-final',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/importe-final/importe-final-dialog.html',
                    controller: 'ImporteFinalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                intervalo: null,
                                capitalInicial: null,
                                interesAnual: null,
                                importeAnual: null,
                                importeMes: null,
                                importeTotal: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('importe-final', null, { reload: 'importe-final' });
                }, function() {
                    $state.go('importe-final');
                });
            }]
        })
        .state('importe-final.edit', {
            parent: 'importe-final',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/importe-final/importe-final-dialog.html',
                    controller: 'ImporteFinalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ImporteFinal', function(ImporteFinal) {
                            return ImporteFinal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('importe-final', null, { reload: 'importe-final' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('importe-final.delete', {
            parent: 'importe-final',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/importe-final/importe-final-delete-dialog.html',
                    controller: 'ImporteFinalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ImporteFinal', function(ImporteFinal) {
                            return ImporteFinal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('importe-final', null, { reload: 'importe-final' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

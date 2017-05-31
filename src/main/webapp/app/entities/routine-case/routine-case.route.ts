import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RoutineCaseComponent } from './routine-case.component';
import { RoutineCaseDetailComponent } from './routine-case-detail.component';
import { RoutineCasePopupComponent } from './routine-case-dialog.component';
import { RoutineCaseDeletePopupComponent } from './routine-case-delete-dialog.component';

import { Principal } from '../../shared';

export const routineCaseRoute: Routes = [
    {
        path: 'routine-case',
        component: RoutineCaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.routineCase.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'routine-case/:id',
        component: RoutineCaseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.routineCase.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const routineCasePopupRoute: Routes = [
    {
        path: 'routine-case-new',
        component: RoutineCasePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.routineCase.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'routine-case/:id/edit',
        component: RoutineCasePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.routineCase.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'routine-case/:id/delete',
        component: RoutineCaseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.routineCase.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

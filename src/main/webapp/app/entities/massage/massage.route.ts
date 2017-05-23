import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MassageComponent } from './massage.component';
import { MassageDetailComponent } from './massage-detail.component';
import { MassagePopupComponent } from './massage-dialog.component';
import { MassageDeletePopupComponent } from './massage-delete-dialog.component';

import { Principal } from '../../shared';

export const massageRoute: Routes = [
    {
        path: 'massage',
        component: MassageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.massage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'massage/:id',
        component: MassageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.massage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const massagePopupRoute: Routes = [
    {
        path: 'massage-new',
        component: MassagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.massage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'massage/:id/edit',
        component: MassagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.massage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'massage/:id/delete',
        component: MassageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.massage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

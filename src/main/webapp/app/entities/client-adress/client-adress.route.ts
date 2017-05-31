import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClientAdressComponent } from './client-adress.component';
import { ClientAdressDetailComponent } from './client-adress-detail.component';
import { ClientAdressPopupComponent } from './client-adress-dialog.component';
import { ClientAdressDeletePopupComponent } from './client-adress-delete-dialog.component';

import { Principal } from '../../shared';

export const clientAdressRoute: Routes = [
    {
        path: 'client-adress',
        component: ClientAdressComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientAdress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'client-adress/:id',
        component: ClientAdressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientAdress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientAdressPopupRoute: Routes = [
    {
        path: 'client-adress-new',
        component: ClientAdressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientAdress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-adress/:id/edit',
        component: ClientAdressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientAdress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-adress/:id/delete',
        component: ClientAdressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientAdress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

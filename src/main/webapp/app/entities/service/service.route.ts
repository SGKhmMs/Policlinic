import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ServiceComponent } from './service.component';
import { ServiceDetailComponent } from './service-detail.component';
import { ServicePopupComponent } from './service-dialog.component';
import { ServiceDeletePopupComponent } from './service-delete-dialog.component';

import { Principal } from '../../shared';

export const serviceRoute: Routes = [
    {
        path: 'service',
        component: ServiceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.service.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'service/:id',
        component: ServiceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.service.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const servicePopupRoute: Routes = [
    {
        path: 'service-new',
        component: ServicePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.service.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service/:id/edit',
        component: ServicePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.service.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service/:id/delete',
        component: ServiceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.service.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

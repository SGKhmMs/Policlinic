import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AttechmentComponent } from './attechment.component';
import { AttechmentDetailComponent } from './attechment-detail.component';
import { AttechmentPopupComponent } from './attechment-dialog.component';
import { AttechmentDeletePopupComponent } from './attechment-delete-dialog.component';

import { Principal } from '../../shared';

export const attechmentRoute: Routes = [
    {
        path: 'attechment',
        component: AttechmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.attechment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'attechment/:id',
        component: AttechmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.attechment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attechmentPopupRoute: Routes = [
    {
        path: 'attechment-new',
        component: AttechmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.attechment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attechment/:id/edit',
        component: AttechmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.attechment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attechment/:id/delete',
        component: AttechmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.attechment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

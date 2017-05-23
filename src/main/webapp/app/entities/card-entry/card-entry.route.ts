import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CardEntryComponent } from './card-entry.component';
import { CardEntryDetailComponent } from './card-entry-detail.component';
import { CardEntryPopupComponent } from './card-entry-dialog.component';
import { CardEntryDeletePopupComponent } from './card-entry-delete-dialog.component';

import { Principal } from '../../shared';

export const cardEntryRoute: Routes = [
    {
        path: 'card-entry',
        component: CardEntryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.cardEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'card-entry/:id',
        component: CardEntryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.cardEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cardEntryPopupRoute: Routes = [
    {
        path: 'card-entry-new',
        component: CardEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.cardEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'card-entry/:id/edit',
        component: CardEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.cardEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'card-entry/:id/delete',
        component: CardEntryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.cardEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

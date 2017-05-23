import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ChatComponent } from './chat.component';
import { ChatDetailComponent } from './chat-detail.component';
import { ChatPopupComponent } from './chat-dialog.component';
import { ChatDeletePopupComponent } from './chat-delete-dialog.component';

import { Principal } from '../../shared';

export const chatRoute: Routes = [
    {
        path: 'chat',
        component: ChatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.chat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'chat/:id',
        component: ChatDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.chat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chatPopupRoute: Routes = [
    {
        path: 'chat-new',
        component: ChatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.chat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chat/:id/edit',
        component: ChatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.chat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chat/:id/delete',
        component: ChatDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.chat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

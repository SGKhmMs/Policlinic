import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MessageAttachmentComponent } from './message-attachment.component';
import { MessageAttachmentDetailComponent } from './message-attachment-detail.component';
import { MessageAttachmentPopupComponent } from './message-attachment-dialog.component';
import { MessageAttachmentDeletePopupComponent } from './message-attachment-delete-dialog.component';

import { Principal } from '../../shared';

export const messageAttachmentRoute: Routes = [
    {
        path: 'message-attachment',
        component: MessageAttachmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.messageAttachment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'message-attachment/:id',
        component: MessageAttachmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.messageAttachment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const messageAttachmentPopupRoute: Routes = [
    {
        path: 'message-attachment-new',
        component: MessageAttachmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.messageAttachment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message-attachment/:id/edit',
        component: MessageAttachmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.messageAttachment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message-attachment/:id/delete',
        component: MessageAttachmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.messageAttachment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

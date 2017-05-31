import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClientProfile } from './client-profile.model';
import { ClientProfilePopupService } from './client-profile-popup.service';
import { ClientProfileService } from './client-profile.service';
import { Client, ClientService } from '../client';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-client-profile-dialog',
    templateUrl: './client-profile-dialog.component.html'
})
export class ClientProfileDialogComponent implements OnInit {

    clientProfile: ClientProfile;
    authorities: any[];
    isSaving: boolean;

    clients: Client[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clientProfileService: ClientProfileService,
        private clientService: ClientService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientService
            .query({filter: 'clientprofile-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.clientProfile.client || !this.clientProfile.client.id) {
                    this.clients = res.json;
                } else {
                    this.clientService
                        .find(this.clientProfile.client.id)
                        .subscribe((subRes: Client) => {
                            this.clients = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clientProfile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientProfileService.update(this.clientProfile));
        } else {
            this.subscribeToSaveResponse(
                this.clientProfileService.create(this.clientProfile));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClientProfile>) {
        result.subscribe((res: ClientProfile) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClientProfile) {
        this.eventManager.broadcast({ name: 'clientProfileListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackClientById(index: number, item: Client) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-client-profile-popup',
    template: ''
})
export class ClientProfilePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientProfilePopupService: ClientProfilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientProfilePopupService
                    .open(ClientProfileDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientProfilePopupService
                    .open(ClientProfileDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClientAdress } from './client-adress.model';
import { ClientAdressPopupService } from './client-adress-popup.service';
import { ClientAdressService } from './client-adress.service';
import { Client, ClientService } from '../client';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-client-adress-dialog',
    templateUrl: './client-adress-dialog.component.html'
})
export class ClientAdressDialogComponent implements OnInit {

    clientAdress: ClientAdress;
    authorities: any[];
    isSaving: boolean;

    clients: Client[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clientAdressService: ClientAdressService,
        private clientService: ClientService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientService
            .query({filter: 'clientadress-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.clientAdress.client || !this.clientAdress.client.id) {
                    this.clients = res.json;
                } else {
                    this.clientService
                        .find(this.clientAdress.client.id)
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
        if (this.clientAdress.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientAdressService.update(this.clientAdress));
        } else {
            this.subscribeToSaveResponse(
                this.clientAdressService.create(this.clientAdress));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClientAdress>) {
        result.subscribe((res: ClientAdress) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClientAdress) {
        this.eventManager.broadcast({ name: 'clientAdressListModification', content: 'OK'});
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
    selector: 'jhi-client-adress-popup',
    template: ''
})
export class ClientAdressPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientAdressPopupService: ClientAdressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientAdressPopupService
                    .open(ClientAdressDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientAdressPopupService
                    .open(ClientAdressDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

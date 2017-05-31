import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Chat } from './chat.model';
import { ChatPopupService } from './chat-popup.service';
import { ChatService } from './chat.service';
import { Client, ClientService } from '../client';
import { Doctor, DoctorService } from '../doctor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-chat-dialog',
    templateUrl: './chat-dialog.component.html'
})
export class ChatDialogComponent implements OnInit {

    chat: Chat;
    authorities: any[];
    isSaving: boolean;

    clients: Client[];

    doctors: Doctor[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private chatService: ChatService,
        private clientService: ClientService,
        private doctorService: DoctorService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientService
            .query({filter: 'chat-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.chat.client || !this.chat.client.id) {
                    this.clients = res.json;
                } else {
                    this.clientService
                        .find(this.chat.client.id)
                        .subscribe((subRes: Client) => {
                            this.clients = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.doctorService
            .query({filter: 'chat-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.chat.doctor || !this.chat.doctor.id) {
                    this.doctors = res.json;
                } else {
                    this.doctorService
                        .find(this.chat.doctor.id)
                        .subscribe((subRes: Doctor) => {
                            this.doctors = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.chat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.chatService.update(this.chat));
        } else {
            this.subscribeToSaveResponse(
                this.chatService.create(this.chat));
        }
    }

    private subscribeToSaveResponse(result: Observable<Chat>) {
        result.subscribe((res: Chat) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Chat) {
        this.eventManager.broadcast({ name: 'chatListModification', content: 'OK'});
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

    trackDoctorById(index: number, item: Doctor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-chat-popup',
    template: ''
})
export class ChatPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chatPopupService: ChatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.chatPopupService
                    .open(ChatDialogComponent, params['id']);
            } else {
                this.modalRef = this.chatPopupService
                    .open(ChatDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

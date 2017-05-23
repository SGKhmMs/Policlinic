import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Chat } from './chat.model';
import { ChatPopupService } from './chat-popup.service';
import { ChatService } from './chat.service';
import { Doctor, DoctorService } from '../doctor';
import { Client, ClientService } from '../client';

@Component({
    selector: 'jhi-chat-dialog',
    templateUrl: './chat-dialog.component.html'
})
export class ChatDialogComponent implements OnInit {

    chat: Chat;
    authorities: any[];
    isSaving: boolean;

    doctors: Doctor[];

    clients: Client[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private chatService: ChatService,
        private doctorService: DoctorService,
        private clientService: ClientService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.doctorService.query().subscribe(
            (res: Response) => { this.doctors = res.json(); }, (res: Response) => this.onError(res.json()));
        this.clientService.query().subscribe(
            (res: Response) => { this.clients = res.json(); }, (res: Response) => this.onError(res.json()));
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

    trackDoctorById(index: number, item: Doctor) {
        return item.id;
    }

    trackClientById(index: number, item: Client) {
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

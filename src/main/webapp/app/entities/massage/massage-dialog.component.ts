import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Massage } from './massage.model';
import { MassagePopupService } from './massage-popup.service';
import { MassageService } from './massage.service';
import { Chat, ChatService } from '../chat';

@Component({
    selector: 'jhi-massage-dialog',
    templateUrl: './massage-dialog.component.html'
})
export class MassageDialogComponent implements OnInit {

    massage: Massage;
    authorities: any[];
    isSaving: boolean;

    chats: Chat[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private massageService: MassageService,
        private chatService: ChatService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.chatService.query().subscribe(
            (res: Response) => { this.chats = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.massage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.massageService.update(this.massage));
        } else {
            this.subscribeToSaveResponse(
                this.massageService.create(this.massage));
        }
    }

    private subscribeToSaveResponse(result: Observable<Massage>) {
        result.subscribe((res: Massage) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Massage) {
        this.eventManager.broadcast({ name: 'massageListModification', content: 'OK'});
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

    trackChatById(index: number, item: Chat) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-massage-popup',
    template: ''
})
export class MassagePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private massagePopupService: MassagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.massagePopupService
                    .open(MassageDialogComponent, params['id']);
            } else {
                this.modalRef = this.massagePopupService
                    .open(MassageDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

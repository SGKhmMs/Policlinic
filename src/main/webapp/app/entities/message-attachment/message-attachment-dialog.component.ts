import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { MessageAttachment } from './message-attachment.model';
import { MessageAttachmentPopupService } from './message-attachment-popup.service';
import { MessageAttachmentService } from './message-attachment.service';
import { Message, MessageService } from '../message';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-message-attachment-dialog',
    templateUrl: './message-attachment-dialog.component.html'
})
export class MessageAttachmentDialogComponent implements OnInit {

    messageAttachment: MessageAttachment;
    authorities: any[];
    isSaving: boolean;

    messages: Message[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private messageAttachmentService: MessageAttachmentService,
        private messageService: MessageService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.messageService.query()
            .subscribe((res: ResponseWrapper) => { this.messages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, messageAttachment, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                messageAttachment[field] = base64Data;
                messageAttachment[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.messageAttachment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.messageAttachmentService.update(this.messageAttachment));
        } else {
            this.subscribeToSaveResponse(
                this.messageAttachmentService.create(this.messageAttachment));
        }
    }

    private subscribeToSaveResponse(result: Observable<MessageAttachment>) {
        result.subscribe((res: MessageAttachment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MessageAttachment) {
        this.eventManager.broadcast({ name: 'messageAttachmentListModification', content: 'OK'});
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

    trackMessageById(index: number, item: Message) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-message-attachment-popup',
    template: ''
})
export class MessageAttachmentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private messageAttachmentPopupService: MessageAttachmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.messageAttachmentPopupService
                    .open(MessageAttachmentDialogComponent, params['id']);
            } else {
                this.modalRef = this.messageAttachmentPopupService
                    .open(MessageAttachmentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

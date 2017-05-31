import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { EntryAttachment } from './entry-attachment.model';
import { EntryAttachmentPopupService } from './entry-attachment-popup.service';
import { EntryAttachmentService } from './entry-attachment.service';
import { CardEntry, CardEntryService } from '../card-entry';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-entry-attachment-dialog',
    templateUrl: './entry-attachment-dialog.component.html'
})
export class EntryAttachmentDialogComponent implements OnInit {

    entryAttachment: EntryAttachment;
    authorities: any[];
    isSaving: boolean;

    cardentries: CardEntry[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private entryAttachmentService: EntryAttachmentService,
        private cardEntryService: CardEntryService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.cardEntryService.query()
            .subscribe((res: ResponseWrapper) => { this.cardentries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entryAttachment, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                entryAttachment[field] = base64Data;
                entryAttachment[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.entryAttachment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entryAttachmentService.update(this.entryAttachment));
        } else {
            this.subscribeToSaveResponse(
                this.entryAttachmentService.create(this.entryAttachment));
        }
    }

    private subscribeToSaveResponse(result: Observable<EntryAttachment>) {
        result.subscribe((res: EntryAttachment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EntryAttachment) {
        this.eventManager.broadcast({ name: 'entryAttachmentListModification', content: 'OK'});
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

    trackCardEntryById(index: number, item: CardEntry) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-entry-attachment-popup',
    template: ''
})
export class EntryAttachmentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entryAttachmentPopupService: EntryAttachmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.entryAttachmentPopupService
                    .open(EntryAttachmentDialogComponent, params['id']);
            } else {
                this.modalRef = this.entryAttachmentPopupService
                    .open(EntryAttachmentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

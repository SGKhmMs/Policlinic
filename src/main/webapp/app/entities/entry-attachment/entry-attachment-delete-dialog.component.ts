import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { EntryAttachment } from './entry-attachment.model';
import { EntryAttachmentPopupService } from './entry-attachment-popup.service';
import { EntryAttachmentService } from './entry-attachment.service';

@Component({
    selector: 'jhi-entry-attachment-delete-dialog',
    templateUrl: './entry-attachment-delete-dialog.component.html'
})
export class EntryAttachmentDeleteDialogComponent {

    entryAttachment: EntryAttachment;

    constructor(
        private entryAttachmentService: EntryAttachmentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entryAttachmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entryAttachmentListModification',
                content: 'Deleted an entryAttachment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entry-attachment-delete-popup',
    template: ''
})
export class EntryAttachmentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entryAttachmentPopupService: EntryAttachmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.entryAttachmentPopupService
                .open(EntryAttachmentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

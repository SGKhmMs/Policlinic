import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { MessageAttachment } from './message-attachment.model';
import { MessageAttachmentPopupService } from './message-attachment-popup.service';
import { MessageAttachmentService } from './message-attachment.service';

@Component({
    selector: 'jhi-message-attachment-delete-dialog',
    templateUrl: './message-attachment-delete-dialog.component.html'
})
export class MessageAttachmentDeleteDialogComponent {

    messageAttachment: MessageAttachment;

    constructor(
        private messageAttachmentService: MessageAttachmentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.messageAttachmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'messageAttachmentListModification',
                content: 'Deleted an messageAttachment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-message-attachment-delete-popup',
    template: ''
})
export class MessageAttachmentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private messageAttachmentPopupService: MessageAttachmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.messageAttachmentPopupService
                .open(MessageAttachmentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MessageAttachment } from './message-attachment.model';
import { MessageAttachmentService } from './message-attachment.service';
@Injectable()
export class MessageAttachmentPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private messageAttachmentService: MessageAttachmentService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.messageAttachmentService.find(id).subscribe((messageAttachment) => {
                this.messageAttachmentModalRef(component, messageAttachment);
            });
        } else {
            return this.messageAttachmentModalRef(component, new MessageAttachment());
        }
    }

    messageAttachmentModalRef(component: Component, messageAttachment: MessageAttachment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.messageAttachment = messageAttachment;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}

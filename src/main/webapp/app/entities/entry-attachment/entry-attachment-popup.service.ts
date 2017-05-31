import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EntryAttachment } from './entry-attachment.model';
import { EntryAttachmentService } from './entry-attachment.service';
@Injectable()
export class EntryAttachmentPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private entryAttachmentService: EntryAttachmentService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.entryAttachmentService.find(id).subscribe((entryAttachment) => {
                this.entryAttachmentModalRef(component, entryAttachment);
            });
        } else {
            return this.entryAttachmentModalRef(component, new EntryAttachment());
        }
    }

    entryAttachmentModalRef(component: Component, entryAttachment: EntryAttachment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.entryAttachment = entryAttachment;
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

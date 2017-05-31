import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClientProfile } from './client-profile.model';
import { ClientProfileService } from './client-profile.service';
@Injectable()
export class ClientProfilePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private clientProfileService: ClientProfileService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clientProfileService.find(id).subscribe((clientProfile) => {
                this.clientProfileModalRef(component, clientProfile);
            });
        } else {
            return this.clientProfileModalRef(component, new ClientProfile());
        }
    }

    clientProfileModalRef(component: Component, clientProfile: ClientProfile): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clientProfile = clientProfile;
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

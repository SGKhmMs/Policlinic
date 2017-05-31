import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClinicModerator } from './clinic-moderator.model';
import { ClinicModeratorService } from './clinic-moderator.service';
@Injectable()
export class ClinicModeratorPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private clinicModeratorService: ClinicModeratorService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clinicModeratorService.find(id).subscribe((clinicModerator) => {
                this.clinicModeratorModalRef(component, clinicModerator);
            });
        } else {
            return this.clinicModeratorModalRef(component, new ClinicModerator());
        }
    }

    clinicModeratorModalRef(component: Component, clinicModerator: ClinicModerator): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clinicModerator = clinicModerator;
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

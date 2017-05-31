import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClinicModeratorProfile } from './clinic-moderator-profile.model';
import { ClinicModeratorProfileService } from './clinic-moderator-profile.service';
@Injectable()
export class ClinicModeratorProfilePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private clinicModeratorProfileService: ClinicModeratorProfileService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clinicModeratorProfileService.find(id).subscribe((clinicModeratorProfile) => {
                this.clinicModeratorProfileModalRef(component, clinicModeratorProfile);
            });
        } else {
            return this.clinicModeratorProfileModalRef(component, new ClinicModeratorProfile());
        }
    }

    clinicModeratorProfileModalRef(component: Component, clinicModeratorProfile: ClinicModeratorProfile): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clinicModeratorProfile = clinicModeratorProfile;
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

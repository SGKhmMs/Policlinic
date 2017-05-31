import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DoctorProfile } from './doctor-profile.model';
import { DoctorProfileService } from './doctor-profile.service';
@Injectable()
export class DoctorProfilePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private doctorProfileService: DoctorProfileService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.doctorProfileService.find(id).subscribe((doctorProfile) => {
                this.doctorProfileModalRef(component, doctorProfile);
            });
        } else {
            return this.doctorProfileModalRef(component, new DoctorProfile());
        }
    }

    doctorProfileModalRef(component: Component, doctorProfile: DoctorProfile): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.doctorProfile = doctorProfile;
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

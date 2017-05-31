import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClinicDoctor } from './clinic-doctor.model';
import { ClinicDoctorService } from './clinic-doctor.service';
@Injectable()
export class ClinicDoctorPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private clinicDoctorService: ClinicDoctorService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clinicDoctorService.find(id).subscribe((clinicDoctor) => {
                this.clinicDoctorModalRef(component, clinicDoctor);
            });
        } else {
            return this.clinicDoctorModalRef(component, new ClinicDoctor());
        }
    }

    clinicDoctorModalRef(component: Component, clinicDoctor: ClinicDoctor): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clinicDoctor = clinicDoctor;
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

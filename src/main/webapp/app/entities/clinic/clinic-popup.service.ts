import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Clinic } from './clinic.model';
import { ClinicService } from './clinic.service';
@Injectable()
export class ClinicPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private clinicService: ClinicService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clinicService.find(id).subscribe((clinic) => {
                clinic.workDatBegin = this.datePipe
                    .transform(clinic.workDatBegin, 'yyyy-MM-ddThh:mm');
                clinic.workDayEnd = this.datePipe
                    .transform(clinic.workDayEnd, 'yyyy-MM-ddThh:mm');
                this.clinicModalRef(component, clinic);
            });
        } else {
            return this.clinicModalRef(component, new Clinic());
        }
    }

    clinicModalRef(component: Component, clinic: Clinic): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clinic = clinic;
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

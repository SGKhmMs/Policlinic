import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AppointmentOperation } from './appointment-operation.model';
import { AppointmentOperationService } from './appointment-operation.service';
@Injectable()
export class AppointmentOperationPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private appointmentOperationService: AppointmentOperationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.appointmentOperationService.find(id).subscribe((appointmentOperation) => {
                this.appointmentOperationModalRef(component, appointmentOperation);
            });
        } else {
            return this.appointmentOperationModalRef(component, new AppointmentOperation());
        }
    }

    appointmentOperationModalRef(component: Component, appointmentOperation: AppointmentOperation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.appointmentOperation = appointmentOperation;
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

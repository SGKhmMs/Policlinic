import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ServiceOnAppointment } from './service-on-appointment.model';
import { ServiceOnAppointmentService } from './service-on-appointment.service';
@Injectable()
export class ServiceOnAppointmentPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private serviceOnAppointmentService: ServiceOnAppointmentService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.serviceOnAppointmentService.find(id).subscribe((serviceOnAppointment) => {
                this.serviceOnAppointmentModalRef(component, serviceOnAppointment);
            });
        } else {
            return this.serviceOnAppointmentModalRef(component, new ServiceOnAppointment());
        }
    }

    serviceOnAppointmentModalRef(component: Component, serviceOnAppointment: ServiceOnAppointment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.serviceOnAppointment = serviceOnAppointment;
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

import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DoctorAdress } from './doctor-adress.model';
import { DoctorAdressService } from './doctor-adress.service';
@Injectable()
export class DoctorAdressPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private doctorAdressService: DoctorAdressService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.doctorAdressService.find(id).subscribe((doctorAdress) => {
                this.doctorAdressModalRef(component, doctorAdress);
            });
        } else {
            return this.doctorAdressModalRef(component, new DoctorAdress());
        }
    }

    doctorAdressModalRef(component: Component, doctorAdress: DoctorAdress): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.doctorAdress = doctorAdress;
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

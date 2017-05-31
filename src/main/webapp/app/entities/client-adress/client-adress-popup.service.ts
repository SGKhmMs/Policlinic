import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClientAdress } from './client-adress.model';
import { ClientAdressService } from './client-adress.service';
@Injectable()
export class ClientAdressPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private clientAdressService: ClientAdressService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clientAdressService.find(id).subscribe((clientAdress) => {
                this.clientAdressModalRef(component, clientAdress);
            });
        } else {
            return this.clientAdressModalRef(component, new ClientAdress());
        }
    }

    clientAdressModalRef(component: Component, clientAdress: ClientAdress): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clientAdress = clientAdress;
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

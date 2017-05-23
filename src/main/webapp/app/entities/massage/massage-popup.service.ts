import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Massage } from './massage.model';
import { MassageService } from './massage.service';
@Injectable()
export class MassagePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private massageService: MassageService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.massageService.find(id).subscribe((massage) => {
                massage.timeOfSending = this.datePipe
                    .transform(massage.timeOfSending, 'yyyy-MM-ddThh:mm');
                this.massageModalRef(component, massage);
            });
        } else {
            return this.massageModalRef(component, new Massage());
        }
    }

    massageModalRef(component: Component, massage: Massage): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.massage = massage;
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

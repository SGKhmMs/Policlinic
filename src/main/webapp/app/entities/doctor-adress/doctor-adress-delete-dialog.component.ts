import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { DoctorAdress } from './doctor-adress.model';
import { DoctorAdressPopupService } from './doctor-adress-popup.service';
import { DoctorAdressService } from './doctor-adress.service';

@Component({
    selector: 'jhi-doctor-adress-delete-dialog',
    templateUrl: './doctor-adress-delete-dialog.component.html'
})
export class DoctorAdressDeleteDialogComponent {

    doctorAdress: DoctorAdress;

    constructor(
        private doctorAdressService: DoctorAdressService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.doctorAdressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'doctorAdressListModification',
                content: 'Deleted an doctorAdress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-doctor-adress-delete-popup',
    template: ''
})
export class DoctorAdressDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorAdressPopupService: DoctorAdressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.doctorAdressPopupService
                .open(DoctorAdressDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

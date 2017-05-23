import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ClinicDoctor } from './clinic-doctor.model';
import { ClinicDoctorPopupService } from './clinic-doctor-popup.service';
import { ClinicDoctorService } from './clinic-doctor.service';

@Component({
    selector: 'jhi-clinic-doctor-delete-dialog',
    templateUrl: './clinic-doctor-delete-dialog.component.html'
})
export class ClinicDoctorDeleteDialogComponent {

    clinicDoctor: ClinicDoctor;

    constructor(
        private clinicDoctorService: ClinicDoctorService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clinicDoctorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clinicDoctorListModification',
                content: 'Deleted an clinicDoctor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clinic-doctor-delete-popup',
    template: ''
})
export class ClinicDoctorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clinicDoctorPopupService: ClinicDoctorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clinicDoctorPopupService
                .open(ClinicDoctorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

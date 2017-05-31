import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Doctor } from './doctor.model';
import { DoctorPopupService } from './doctor-popup.service';
import { DoctorService } from './doctor.service';

@Component({
    selector: 'jhi-doctor-delete-dialog',
    templateUrl: './doctor-delete-dialog.component.html'
})
export class DoctorDeleteDialogComponent {

    doctor: Doctor;

    constructor(
        private doctorService: DoctorService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.doctorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'doctorListModification',
                content: 'Deleted an doctor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-doctor-delete-popup',
    template: ''
})
export class DoctorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorPopupService: DoctorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.doctorPopupService
                .open(DoctorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { DoctorSpecialty } from './doctor-specialty.model';
import { DoctorSpecialtyPopupService } from './doctor-specialty-popup.service';
import { DoctorSpecialtyService } from './doctor-specialty.service';

@Component({
    selector: 'jhi-doctor-specialty-delete-dialog',
    templateUrl: './doctor-specialty-delete-dialog.component.html'
})
export class DoctorSpecialtyDeleteDialogComponent {

    doctorSpecialty: DoctorSpecialty;

    constructor(
        private doctorSpecialtyService: DoctorSpecialtyService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.doctorSpecialtyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'doctorSpecialtyListModification',
                content: 'Deleted an doctorSpecialty'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-doctor-specialty-delete-popup',
    template: ''
})
export class DoctorSpecialtyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorSpecialtyPopupService: DoctorSpecialtyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.doctorSpecialtyPopupService
                .open(DoctorSpecialtyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

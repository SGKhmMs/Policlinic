import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Clinic } from './clinic.model';
import { ClinicPopupService } from './clinic-popup.service';
import { ClinicService } from './clinic.service';

@Component({
    selector: 'jhi-clinic-delete-dialog',
    templateUrl: './clinic-delete-dialog.component.html'
})
export class ClinicDeleteDialogComponent {

    clinic: Clinic;

    constructor(
        private clinicService: ClinicService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clinicService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clinicListModification',
                content: 'Deleted an clinic'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clinic-delete-popup',
    template: ''
})
export class ClinicDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clinicPopupService: ClinicPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clinicPopupService
                .open(ClinicDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

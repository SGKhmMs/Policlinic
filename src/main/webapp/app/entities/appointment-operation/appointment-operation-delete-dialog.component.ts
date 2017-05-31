import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AppointmentOperation } from './appointment-operation.model';
import { AppointmentOperationPopupService } from './appointment-operation-popup.service';
import { AppointmentOperationService } from './appointment-operation.service';

@Component({
    selector: 'jhi-appointment-operation-delete-dialog',
    templateUrl: './appointment-operation-delete-dialog.component.html'
})
export class AppointmentOperationDeleteDialogComponent {

    appointmentOperation: AppointmentOperation;

    constructor(
        private appointmentOperationService: AppointmentOperationService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appointmentOperationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'appointmentOperationListModification',
                content: 'Deleted an appointmentOperation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-appointment-operation-delete-popup',
    template: ''
})
export class AppointmentOperationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appointmentOperationPopupService: AppointmentOperationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.appointmentOperationPopupService
                .open(AppointmentOperationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

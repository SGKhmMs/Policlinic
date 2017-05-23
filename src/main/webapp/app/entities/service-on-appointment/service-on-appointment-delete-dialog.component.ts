import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ServiceOnAppointment } from './service-on-appointment.model';
import { ServiceOnAppointmentPopupService } from './service-on-appointment-popup.service';
import { ServiceOnAppointmentService } from './service-on-appointment.service';

@Component({
    selector: 'jhi-service-on-appointment-delete-dialog',
    templateUrl: './service-on-appointment-delete-dialog.component.html'
})
export class ServiceOnAppointmentDeleteDialogComponent {

    serviceOnAppointment: ServiceOnAppointment;

    constructor(
        private serviceOnAppointmentService: ServiceOnAppointmentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.serviceOnAppointmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'serviceOnAppointmentListModification',
                content: 'Deleted an serviceOnAppointment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-service-on-appointment-delete-popup',
    template: ''
})
export class ServiceOnAppointmentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private serviceOnAppointmentPopupService: ServiceOnAppointmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.serviceOnAppointmentPopupService
                .open(ServiceOnAppointmentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

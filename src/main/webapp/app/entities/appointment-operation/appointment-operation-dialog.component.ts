import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AppointmentOperation } from './appointment-operation.model';
import { AppointmentOperationPopupService } from './appointment-operation-popup.service';
import { AppointmentOperationService } from './appointment-operation.service';
import { Appointment, AppointmentService } from '../appointment';
import { Operation, OperationService } from '../operation';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-appointment-operation-dialog',
    templateUrl: './appointment-operation-dialog.component.html'
})
export class AppointmentOperationDialogComponent implements OnInit {

    appointmentOperation: AppointmentOperation;
    authorities: any[];
    isSaving: boolean;

    appointments: Appointment[];

    operations: Operation[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private appointmentOperationService: AppointmentOperationService,
        private appointmentService: AppointmentService,
        private operationService: OperationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.appointmentService.query()
            .subscribe((res: ResponseWrapper) => { this.appointments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.operationService
            .query({filter: 'appointmentoperation-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.appointmentOperation.operation || !this.appointmentOperation.operation.id) {
                    this.operations = res.json;
                } else {
                    this.operationService
                        .find(this.appointmentOperation.operation.id)
                        .subscribe((subRes: Operation) => {
                            this.operations = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.appointmentOperation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.appointmentOperationService.update(this.appointmentOperation));
        } else {
            this.subscribeToSaveResponse(
                this.appointmentOperationService.create(this.appointmentOperation));
        }
    }

    private subscribeToSaveResponse(result: Observable<AppointmentOperation>) {
        result.subscribe((res: AppointmentOperation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AppointmentOperation) {
        this.eventManager.broadcast({ name: 'appointmentOperationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackAppointmentById(index: number, item: Appointment) {
        return item.id;
    }

    trackOperationById(index: number, item: Operation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-appointment-operation-popup',
    template: ''
})
export class AppointmentOperationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appointmentOperationPopupService: AppointmentOperationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.appointmentOperationPopupService
                    .open(AppointmentOperationDialogComponent, params['id']);
            } else {
                this.modalRef = this.appointmentOperationPopupService
                    .open(AppointmentOperationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

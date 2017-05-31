import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { DoctorReview } from './doctor-review.model';
import { DoctorReviewPopupService } from './doctor-review-popup.service';
import { DoctorReviewService } from './doctor-review.service';
import { Appointment, AppointmentService } from '../appointment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-doctor-review-dialog',
    templateUrl: './doctor-review-dialog.component.html'
})
export class DoctorReviewDialogComponent implements OnInit {

    doctorReview: DoctorReview;
    authorities: any[];
    isSaving: boolean;

    appointments: Appointment[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private doctorReviewService: DoctorReviewService,
        private appointmentService: AppointmentService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.appointmentService.query()
            .subscribe((res: ResponseWrapper) => { this.appointments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.doctorReview.id !== undefined) {
            this.subscribeToSaveResponse(
                this.doctorReviewService.update(this.doctorReview));
        } else {
            this.subscribeToSaveResponse(
                this.doctorReviewService.create(this.doctorReview));
        }
    }

    private subscribeToSaveResponse(result: Observable<DoctorReview>) {
        result.subscribe((res: DoctorReview) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DoctorReview) {
        this.eventManager.broadcast({ name: 'doctorReviewListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-doctor-review-popup',
    template: ''
})
export class DoctorReviewPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorReviewPopupService: DoctorReviewPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.doctorReviewPopupService
                    .open(DoctorReviewDialogComponent, params['id']);
            } else {
                this.modalRef = this.doctorReviewPopupService
                    .open(DoctorReviewDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { DoctorDayRoutine } from './doctor-day-routine.model';
import { DoctorDayRoutinePopupService } from './doctor-day-routine-popup.service';
import { DoctorDayRoutineService } from './doctor-day-routine.service';
import { Doctor, DoctorService } from '../doctor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-doctor-day-routine-dialog',
    templateUrl: './doctor-day-routine-dialog.component.html'
})
export class DoctorDayRoutineDialogComponent implements OnInit {

    doctorDayRoutine: DoctorDayRoutine;
    authorities: any[];
    isSaving: boolean;

    doctors: Doctor[];
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private doctorDayRoutineService: DoctorDayRoutineService,
        private doctorService: DoctorService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.doctorService.query()
            .subscribe((res: ResponseWrapper) => { this.doctors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.doctorDayRoutine.id !== undefined) {
            this.subscribeToSaveResponse(
                this.doctorDayRoutineService.update(this.doctorDayRoutine));
        } else {
            this.subscribeToSaveResponse(
                this.doctorDayRoutineService.create(this.doctorDayRoutine));
        }
    }

    private subscribeToSaveResponse(result: Observable<DoctorDayRoutine>) {
        result.subscribe((res: DoctorDayRoutine) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DoctorDayRoutine) {
        this.eventManager.broadcast({ name: 'doctorDayRoutineListModification', content: 'OK'});
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

    trackDoctorById(index: number, item: Doctor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-doctor-day-routine-popup',
    template: ''
})
export class DoctorDayRoutinePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorDayRoutinePopupService: DoctorDayRoutinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.doctorDayRoutinePopupService
                    .open(DoctorDayRoutineDialogComponent, params['id']);
            } else {
                this.modalRef = this.doctorDayRoutinePopupService
                    .open(DoctorDayRoutineDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

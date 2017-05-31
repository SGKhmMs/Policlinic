import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { RoutineCase } from './routine-case.model';
import { RoutineCasePopupService } from './routine-case-popup.service';
import { RoutineCaseService } from './routine-case.service';
import { DoctorDayRoutine, DoctorDayRoutineService } from '../doctor-day-routine';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-routine-case-dialog',
    templateUrl: './routine-case-dialog.component.html'
})
export class RoutineCaseDialogComponent implements OnInit {

    routineCase: RoutineCase;
    authorities: any[];
    isSaving: boolean;

    doctordayroutines: DoctorDayRoutine[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private routineCaseService: RoutineCaseService,
        private doctorDayRoutineService: DoctorDayRoutineService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.doctorDayRoutineService.query()
            .subscribe((res: ResponseWrapper) => { this.doctordayroutines = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.routineCase.id !== undefined) {
            this.subscribeToSaveResponse(
                this.routineCaseService.update(this.routineCase));
        } else {
            this.subscribeToSaveResponse(
                this.routineCaseService.create(this.routineCase));
        }
    }

    private subscribeToSaveResponse(result: Observable<RoutineCase>) {
        result.subscribe((res: RoutineCase) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RoutineCase) {
        this.eventManager.broadcast({ name: 'routineCaseListModification', content: 'OK'});
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

    trackDoctorDayRoutineById(index: number, item: DoctorDayRoutine) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-routine-case-popup',
    template: ''
})
export class RoutineCasePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private routineCasePopupService: RoutineCasePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.routineCasePopupService
                    .open(RoutineCaseDialogComponent, params['id']);
            } else {
                this.modalRef = this.routineCasePopupService
                    .open(RoutineCaseDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

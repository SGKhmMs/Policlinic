import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Clinic } from './clinic.model';
import { ClinicPopupService } from './clinic-popup.service';
import { ClinicService } from './clinic.service';

@Component({
    selector: 'jhi-clinic-dialog',
    templateUrl: './clinic-dialog.component.html'
})
export class ClinicDialogComponent implements OnInit {

    clinic: Clinic;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clinicService: ClinicService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clinic.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clinicService.update(this.clinic));
        } else {
            this.subscribeToSaveResponse(
                this.clinicService.create(this.clinic));
        }
    }

    private subscribeToSaveResponse(result: Observable<Clinic>) {
        result.subscribe((res: Clinic) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Clinic) {
        this.eventManager.broadcast({ name: 'clinicListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-clinic-popup',
    template: ''
})
export class ClinicPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clinicPopupService: ClinicPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clinicPopupService
                    .open(ClinicDialogComponent, params['id']);
            } else {
                this.modalRef = this.clinicPopupService
                    .open(ClinicDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

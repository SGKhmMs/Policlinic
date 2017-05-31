import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClinicModerator } from './clinic-moderator.model';
import { ClinicModeratorPopupService } from './clinic-moderator-popup.service';
import { ClinicModeratorService } from './clinic-moderator.service';
import { Clinic, ClinicService } from '../clinic';
import { ClinicModeratorProfile, ClinicModeratorProfileService } from '../clinic-moderator-profile';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-clinic-moderator-dialog',
    templateUrl: './clinic-moderator-dialog.component.html'
})
export class ClinicModeratorDialogComponent implements OnInit {

    clinicModerator: ClinicModerator;
    authorities: any[];
    isSaving: boolean;

    clinics: Clinic[];

    clinicmoderatorprofiles: ClinicModeratorProfile[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clinicModeratorService: ClinicModeratorService,
        private clinicService: ClinicService,
        private clinicModeratorProfileService: ClinicModeratorProfileService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clinicService.query()
            .subscribe((res: ResponseWrapper) => { this.clinics = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.clinicModeratorProfileService
            .query({filter: 'clinicmoderator-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.clinicModerator.clinicModeratorProfile || !this.clinicModerator.clinicModeratorProfile.id) {
                    this.clinicmoderatorprofiles = res.json;
                } else {
                    this.clinicModeratorProfileService
                        .find(this.clinicModerator.clinicModeratorProfile.id)
                        .subscribe((subRes: ClinicModeratorProfile) => {
                            this.clinicmoderatorprofiles = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clinicModerator.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clinicModeratorService.update(this.clinicModerator));
        } else {
            this.subscribeToSaveResponse(
                this.clinicModeratorService.create(this.clinicModerator));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClinicModerator>) {
        result.subscribe((res: ClinicModerator) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClinicModerator) {
        this.eventManager.broadcast({ name: 'clinicModeratorListModification', content: 'OK'});
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

    trackClinicById(index: number, item: Clinic) {
        return item.id;
    }

    trackClinicModeratorProfileById(index: number, item: ClinicModeratorProfile) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-clinic-moderator-popup',
    template: ''
})
export class ClinicModeratorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clinicModeratorPopupService: ClinicModeratorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clinicModeratorPopupService
                    .open(ClinicModeratorDialogComponent, params['id']);
            } else {
                this.modalRef = this.clinicModeratorPopupService
                    .open(ClinicModeratorDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

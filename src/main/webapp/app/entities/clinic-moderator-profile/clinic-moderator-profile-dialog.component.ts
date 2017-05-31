import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClinicModeratorProfile } from './clinic-moderator-profile.model';
import { ClinicModeratorProfilePopupService } from './clinic-moderator-profile-popup.service';
import { ClinicModeratorProfileService } from './clinic-moderator-profile.service';

@Component({
    selector: 'jhi-clinic-moderator-profile-dialog',
    templateUrl: './clinic-moderator-profile-dialog.component.html'
})
export class ClinicModeratorProfileDialogComponent implements OnInit {

    clinicModeratorProfile: ClinicModeratorProfile;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clinicModeratorProfileService: ClinicModeratorProfileService,
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
        if (this.clinicModeratorProfile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clinicModeratorProfileService.update(this.clinicModeratorProfile));
        } else {
            this.subscribeToSaveResponse(
                this.clinicModeratorProfileService.create(this.clinicModeratorProfile));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClinicModeratorProfile>) {
        result.subscribe((res: ClinicModeratorProfile) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClinicModeratorProfile) {
        this.eventManager.broadcast({ name: 'clinicModeratorProfileListModification', content: 'OK'});
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
    selector: 'jhi-clinic-moderator-profile-popup',
    template: ''
})
export class ClinicModeratorProfilePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clinicModeratorProfilePopupService: ClinicModeratorProfilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clinicModeratorProfilePopupService
                    .open(ClinicModeratorProfileDialogComponent, params['id']);
            } else {
                this.modalRef = this.clinicModeratorProfilePopupService
                    .open(ClinicModeratorProfileDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

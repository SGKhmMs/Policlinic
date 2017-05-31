import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ClinicModeratorProfile } from './clinic-moderator-profile.model';
import { ClinicModeratorProfilePopupService } from './clinic-moderator-profile-popup.service';
import { ClinicModeratorProfileService } from './clinic-moderator-profile.service';

@Component({
    selector: 'jhi-clinic-moderator-profile-delete-dialog',
    templateUrl: './clinic-moderator-profile-delete-dialog.component.html'
})
export class ClinicModeratorProfileDeleteDialogComponent {

    clinicModeratorProfile: ClinicModeratorProfile;

    constructor(
        private clinicModeratorProfileService: ClinicModeratorProfileService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clinicModeratorProfileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clinicModeratorProfileListModification',
                content: 'Deleted an clinicModeratorProfile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clinic-moderator-profile-delete-popup',
    template: ''
})
export class ClinicModeratorProfileDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clinicModeratorProfilePopupService: ClinicModeratorProfilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clinicModeratorProfilePopupService
                .open(ClinicModeratorProfileDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

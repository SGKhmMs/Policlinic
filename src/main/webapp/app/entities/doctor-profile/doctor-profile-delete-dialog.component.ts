import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { DoctorProfile } from './doctor-profile.model';
import { DoctorProfilePopupService } from './doctor-profile-popup.service';
import { DoctorProfileService } from './doctor-profile.service';

@Component({
    selector: 'jhi-doctor-profile-delete-dialog',
    templateUrl: './doctor-profile-delete-dialog.component.html'
})
export class DoctorProfileDeleteDialogComponent {

    doctorProfile: DoctorProfile;

    constructor(
        private doctorProfileService: DoctorProfileService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.doctorProfileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'doctorProfileListModification',
                content: 'Deleted an doctorProfile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-doctor-profile-delete-popup',
    template: ''
})
export class DoctorProfileDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorProfilePopupService: DoctorProfilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.doctorProfilePopupService
                .open(DoctorProfileDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

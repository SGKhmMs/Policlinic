import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { DoctorReview } from './doctor-review.model';
import { DoctorReviewPopupService } from './doctor-review-popup.service';
import { DoctorReviewService } from './doctor-review.service';

@Component({
    selector: 'jhi-doctor-review-delete-dialog',
    templateUrl: './doctor-review-delete-dialog.component.html'
})
export class DoctorReviewDeleteDialogComponent {

    doctorReview: DoctorReview;

    constructor(
        private doctorReviewService: DoctorReviewService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.doctorReviewService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'doctorReviewListModification',
                content: 'Deleted an doctorReview'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-doctor-review-delete-popup',
    template: ''
})
export class DoctorReviewDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorReviewPopupService: DoctorReviewPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.doctorReviewPopupService
                .open(DoctorReviewDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

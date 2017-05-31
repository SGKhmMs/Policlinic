import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DoctorReview } from './doctor-review.model';
import { DoctorReviewService } from './doctor-review.service';
@Injectable()
export class DoctorReviewPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private doctorReviewService: DoctorReviewService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.doctorReviewService.find(id).subscribe((doctorReview) => {
                this.doctorReviewModalRef(component, doctorReview);
            });
        } else {
            return this.doctorReviewModalRef(component, new DoctorReview());
        }
    }

    doctorReviewModalRef(component: Component, doctorReview: DoctorReview): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.doctorReview = doctorReview;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}

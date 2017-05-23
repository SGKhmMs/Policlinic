import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { DoctorReview } from './doctor-review.model';
import { DoctorReviewService } from './doctor-review.service';

@Component({
    selector: 'jhi-doctor-review-detail',
    templateUrl: './doctor-review-detail.component.html'
})
export class DoctorReviewDetailComponent implements OnInit, OnDestroy {

    doctorReview: DoctorReview;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private doctorReviewService: DoctorReviewService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDoctorReviews();
    }

    load(id) {
        this.doctorReviewService.find(id).subscribe((doctorReview) => {
            this.doctorReview = doctorReview;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDoctorReviews() {
        this.eventSubscriber = this.eventManager.subscribe(
            'doctorReviewListModification',
            (response) => this.load(this.doctorReview.id)
        );
    }
}

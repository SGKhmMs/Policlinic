import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { DoctorReview } from './doctor-review.model';
import { DoctorReviewService } from './doctor-review.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-doctor-review',
    templateUrl: './doctor-review.component.html'
})
export class DoctorReviewComponent implements OnInit, OnDestroy {
doctorReviews: DoctorReview[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private doctorReviewService: DoctorReviewService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.doctorReviewService.query().subscribe(
            (res: ResponseWrapper) => {
                this.doctorReviews = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDoctorReviews();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DoctorReview) {
        return item.id;
    }
    registerChangeInDoctorReviews() {
        this.eventSubscriber = this.eventManager.subscribe('doctorReviewListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

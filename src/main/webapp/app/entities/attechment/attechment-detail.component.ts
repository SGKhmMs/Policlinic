import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Attechment } from './attechment.model';
import { AttechmentService } from './attechment.service';

@Component({
    selector: 'jhi-attechment-detail',
    templateUrl: './attechment-detail.component.html'
})
export class AttechmentDetailComponent implements OnInit, OnDestroy {

    attechment: Attechment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private attechmentService: AttechmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAttechments();
    }

    load(id) {
        this.attechmentService.find(id).subscribe((attechment) => {
            this.attechment = attechment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAttechments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'attechmentListModification',
            (response) => this.load(this.attechment.id)
        );
    }
}

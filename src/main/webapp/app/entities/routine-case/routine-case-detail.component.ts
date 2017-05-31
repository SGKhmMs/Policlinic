import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { RoutineCase } from './routine-case.model';
import { RoutineCaseService } from './routine-case.service';

@Component({
    selector: 'jhi-routine-case-detail',
    templateUrl: './routine-case-detail.component.html'
})
export class RoutineCaseDetailComponent implements OnInit, OnDestroy {

    routineCase: RoutineCase;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private routineCaseService: RoutineCaseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRoutineCases();
    }

    load(id) {
        this.routineCaseService.find(id).subscribe((routineCase) => {
            this.routineCase = routineCase;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRoutineCases() {
        this.eventSubscriber = this.eventManager.subscribe(
            'routineCaseListModification',
            (response) => this.load(this.routineCase.id)
        );
    }
}

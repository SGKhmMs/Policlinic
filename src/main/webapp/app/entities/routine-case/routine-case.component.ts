import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { RoutineCase } from './routine-case.model';
import { RoutineCaseService } from './routine-case.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-routine-case',
    templateUrl: './routine-case.component.html'
})
export class RoutineCaseComponent implements OnInit, OnDestroy {
routineCases: RoutineCase[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private routineCaseService: RoutineCaseService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.routineCaseService.query().subscribe(
            (res: ResponseWrapper) => {
                this.routineCases = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRoutineCases();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RoutineCase) {
        return item.id;
    }
    registerChangeInRoutineCases() {
        this.eventSubscriber = this.eventManager.subscribe('routineCaseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

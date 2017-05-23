import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Attechment } from './attechment.model';
import { AttechmentService } from './attechment.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-attechment',
    templateUrl: './attechment.component.html'
})
export class AttechmentComponent implements OnInit, OnDestroy {
attechments: Attechment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private attechmentService: AttechmentService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.attechmentService.query().subscribe(
            (res: Response) => {
                this.attechments = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAttechments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Attechment) {
        return item.id;
    }
    registerChangeInAttechments() {
        this.eventSubscriber = this.eventManager.subscribe('attechmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

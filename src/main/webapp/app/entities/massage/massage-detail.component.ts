import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Massage } from './massage.model';
import { MassageService } from './massage.service';

@Component({
    selector: 'jhi-massage-detail',
    templateUrl: './massage-detail.component.html'
})
export class MassageDetailComponent implements OnInit, OnDestroy {

    massage: Massage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private massageService: MassageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMassages();
    }

    load(id) {
        this.massageService.find(id).subscribe((massage) => {
            this.massage = massage;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMassages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'massageListModification',
            (response) => this.load(this.massage.id)
        );
    }
}

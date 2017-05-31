import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ClinicModerator } from './clinic-moderator.model';
import { ClinicModeratorService } from './clinic-moderator.service';

@Component({
    selector: 'jhi-clinic-moderator-detail',
    templateUrl: './clinic-moderator-detail.component.html'
})
export class ClinicModeratorDetailComponent implements OnInit, OnDestroy {

    clinicModerator: ClinicModerator;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private clinicModeratorService: ClinicModeratorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClinicModerators();
    }

    load(id) {
        this.clinicModeratorService.find(id).subscribe((clinicModerator) => {
            this.clinicModerator = clinicModerator;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClinicModerators() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clinicModeratorListModification',
            (response) => this.load(this.clinicModerator.id)
        );
    }
}

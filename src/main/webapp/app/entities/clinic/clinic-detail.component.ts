import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Clinic } from './clinic.model';
import { ClinicService } from './clinic.service';

@Component({
    selector: 'jhi-clinic-detail',
    templateUrl: './clinic-detail.component.html'
})
export class ClinicDetailComponent implements OnInit, OnDestroy {

    clinic: Clinic;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private clinicService: ClinicService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClinics();
    }

    load(id) {
        this.clinicService.find(id).subscribe((clinic) => {
            this.clinic = clinic;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClinics() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clinicListModification',
            (response) => this.load(this.clinic.id)
        );
    }
}

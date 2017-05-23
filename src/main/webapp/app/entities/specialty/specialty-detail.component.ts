import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Specialty } from './specialty.model';
import { SpecialtyService } from './specialty.service';

@Component({
    selector: 'jhi-specialty-detail',
    templateUrl: './specialty-detail.component.html'
})
export class SpecialtyDetailComponent implements OnInit, OnDestroy {

    specialty: Specialty;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private specialtyService: SpecialtyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSpecialties();
    }

    load(id) {
        this.specialtyService.find(id).subscribe((specialty) => {
            this.specialty = specialty;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSpecialties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'specialtyListModification',
            (response) => this.load(this.specialty.id)
        );
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { DoctorAdress } from './doctor-adress.model';
import { DoctorAdressService } from './doctor-adress.service';

@Component({
    selector: 'jhi-doctor-adress-detail',
    templateUrl: './doctor-adress-detail.component.html'
})
export class DoctorAdressDetailComponent implements OnInit, OnDestroy {

    doctorAdress: DoctorAdress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private doctorAdressService: DoctorAdressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDoctorAdresses();
    }

    load(id) {
        this.doctorAdressService.find(id).subscribe((doctorAdress) => {
            this.doctorAdress = doctorAdress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDoctorAdresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'doctorAdressListModification',
            (response) => this.load(this.doctorAdress.id)
        );
    }
}

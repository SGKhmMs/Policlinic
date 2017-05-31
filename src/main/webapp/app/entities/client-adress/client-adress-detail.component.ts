import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ClientAdress } from './client-adress.model';
import { ClientAdressService } from './client-adress.service';

@Component({
    selector: 'jhi-client-adress-detail',
    templateUrl: './client-adress-detail.component.html'
})
export class ClientAdressDetailComponent implements OnInit, OnDestroy {

    clientAdress: ClientAdress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private clientAdressService: ClientAdressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClientAdresses();
    }

    load(id) {
        this.clientAdressService.find(id).subscribe((clientAdress) => {
            this.clientAdress = clientAdress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClientAdresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clientAdressListModification',
            (response) => this.load(this.clientAdress.id)
        );
    }
}

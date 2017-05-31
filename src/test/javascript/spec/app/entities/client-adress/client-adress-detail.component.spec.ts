import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClientAdressDetailComponent } from '../../../../../../main/webapp/app/entities/client-adress/client-adress-detail.component';
import { ClientAdressService } from '../../../../../../main/webapp/app/entities/client-adress/client-adress.service';
import { ClientAdress } from '../../../../../../main/webapp/app/entities/client-adress/client-adress.model';

describe('Component Tests', () => {

    describe('ClientAdress Management Detail Component', () => {
        let comp: ClientAdressDetailComponent;
        let fixture: ComponentFixture<ClientAdressDetailComponent>;
        let service: ClientAdressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [ClientAdressDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClientAdressService,
                    EventManager
                ]
            }).overrideComponent(ClientAdressDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientAdressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientAdressService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClientAdress(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clientAdress).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

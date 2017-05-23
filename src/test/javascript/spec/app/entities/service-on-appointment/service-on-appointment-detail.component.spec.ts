import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ServiceOnAppointmentDetailComponent } from '../../../../../../main/webapp/app/entities/service-on-appointment/service-on-appointment-detail.component';
import { ServiceOnAppointmentService } from '../../../../../../main/webapp/app/entities/service-on-appointment/service-on-appointment.service';
import { ServiceOnAppointment } from '../../../../../../main/webapp/app/entities/service-on-appointment/service-on-appointment.model';

describe('Component Tests', () => {

    describe('ServiceOnAppointment Management Detail Component', () => {
        let comp: ServiceOnAppointmentDetailComponent;
        let fixture: ComponentFixture<ServiceOnAppointmentDetailComponent>;
        let service: ServiceOnAppointmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [ServiceOnAppointmentDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ServiceOnAppointmentService,
                    EventManager
                ]
            }).overrideComponent(ServiceOnAppointmentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServiceOnAppointmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServiceOnAppointmentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ServiceOnAppointment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.serviceOnAppointment).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

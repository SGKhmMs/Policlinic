import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AppointmentOperationDetailComponent } from '../../../../../../main/webapp/app/entities/appointment-operation/appointment-operation-detail.component';
import { AppointmentOperationService } from '../../../../../../main/webapp/app/entities/appointment-operation/appointment-operation.service';
import { AppointmentOperation } from '../../../../../../main/webapp/app/entities/appointment-operation/appointment-operation.model';

describe('Component Tests', () => {

    describe('AppointmentOperation Management Detail Component', () => {
        let comp: AppointmentOperationDetailComponent;
        let fixture: ComponentFixture<AppointmentOperationDetailComponent>;
        let service: AppointmentOperationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [AppointmentOperationDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AppointmentOperationService,
                    EventManager
                ]
            }).overrideComponent(AppointmentOperationDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppointmentOperationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppointmentOperationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AppointmentOperation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.appointmentOperation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

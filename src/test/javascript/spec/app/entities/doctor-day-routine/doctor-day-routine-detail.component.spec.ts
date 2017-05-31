import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DoctorDayRoutineDetailComponent } from '../../../../../../main/webapp/app/entities/doctor-day-routine/doctor-day-routine-detail.component';
import { DoctorDayRoutineService } from '../../../../../../main/webapp/app/entities/doctor-day-routine/doctor-day-routine.service';
import { DoctorDayRoutine } from '../../../../../../main/webapp/app/entities/doctor-day-routine/doctor-day-routine.model';

describe('Component Tests', () => {

    describe('DoctorDayRoutine Management Detail Component', () => {
        let comp: DoctorDayRoutineDetailComponent;
        let fixture: ComponentFixture<DoctorDayRoutineDetailComponent>;
        let service: DoctorDayRoutineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [DoctorDayRoutineDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DoctorDayRoutineService,
                    EventManager
                ]
            }).overrideComponent(DoctorDayRoutineDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DoctorDayRoutineDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DoctorDayRoutineService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DoctorDayRoutine(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.doctorDayRoutine).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DoctorSpecialtyDetailComponent } from '../../../../../../main/webapp/app/entities/doctor-specialty/doctor-specialty-detail.component';
import { DoctorSpecialtyService } from '../../../../../../main/webapp/app/entities/doctor-specialty/doctor-specialty.service';
import { DoctorSpecialty } from '../../../../../../main/webapp/app/entities/doctor-specialty/doctor-specialty.model';

describe('Component Tests', () => {

    describe('DoctorSpecialty Management Detail Component', () => {
        let comp: DoctorSpecialtyDetailComponent;
        let fixture: ComponentFixture<DoctorSpecialtyDetailComponent>;
        let service: DoctorSpecialtyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [DoctorSpecialtyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DoctorSpecialtyService,
                    EventManager
                ]
            }).overrideComponent(DoctorSpecialtyDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DoctorSpecialtyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DoctorSpecialtyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DoctorSpecialty(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.doctorSpecialty).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

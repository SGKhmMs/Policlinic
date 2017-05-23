import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DoctorReviewDetailComponent } from '../../../../../../main/webapp/app/entities/doctor-review/doctor-review-detail.component';
import { DoctorReviewService } from '../../../../../../main/webapp/app/entities/doctor-review/doctor-review.service';
import { DoctorReview } from '../../../../../../main/webapp/app/entities/doctor-review/doctor-review.model';

describe('Component Tests', () => {

    describe('DoctorReview Management Detail Component', () => {
        let comp: DoctorReviewDetailComponent;
        let fixture: ComponentFixture<DoctorReviewDetailComponent>;
        let service: DoctorReviewService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [DoctorReviewDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DoctorReviewService,
                    EventManager
                ]
            }).overrideComponent(DoctorReviewDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DoctorReviewDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DoctorReviewService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DoctorReview(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.doctorReview).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

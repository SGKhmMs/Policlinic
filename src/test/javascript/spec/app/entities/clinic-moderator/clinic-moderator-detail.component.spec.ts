import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClinicModeratorDetailComponent } from '../../../../../../main/webapp/app/entities/clinic-moderator/clinic-moderator-detail.component';
import { ClinicModeratorService } from '../../../../../../main/webapp/app/entities/clinic-moderator/clinic-moderator.service';
import { ClinicModerator } from '../../../../../../main/webapp/app/entities/clinic-moderator/clinic-moderator.model';

describe('Component Tests', () => {

    describe('ClinicModerator Management Detail Component', () => {
        let comp: ClinicModeratorDetailComponent;
        let fixture: ComponentFixture<ClinicModeratorDetailComponent>;
        let service: ClinicModeratorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [ClinicModeratorDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClinicModeratorService,
                    EventManager
                ]
            }).overrideComponent(ClinicModeratorDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClinicModeratorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClinicModeratorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClinicModerator(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clinicModerator).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MassageDetailComponent } from '../../../../../../main/webapp/app/entities/massage/massage-detail.component';
import { MassageService } from '../../../../../../main/webapp/app/entities/massage/massage.service';
import { Massage } from '../../../../../../main/webapp/app/entities/massage/massage.model';

describe('Component Tests', () => {

    describe('Massage Management Detail Component', () => {
        let comp: MassageDetailComponent;
        let fixture: ComponentFixture<MassageDetailComponent>;
        let service: MassageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [MassageDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MassageService,
                    EventManager
                ]
            }).overrideComponent(MassageDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MassageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MassageService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Massage(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.massage).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

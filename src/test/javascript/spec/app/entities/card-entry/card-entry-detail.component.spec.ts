import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CardEntryDetailComponent } from '../../../../../../main/webapp/app/entities/card-entry/card-entry-detail.component';
import { CardEntryService } from '../../../../../../main/webapp/app/entities/card-entry/card-entry.service';
import { CardEntry } from '../../../../../../main/webapp/app/entities/card-entry/card-entry.model';

describe('Component Tests', () => {

    describe('CardEntry Management Detail Component', () => {
        let comp: CardEntryDetailComponent;
        let fixture: ComponentFixture<CardEntryDetailComponent>;
        let service: CardEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [CardEntryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CardEntryService,
                    EventManager
                ]
            }).overrideComponent(CardEntryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CardEntryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CardEntryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CardEntry(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cardEntry).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

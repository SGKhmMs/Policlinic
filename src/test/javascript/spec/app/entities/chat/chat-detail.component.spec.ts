import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ChatDetailComponent } from '../../../../../../main/webapp/app/entities/chat/chat-detail.component';
import { ChatService } from '../../../../../../main/webapp/app/entities/chat/chat.service';
import { Chat } from '../../../../../../main/webapp/app/entities/chat/chat.model';

describe('Component Tests', () => {

    describe('Chat Management Detail Component', () => {
        let comp: ChatDetailComponent;
        let fixture: ComponentFixture<ChatDetailComponent>;
        let service: ChatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [ChatDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ChatService,
                    EventManager
                ]
            }).overrideComponent(ChatDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChatDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChatService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Chat(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.chat).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

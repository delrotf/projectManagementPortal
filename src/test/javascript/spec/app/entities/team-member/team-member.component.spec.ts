/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ProjectManagementPortalTestModule } from '../../../test.module';
import { TeamMemberComponent } from '../../../../../../main/webapp/app/entities/team-member/team-member.component';
import { TeamMemberService } from '../../../../../../main/webapp/app/entities/team-member/team-member.service';
import { TeamMember } from '../../../../../../main/webapp/app/entities/team-member/team-member.model';

describe('Component Tests', () => {

    describe('TeamMember Management Component', () => {
        let comp: TeamMemberComponent;
        let fixture: ComponentFixture<TeamMemberComponent>;
        let service: TeamMemberService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ProjectManagementPortalTestModule],
                declarations: [TeamMemberComponent],
                providers: [
                    TeamMemberService
                ]
            })
            .overrideTemplate(TeamMemberComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TeamMemberComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamMemberService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new TeamMember(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.teamMembers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

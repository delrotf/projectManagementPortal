/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ProjectManagementPortalTestModule } from '../../../test.module';
import { DesignationComponent } from '../../../../../../main/webapp/app/entities/designation/designation.component';
import { DesignationService } from '../../../../../../main/webapp/app/entities/designation/designation.service';
import { Designation } from '../../../../../../main/webapp/app/entities/designation/designation.model';

describe('Component Tests', () => {

    describe('Designation Management Component', () => {
        let comp: DesignationComponent;
        let fixture: ComponentFixture<DesignationComponent>;
        let service: DesignationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ProjectManagementPortalTestModule],
                declarations: [DesignationComponent],
                providers: [
                    DesignationService
                ]
            })
            .overrideTemplate(DesignationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Designation(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.designations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

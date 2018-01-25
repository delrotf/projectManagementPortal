/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ProjectManagementPortalTestModule } from '../../../test.module';
import { DesignationDetailComponent } from '../../../../../../main/webapp/app/entities/designation/designation-detail.component';
import { DesignationService } from '../../../../../../main/webapp/app/entities/designation/designation.service';
import { Designation } from '../../../../../../main/webapp/app/entities/designation/designation.model';

describe('Component Tests', () => {

    describe('Designation Management Detail Component', () => {
        let comp: DesignationDetailComponent;
        let fixture: ComponentFixture<DesignationDetailComponent>;
        let service: DesignationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ProjectManagementPortalTestModule],
                declarations: [DesignationDetailComponent],
                providers: [
                    DesignationService
                ]
            })
            .overrideTemplate(DesignationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Designation(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.designation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

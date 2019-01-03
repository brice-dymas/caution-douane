/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { AppurementDetailComponent } from 'app/entities/appurement/appurement-detail.component';
import { Appurement } from 'app/shared/model/appurement.model';

describe('Component Tests', () => {
    describe('Appurement Management Detail Component', () => {
        let comp: AppurementDetailComponent;
        let fixture: ComponentFixture<AppurementDetailComponent>;
        const route = ({ data: of({ appurement: new Appurement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [AppurementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AppurementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AppurementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.appurement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

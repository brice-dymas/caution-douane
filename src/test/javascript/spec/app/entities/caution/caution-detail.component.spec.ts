/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { CautionDetailComponent } from 'app/entities/caution/caution-detail.component';
import { Caution } from 'app/shared/model/caution.model';

describe('Component Tests', () => {
    describe('Caution Management Detail Component', () => {
        let comp: CautionDetailComponent;
        let fixture: ComponentFixture<CautionDetailComponent>;
        const route = ({ data: of({ caution: new Caution(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [CautionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CautionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CautionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.caution).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

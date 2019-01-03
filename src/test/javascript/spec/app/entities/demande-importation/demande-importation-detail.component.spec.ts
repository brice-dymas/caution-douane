/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { DemandeImportationDetailComponent } from 'app/entities/demande-importation/demande-importation-detail.component';
import { DemandeImportation } from 'app/shared/model/demande-importation.model';

describe('Component Tests', () => {
    describe('DemandeImportation Management Detail Component', () => {
        let comp: DemandeImportationDetailComponent;
        let fixture: ComponentFixture<DemandeImportationDetailComponent>;
        const route = ({ data: of({ demandeImportation: new DemandeImportation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [DemandeImportationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DemandeImportationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DemandeImportationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.demandeImportation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

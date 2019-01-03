/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { DIQteTypeVehiculeDetailComponent } from 'app/entities/di-qte-type-vehicule/di-qte-type-vehicule-detail.component';
import { DIQteTypeVehicule } from 'app/shared/model/di-qte-type-vehicule.model';

describe('Component Tests', () => {
    describe('DIQteTypeVehicule Management Detail Component', () => {
        let comp: DIQteTypeVehiculeDetailComponent;
        let fixture: ComponentFixture<DIQteTypeVehiculeDetailComponent>;
        const route = ({ data: of({ dIQteTypeVehicule: new DIQteTypeVehicule(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [DIQteTypeVehiculeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DIQteTypeVehiculeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DIQteTypeVehiculeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dIQteTypeVehicule).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

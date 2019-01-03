/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { PieceJointeDetailComponent } from 'app/entities/piece-jointe/piece-jointe-detail.component';
import { PieceJointe } from 'app/shared/model/piece-jointe.model';

describe('Component Tests', () => {
    describe('PieceJointe Management Detail Component', () => {
        let comp: PieceJointeDetailComponent;
        let fixture: ComponentFixture<PieceJointeDetailComponent>;
        const route = ({ data: of({ pieceJointe: new PieceJointe(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [PieceJointeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PieceJointeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PieceJointeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pieceJointe).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

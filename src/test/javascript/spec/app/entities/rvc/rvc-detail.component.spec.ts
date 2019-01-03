/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { RVCDetailComponent } from 'app/entities/rvc/rvc-detail.component';
import { RVC } from 'app/shared/model/rvc.model';

describe('Component Tests', () => {
    describe('RVC Management Detail Component', () => {
        let comp: RVCDetailComponent;
        let fixture: ComponentFixture<RVCDetailComponent>;
        const route = ({ data: of({ rVC: new RVC(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [RVCDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RVCDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RVCDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rVC).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

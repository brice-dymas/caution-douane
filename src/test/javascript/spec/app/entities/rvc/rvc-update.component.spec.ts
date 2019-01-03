/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { RVCUpdateComponent } from 'app/entities/rvc/rvc-update.component';
import { RVCService } from 'app/entities/rvc/rvc.service';
import { RVC } from 'app/shared/model/rvc.model';

describe('Component Tests', () => {
    describe('RVC Management Update Component', () => {
        let comp: RVCUpdateComponent;
        let fixture: ComponentFixture<RVCUpdateComponent>;
        let service: RVCService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [RVCUpdateComponent]
            })
                .overrideTemplate(RVCUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RVCUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RVCService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RVC(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rVC = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RVC();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rVC = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

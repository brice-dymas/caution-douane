/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { AppurementUpdateComponent } from 'app/entities/appurement/appurement-update.component';
import { AppurementService } from 'app/entities/appurement/appurement.service';
import { Appurement } from 'app/shared/model/appurement.model';

describe('Component Tests', () => {
    describe('Appurement Management Update Component', () => {
        let comp: AppurementUpdateComponent;
        let fixture: ComponentFixture<AppurementUpdateComponent>;
        let service: AppurementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [AppurementUpdateComponent]
            })
                .overrideTemplate(AppurementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AppurementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppurementService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Appurement(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appurement = entity;
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
                    const entity = new Appurement();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appurement = entity;
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

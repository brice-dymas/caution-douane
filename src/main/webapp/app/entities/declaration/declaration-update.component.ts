import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDeclaration } from 'app/shared/model/declaration.model';
import { DeclarationService } from './declaration.service';
import { IDemandeImportation } from 'app/shared/model/demande-importation.model';
import { DemandeImportationService } from 'app/entities/demande-importation';

@Component({
    selector: 'jhi-declaration-update',
    templateUrl: './declaration-update.component.html'
})
export class DeclarationUpdateComponent implements OnInit {
    declaration: IDeclaration;
    isSaving: boolean;

    demandeimportations: IDemandeImportation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected declarationService: DeclarationService,
        protected demandeImportationService: DemandeImportationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ declaration }) => {
            this.declaration = declaration;
        });
        this.demandeImportationService.query().subscribe(
            (res: HttpResponse<IDemandeImportation[]>) => {
                this.demandeimportations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.declaration.id !== undefined) {
            this.subscribeToSaveResponse(this.declarationService.update(this.declaration));
        } else {
            this.subscribeToSaveResponse(this.declarationService.create(this.declaration));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeclaration>>) {
        result.subscribe((res: HttpResponse<IDeclaration>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDemandeImportationById(index: number, item: IDemandeImportation) {
        return item.id;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPieceJointe } from 'app/shared/model/piece-jointe.model';
import { PieceJointeService } from './piece-jointe.service';
import { IDemandeImportation } from 'app/shared/model/demande-importation.model';
import { DemandeImportationService } from 'app/entities/demande-importation';
import { IRVC } from 'app/shared/model/rvc.model';
import { RVCService } from 'app/entities/rvc';
import { IDeclaration } from 'app/shared/model/declaration.model';
import { DeclarationService } from 'app/entities/declaration';

@Component({
    selector: 'jhi-piece-jointe-update',
    templateUrl: './piece-jointe-update.component.html'
})
export class PieceJointeUpdateComponent implements OnInit {
    pieceJointe: IPieceJointe;
    isSaving: boolean;

    demandeimportations: IDemandeImportation[];

    rvcs: IRVC[];

    declarations: IDeclaration[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected pieceJointeService: PieceJointeService,
        protected demandeImportationService: DemandeImportationService,
        protected rVCService: RVCService,
        protected declarationService: DeclarationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pieceJointe }) => {
            this.pieceJointe = pieceJointe;
        });
        this.demandeImportationService.query().subscribe(
            (res: HttpResponse<IDemandeImportation[]>) => {
                this.demandeimportations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rVCService.query().subscribe(
            (res: HttpResponse<IRVC[]>) => {
                this.rvcs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.declarationService.query().subscribe(
            (res: HttpResponse<IDeclaration[]>) => {
                this.declarations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pieceJointe.id !== undefined) {
            this.subscribeToSaveResponse(this.pieceJointeService.update(this.pieceJointe));
        } else {
            this.subscribeToSaveResponse(this.pieceJointeService.create(this.pieceJointe));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPieceJointe>>) {
        result.subscribe((res: HttpResponse<IPieceJointe>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRVCById(index: number, item: IRVC) {
        return item.id;
    }

    trackDeclarationById(index: number, item: IDeclaration) {
        return item.id;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICaution } from 'app/shared/model/caution.model';
import { CautionService } from './caution.service';
import { IBanque } from 'app/shared/model/banque.model';
import { BanqueService } from 'app/entities/banque';

@Component({
    selector: 'jhi-caution-update',
    templateUrl: './caution-update.component.html'
})
export class CautionUpdateComponent implements OnInit {
    caution: ICaution;
    isSaving: boolean;

    banques: IBanque[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected cautionService: CautionService,
        protected banqueService: BanqueService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ caution }) => {
            this.caution = caution;
        });
        this.banqueService.query().subscribe(
            (res: HttpResponse<IBanque[]>) => {
                this.banques = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.caution.id !== undefined) {
            this.subscribeToSaveResponse(this.cautionService.update(this.caution));
        } else {
            this.subscribeToSaveResponse(this.cautionService.create(this.caution));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaution>>) {
        result.subscribe((res: HttpResponse<ICaution>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBanqueById(index: number, item: IBanque) {
        return item.id;
    }
}

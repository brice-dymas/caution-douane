import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAppurement } from 'app/shared/model/appurement.model';
import { AppurementService } from './appurement.service';
import { ICaution } from 'app/shared/model/caution.model';
import { CautionService } from 'app/entities/caution';

@Component({
    selector: 'jhi-appurement-update',
    templateUrl: './appurement-update.component.html'
})
export class AppurementUpdateComponent implements OnInit {
    appurement: IAppurement;
    isSaving: boolean;

    cautions: ICaution[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected appurementService: AppurementService,
        protected cautionService: CautionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ appurement }) => {
            this.appurement = appurement;
        });
        this.cautionService.query().subscribe(
            (res: HttpResponse<ICaution[]>) => {
                this.cautions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.appurement.id !== undefined) {
            this.subscribeToSaveResponse(this.appurementService.update(this.appurement));
        } else {
            this.subscribeToSaveResponse(this.appurementService.create(this.appurement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppurement>>) {
        result.subscribe((res: HttpResponse<IAppurement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCautionById(index: number, item: ICaution) {
        return item.id;
    }
}

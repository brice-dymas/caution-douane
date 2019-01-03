import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPieceJointe } from 'app/shared/model/piece-jointe.model';

@Component({
    selector: 'jhi-piece-jointe-detail',
    templateUrl: './piece-jointe-detail.component.html'
})
export class PieceJointeDetailComponent implements OnInit {
    pieceJointe: IPieceJointe;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pieceJointe }) => {
            this.pieceJointe = pieceJointe;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}

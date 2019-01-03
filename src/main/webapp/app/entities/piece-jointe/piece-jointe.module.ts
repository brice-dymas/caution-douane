import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    PieceJointeComponent,
    PieceJointeDetailComponent,
    PieceJointeUpdateComponent,
    PieceJointeDeletePopupComponent,
    PieceJointeDeleteDialogComponent,
    pieceJointeRoute,
    pieceJointePopupRoute
} from './';

const ENTITY_STATES = [...pieceJointeRoute, ...pieceJointePopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PieceJointeComponent,
        PieceJointeDetailComponent,
        PieceJointeUpdateComponent,
        PieceJointeDeleteDialogComponent,
        PieceJointeDeletePopupComponent
    ],
    entryComponents: [PieceJointeComponent, PieceJointeUpdateComponent, PieceJointeDeleteDialogComponent, PieceJointeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouanePieceJointeModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    MarqueComponent,
    MarqueDetailComponent,
    MarqueUpdateComponent,
    MarqueDeletePopupComponent,
    MarqueDeleteDialogComponent,
    marqueRoute,
    marquePopupRoute
} from './';

const ENTITY_STATES = [...marqueRoute, ...marquePopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MarqueComponent, MarqueDetailComponent, MarqueUpdateComponent, MarqueDeleteDialogComponent, MarqueDeletePopupComponent],
    entryComponents: [MarqueComponent, MarqueUpdateComponent, MarqueDeleteDialogComponent, MarqueDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneMarqueModule {}

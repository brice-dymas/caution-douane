import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    RVCComponent,
    RVCDetailComponent,
    RVCUpdateComponent,
    RVCDeletePopupComponent,
    RVCDeleteDialogComponent,
    rVCRoute,
    rVCPopupRoute
} from './';

const ENTITY_STATES = [...rVCRoute, ...rVCPopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RVCComponent, RVCDetailComponent, RVCUpdateComponent, RVCDeleteDialogComponent, RVCDeletePopupComponent],
    entryComponents: [RVCComponent, RVCUpdateComponent, RVCDeleteDialogComponent, RVCDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneRVCModule {}

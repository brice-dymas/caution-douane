import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    CautionComponent,
    CautionDetailComponent,
    CautionUpdateComponent,
    CautionDeletePopupComponent,
    CautionDeleteDialogComponent,
    cautionRoute,
    cautionPopupRoute
} from './';

const ENTITY_STATES = [...cautionRoute, ...cautionPopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CautionComponent,
        CautionDetailComponent,
        CautionUpdateComponent,
        CautionDeleteDialogComponent,
        CautionDeletePopupComponent
    ],
    entryComponents: [CautionComponent, CautionUpdateComponent, CautionDeleteDialogComponent, CautionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneCautionModule {}

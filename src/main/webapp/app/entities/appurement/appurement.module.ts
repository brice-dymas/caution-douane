import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    AppurementComponent,
    AppurementDetailComponent,
    AppurementUpdateComponent,
    AppurementDeletePopupComponent,
    AppurementDeleteDialogComponent,
    appurementRoute,
    appurementPopupRoute
} from './';

const ENTITY_STATES = [...appurementRoute, ...appurementPopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AppurementComponent,
        AppurementDetailComponent,
        AppurementUpdateComponent,
        AppurementDeleteDialogComponent,
        AppurementDeletePopupComponent
    ],
    entryComponents: [AppurementComponent, AppurementUpdateComponent, AppurementDeleteDialogComponent, AppurementDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneAppurementModule {}

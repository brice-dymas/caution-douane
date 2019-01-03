import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    DIQteTypeVehiculeComponent,
    DIQteTypeVehiculeDetailComponent,
    DIQteTypeVehiculeUpdateComponent,
    DIQteTypeVehiculeDeletePopupComponent,
    DIQteTypeVehiculeDeleteDialogComponent,
    dIQteTypeVehiculeRoute,
    dIQteTypeVehiculePopupRoute
} from './';

const ENTITY_STATES = [...dIQteTypeVehiculeRoute, ...dIQteTypeVehiculePopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DIQteTypeVehiculeComponent,
        DIQteTypeVehiculeDetailComponent,
        DIQteTypeVehiculeUpdateComponent,
        DIQteTypeVehiculeDeleteDialogComponent,
        DIQteTypeVehiculeDeletePopupComponent
    ],
    entryComponents: [
        DIQteTypeVehiculeComponent,
        DIQteTypeVehiculeUpdateComponent,
        DIQteTypeVehiculeDeleteDialogComponent,
        DIQteTypeVehiculeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneDIQteTypeVehiculeModule {}

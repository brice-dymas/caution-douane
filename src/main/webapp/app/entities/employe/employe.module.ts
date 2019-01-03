import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import { CautiondouaneAdminModule } from 'app/admin/admin.module';
import {
    EmployeComponent,
    EmployeDetailComponent,
    EmployeUpdateComponent,
    EmployeDeletePopupComponent,
    EmployeDeleteDialogComponent,
    employeRoute,
    employePopupRoute
} from './';

const ENTITY_STATES = [...employeRoute, ...employePopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, CautiondouaneAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmployeComponent,
        EmployeDetailComponent,
        EmployeUpdateComponent,
        EmployeDeleteDialogComponent,
        EmployeDeletePopupComponent
    ],
    entryComponents: [EmployeComponent, EmployeUpdateComponent, EmployeDeleteDialogComponent, EmployeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneEmployeModule {}

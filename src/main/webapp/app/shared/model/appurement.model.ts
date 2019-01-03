import { ICaution } from 'app/shared/model//caution.model';

export interface IAppurement {
    id?: number;
    numeroAppurement?: string;
    montantAppure?: number;
    nombreVehicule?: number;
    caution?: ICaution;
}

export class Appurement implements IAppurement {
    constructor(
        public id?: number,
        public numeroAppurement?: string,
        public montantAppure?: number,
        public nombreVehicule?: number,
        public caution?: ICaution
    ) {}
}

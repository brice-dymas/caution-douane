import { IDemandeImportation } from 'app/shared/model//demande-importation.model';

export interface IDeclaration {
    id?: number;
    numeroDeclaration?: string;
    reference?: string;
    numeroIM7?: string;
    demandeImportation?: IDemandeImportation;
}

export class Declaration implements IDeclaration {
    constructor(
        public id?: number,
        public numeroDeclaration?: string,
        public reference?: string,
        public numeroIM7?: string,
        public demandeImportation?: IDemandeImportation
    ) {}
}

import { BaseEntity } from './../../shared';

export class Designation implements BaseEntity {
    constructor(
        public id?: number,
        public designation?: string,
    ) {
    }
}

import { BaseEntity } from './../../shared';

export class TeamMember implements BaseEntity {
    constructor(
        public id?: number,
        public updatedTime?: any,
        public userInfoId?: number,
        public userInfoUserLogin?: string,
        public teamId?: number,
    ) {
    }
}

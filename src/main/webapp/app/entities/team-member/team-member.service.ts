import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { TeamMember } from './team-member.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TeamMemberService {

    private resourceUrl =  SERVER_API_URL + 'api/team-members';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(teamMember: TeamMember): Observable<TeamMember> {
        const copy = this.convert(teamMember);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(teamMember: TeamMember): Observable<TeamMember> {
        const copy = this.convert(teamMember);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TeamMember> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to TeamMember.
     */
    private convertItemFromServer(json: any): TeamMember {
        const entity: TeamMember = Object.assign(new TeamMember(), json);
        entity.updatedTime = this.dateUtils
            .convertDateTimeFromServer(json.updatedTime);
        return entity;
    }

    /**
     * Convert a TeamMember to a JSON which can be sent to the server.
     */
    private convert(teamMember: TeamMember): TeamMember {
        const copy: TeamMember = Object.assign({}, teamMember);

        copy.updatedTime = this.dateUtils.toDate(teamMember.updatedTime);
        return copy;
    }
}

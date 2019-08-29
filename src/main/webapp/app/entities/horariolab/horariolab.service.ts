import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHorariolab } from 'app/shared/model/horariolab.model';

type EntityResponseType = HttpResponse<IHorariolab>;
type EntityArrayResponseType = HttpResponse<IHorariolab[]>;

@Injectable({ providedIn: 'root' })
export class HorariolabService {
  public resourceUrl = SERVER_API_URL + 'api/horariolabs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/horariolabs';

  constructor(protected http: HttpClient) {}

  create(horariolab: IHorariolab): Observable<EntityResponseType> {
    return this.http.post<IHorariolab>(this.resourceUrl, horariolab, { observe: 'response' });
  }

  update(horariolab: IHorariolab): Observable<EntityResponseType> {
    return this.http.put<IHorariolab>(this.resourceUrl, horariolab, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHorariolab>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHorariolab[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHorariolab[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

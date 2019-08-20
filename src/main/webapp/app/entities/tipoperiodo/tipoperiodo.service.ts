import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoperiodo } from 'app/shared/model/tipoperiodo.model';

type EntityResponseType = HttpResponse<ITipoperiodo>;
type EntityArrayResponseType = HttpResponse<ITipoperiodo[]>;

@Injectable({ providedIn: 'root' })
export class TipoperiodoService {
  public resourceUrl = SERVER_API_URL + 'api/tipoperiodos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipoperiodos';

  constructor(protected http: HttpClient) {}

  create(tipoperiodo: ITipoperiodo): Observable<EntityResponseType> {
    return this.http.post<ITipoperiodo>(this.resourceUrl, tipoperiodo, { observe: 'response' });
  }

  update(tipoperiodo: ITipoperiodo): Observable<EntityResponseType> {
    return this.http.put<ITipoperiodo>(this.resourceUrl, tipoperiodo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoperiodo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoperiodo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoperiodo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

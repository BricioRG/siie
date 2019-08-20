import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipomov } from 'app/shared/model/tipomov.model';

type EntityResponseType = HttpResponse<ITipomov>;
type EntityArrayResponseType = HttpResponse<ITipomov[]>;

@Injectable({ providedIn: 'root' })
export class TipomovService {
  public resourceUrl = SERVER_API_URL + 'api/tipomovs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipomovs';

  constructor(protected http: HttpClient) {}

  create(tipomov: ITipomov): Observable<EntityResponseType> {
    return this.http.post<ITipomov>(this.resourceUrl, tipomov, { observe: 'response' });
  }

  update(tipomov: ITipomov): Observable<EntityResponseType> {
    return this.http.put<ITipomov>(this.resourceUrl, tipomov, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipomov>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipomov[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipomov[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

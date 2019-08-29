import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMotivomov } from 'app/shared/model/motivomov.model';

type EntityResponseType = HttpResponse<IMotivomov>;
type EntityArrayResponseType = HttpResponse<IMotivomov[]>;

@Injectable({ providedIn: 'root' })
export class MotivomovService {
  public resourceUrl = SERVER_API_URL + 'api/motivomovs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/motivomovs';

  constructor(protected http: HttpClient) {}

  create(motivomov: IMotivomov): Observable<EntityResponseType> {
    return this.http.post<IMotivomov>(this.resourceUrl, motivomov, { observe: 'response' });
  }

  update(motivomov: IMotivomov): Observable<EntityResponseType> {
    return this.http.put<IMotivomov>(this.resourceUrl, motivomov, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMotivomov>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMotivomov[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMotivomov[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

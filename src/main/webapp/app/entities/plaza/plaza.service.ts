import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlaza } from 'app/shared/model/plaza.model';

type EntityResponseType = HttpResponse<IPlaza>;
type EntityArrayResponseType = HttpResponse<IPlaza[]>;

@Injectable({ providedIn: 'root' })
export class PlazaService {
  public resourceUrl = SERVER_API_URL + 'api/plazas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/plazas';

  constructor(protected http: HttpClient) {}

  create(plaza: IPlaza): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plaza);
    return this.http
      .post<IPlaza>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plaza: IPlaza): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plaza);
    return this.http
      .put<IPlaza>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlaza>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlaza[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlaza[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(plaza: IPlaza): IPlaza {
    const copy: IPlaza = Object.assign({}, plaza, {
      fechaini: plaza.fechaini != null && plaza.fechaini.isValid() ? plaza.fechaini.format(DATE_FORMAT) : null,
      fechafin: plaza.fechafin != null && plaza.fechafin.isValid() ? plaza.fechafin.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaini = res.body.fechaini != null ? moment(res.body.fechaini) : null;
      res.body.fechafin = res.body.fechafin != null ? moment(res.body.fechafin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((plaza: IPlaza) => {
        plaza.fechaini = plaza.fechaini != null ? moment(plaza.fechaini) : null;
        plaza.fechafin = plaza.fechafin != null ? moment(plaza.fechafin) : null;
      });
    }
    return res;
  }
}

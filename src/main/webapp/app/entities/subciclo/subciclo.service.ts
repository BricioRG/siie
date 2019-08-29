import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISubciclo } from 'app/shared/model/subciclo.model';

type EntityResponseType = HttpResponse<ISubciclo>;
type EntityArrayResponseType = HttpResponse<ISubciclo[]>;

@Injectable({ providedIn: 'root' })
export class SubcicloService {
  public resourceUrl = SERVER_API_URL + 'api/subciclos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/subciclos';

  constructor(protected http: HttpClient) {}

  create(subciclo: ISubciclo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subciclo);
    return this.http
      .post<ISubciclo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subciclo: ISubciclo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subciclo);
    return this.http
      .put<ISubciclo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISubciclo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubciclo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubciclo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(subciclo: ISubciclo): ISubciclo {
    const copy: ISubciclo = Object.assign({}, subciclo, {
      fecini: subciclo.fecini != null && subciclo.fecini.isValid() ? subciclo.fecini.format(DATE_FORMAT) : null,
      fechafin: subciclo.fechafin != null && subciclo.fechafin.isValid() ? subciclo.fechafin.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecini = res.body.fecini != null ? moment(res.body.fecini) : null;
      res.body.fechafin = res.body.fechafin != null ? moment(res.body.fechafin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((subciclo: ISubciclo) => {
        subciclo.fecini = subciclo.fecini != null ? moment(subciclo.fecini) : null;
        subciclo.fechafin = subciclo.fechafin != null ? moment(subciclo.fechafin) : null;
      });
    }
    return res;
  }
}

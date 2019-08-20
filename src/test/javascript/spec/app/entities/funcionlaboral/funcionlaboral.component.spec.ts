/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { FuncionlaboralComponent } from 'app/entities/funcionlaboral/funcionlaboral.component';
import { FuncionlaboralService } from 'app/entities/funcionlaboral/funcionlaboral.service';
import { Funcionlaboral } from 'app/shared/model/funcionlaboral.model';

describe('Component Tests', () => {
  describe('Funcionlaboral Management Component', () => {
    let comp: FuncionlaboralComponent;
    let fixture: ComponentFixture<FuncionlaboralComponent>;
    let service: FuncionlaboralService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [FuncionlaboralComponent],
        providers: []
      })
        .overrideTemplate(FuncionlaboralComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuncionlaboralComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuncionlaboralService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Funcionlaboral(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.funcionlaborals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

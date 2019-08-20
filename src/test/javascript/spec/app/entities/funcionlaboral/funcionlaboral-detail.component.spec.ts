/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { FuncionlaboralDetailComponent } from 'app/entities/funcionlaboral/funcionlaboral-detail.component';
import { Funcionlaboral } from 'app/shared/model/funcionlaboral.model';

describe('Component Tests', () => {
  describe('Funcionlaboral Management Detail Component', () => {
    let comp: FuncionlaboralDetailComponent;
    let fixture: ComponentFixture<FuncionlaboralDetailComponent>;
    const route = ({ data: of({ funcionlaboral: new Funcionlaboral(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [FuncionlaboralDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FuncionlaboralDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuncionlaboralDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.funcionlaboral).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

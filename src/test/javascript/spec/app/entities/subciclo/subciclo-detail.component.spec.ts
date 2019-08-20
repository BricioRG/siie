/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { SubcicloDetailComponent } from 'app/entities/subciclo/subciclo-detail.component';
import { Subciclo } from 'app/shared/model/subciclo.model';

describe('Component Tests', () => {
  describe('Subciclo Management Detail Component', () => {
    let comp: SubcicloDetailComponent;
    let fixture: ComponentFixture<SubcicloDetailComponent>;
    const route = ({ data: of({ subciclo: new Subciclo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [SubcicloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubcicloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubcicloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subciclo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

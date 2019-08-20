/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { MotivomovDetailComponent } from 'app/entities/motivomov/motivomov-detail.component';
import { Motivomov } from 'app/shared/model/motivomov.model';

describe('Component Tests', () => {
  describe('Motivomov Management Detail Component', () => {
    let comp: MotivomovDetailComponent;
    let fixture: ComponentFixture<MotivomovDetailComponent>;
    const route = ({ data: of({ motivomov: new Motivomov(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [MotivomovDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MotivomovDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MotivomovDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.motivomov).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
